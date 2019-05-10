package org.minbox.framework.knowledge.library.service.article.service;

import com.gitee.hengboy.mybatis.enhance.dsl.serach.Searchable;
import com.gitee.hengboy.mybatis.enhance.dsl.update.filter.SetFilter;
import com.gitee.hengboy.mybatis.enhance.sort.SortEnum;
import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.request.PageableRequest;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceLoad;
import org.minbox.framework.api.boot.plugin.resource.load.enums.ResourceStoreEvent;
import org.minbox.framework.knowledge.library.common.base.BaseService;
import org.minbox.framework.knowledge.library.common.constants.Constant;
import org.minbox.framework.knowledge.library.common.dsl.DArticleInfoEntity;
import org.minbox.framework.knowledge.library.common.dsl.DArticleTopicUniEntity;
import org.minbox.framework.knowledge.library.common.entity.ArticleInfoEntity;
import org.minbox.framework.knowledge.library.common.entity.ArticleTopicInfoEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.article.dto.ArticleInfoDTO;
import org.minbox.framework.knowledge.library.service.article.mapper.ArticleInfoMapper;
import org.minbox.framework.knowledge.library.service.article.param.ArticleQueryParam;
import org.minbox.framework.knowledge.library.service.comment.service.CommentService;
import org.minbox.framework.knowledge.library.service.constants.ResourceType;
import org.minbox.framework.knowledge.library.service.user.service.UserLikeRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 文章业务逻辑
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 11:23
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleInfoService extends BaseService {
    /**
     * 文章专题业务逻辑
     */
    @Autowired
    private ArticleTopicInfoService articleTopicInfoService;
    /**
     * 喜欢文章记录
     */
    @Autowired
    private UserLikeRecordService userLikeRecordService;
    /**
     * 同类方法AOP失效采用这种方法调用
     */
    @Autowired
    private ArticleInfoService articleInfoService;
    /**
     * 文章留言
     */
    @Autowired
    private CommentService commentService;

    @Autowired
    private ArticleInfoMapper articleInfoMapper;

    /**
     * 分享回调
     * 1、更新文章分享数量
     *
     * @param articleId 文章编号
     * @throws KnowledgeException
     */
    public void shareCallBack(String articleId) throws KnowledgeException {
        // 查询文章详情 & 检查文章有效性
        ArticleInfoEntity article = selectArticleDetail(articleId);
        // 更新分享数量
        DArticleInfoEntity dArticleInfoEntity = DArticleInfoEntity.DSL();
        dslQuery.createUpdateable().update(dArticleInfoEntity).set(SetFilter.set(dArticleInfoEntity.aiShareCount, article.getAiShareCount() + 1)).where(dArticleInfoEntity.aiId.eq(articleId)).execute();
    }

    /**
     * 喜欢文章
     * 1. 写入喜欢记录
     * 2. 更新文章喜欢数量
     *
     * @param userId    用户编号
     * @param articleId 文章编号
     * @throws KnowledgeException
     */
    public int likeArticle(String userId, String articleId) throws KnowledgeException {
        // 查询文章详情 & 检查文章有效性
        ArticleInfoDTO article = detail(articleId, true);
        // 写入喜欢记录
        userLikeRecordService.likeArticle(articleId, userId);
        // 更新喜欢数量
        DArticleInfoEntity dArticleInfoEntity = DArticleInfoEntity.DSL();
        dslQuery.createUpdateable().update(dArticleInfoEntity).set(SetFilter.set(dArticleInfoEntity.aiLikeCount, article.getAiLikeCount() + 1)).where(dArticleInfoEntity.aiId.eq(articleId)).execute();
        return article.getAiLikeCount() + 1;
    }

    /**
     * 查询文章详情
     * 检查文章有效性
     *
     * @param articleId 文章编号
     * @return
     * @throws KnowledgeException
     */
    @ResourceLoad
    @ResourceField(name = "convertImage", source = "aiId", type = ResourceType.ARTICLE_COVERT_IMAGE)
    public ArticleInfoDTO selectArticleDetail(String articleId) throws KnowledgeException {
        ArticleInfoDTO article = detail(articleId, true);
        // 设置文章留言总数
        Long commentCount = commentService.countByArticle(articleId);
        article.setCommentCount(commentCount);
        return article;
    }

    /**
     * 更新阅读数量
     *
     * @param articleId   文章编号
     * @param readCount   文章原阅读数量
     * @param chanegCount 阅读量更新数量
     * @throws KnowledgeException
     */
    public void updateArticleReadCount(String articleId, int readCount, int chanegCount) throws KnowledgeException {
        DArticleInfoEntity dArticleInfoEntity = DArticleInfoEntity.DSL();
        dslQuery.createUpdateable()
                .update(dArticleInfoEntity)
                .set(SetFilter.set(dArticleInfoEntity.aiReadCount, readCount + chanegCount))
                .where(dArticleInfoEntity.aiId.eq(articleId))
                .execute();
    }

    /**
     * 查询文章详情
     *
     * @param articleId     文章编号
     * @param effectiveness 有效性，true：检查文章有效性，false：不检查
     * @return
     * @throws KnowledgeException
     */
    public ArticleInfoDTO detail(String articleId, boolean effectiveness) throws KnowledgeException {
        DArticleInfoEntity dArticleInfoEntity = DArticleInfoEntity.DSL();
        ArticleInfoDTO article = dslQuery.createSearchable()
                .selectFrom(dArticleInfoEntity)
                .where(dArticleInfoEntity.aiId.eq(articleId))
                .resultType(ArticleInfoDTO.class)
                .fetchOne();
        // 检查存在性
        if (ObjectUtils.isEmpty(article)) {
            throw new KnowledgeException("文章不存在.");
        }

        // 检查有效性
        boolean efResult = effectiveness && (Constant.NO.equals(article.getAiIsRelease()) || Constant.DELETE.equals(article.getAiStatus()));
        if (efResult) {
            throw new KnowledgeException("文章已下架.");
        }
        return article;
    }

    /**
     * 同文推荐列表
     *
     * @return
     */
    @ResourceLoad
    @ResourceField(name = "convertImage", source = "aiId", type = ResourceType.ARTICLE_COVERT_IMAGE)
    public List<ArticleInfoDTO> similarRecommend(String articleId) throws KnowledgeException {
        List<ArticleInfoDTO> articles = new ArrayList();
        // 查询文章所属的专题列表
        List<ArticleTopicInfoEntity> topics = articleTopicInfoService.selectByArticleId(articleId);
        if (!ObjectUtils.isEmpty(topics)) {
            for (ArticleTopicInfoEntity topic : topics) {
                // 查询同文每一个专题下文章列表
                Page<ArticleInfoDTO> topicPage = PageableRequest.of(1, 3).request(() -> selectByTopicId(topic.getAtiId()));
                List<ArticleInfoDTO> topicArticles = topicPage.getData();
                // 存在数据时
                if (!ObjectUtils.isEmpty(topicArticles)) {
                    articles.addAll(topicArticles);
                }
            }
        }
        // 不存在专题文章时
        if (ObjectUtils.isEmpty(articles)) {
            articles.addAll(pageableOfNewArticleList(1, 3));
        }
        return articles;
    }

    /**
     * 查询专题下的文章列表
     *
     * @param topicId 专题编号
     * @return
     * @throws KnowledgeException
     */
    @ResourceLoad
    @ResourceField(name = "convertImage", source = "aiId", type = ResourceType.ARTICLE_COVERT_IMAGE)
    public List<ArticleInfoDTO> selectByTopicId(String topicId) throws KnowledgeException {
        DArticleInfoEntity dArticleInfoEntity = DArticleInfoEntity.DSL();
        DArticleTopicUniEntity dArticleTopicUniEntity = DArticleTopicUniEntity.DSL();
        // 新文靠前
        return dslQuery.createSearchable().selectFrom(dArticleInfoEntity).leftJoin(dArticleInfoEntity.aiId, dArticleTopicUniEntity.atuArticleId).where(dArticleTopicUniEntity.atuTopicId.eq(topicId)).and(dArticleInfoEntity.aiStatus.eq(Constant.OK)).and(dArticleInfoEntity.aiIsRelease.eq(Constant.YES)).orderBy(dArticleInfoEntity.aiCreateTime, SortEnum.DESC).resultType(ArticleInfoDTO.class).fetch();
    }

    /**
     * 分页查询推荐文章列表
     *
     * @param pageIndex 分页参数：当前页码
     * @param pageSize  分页参数：每页条数
     * @return
     * @throws KnowledgeException
     */
    public List<ArticleInfoDTO> pageableOfRecommendArticleList(int pageIndex, int pageSize) throws KnowledgeException {
        return articleInfoService.selectArticleList(ArticleQueryParam.builder().pageIndex(pageIndex).pageSize(pageSize).isRecommend(Constant.YES).build());
    }

    /**
     * 分页查询热门文章列表
     *
     * @param pageIndex 分页参数：当前页码
     * @param pageSize  分页参数：每页条数
     * @return
     * @throws KnowledgeException
     */
    public List<ArticleInfoDTO> pageableOfHotArticleList(int pageIndex, int pageSize) throws KnowledgeException {
        return articleInfoService.selectArticleList(ArticleQueryParam.builder().pageSize(pageSize).pageIndex(pageIndex).isHot(Constant.YES).build());
    }

    /**
     * 分页查询专题下文章列表
     *
     * @param pageIndex 分页参数：当前页码
     * @param pageSize  分页参数：每页条数
     * @return
     * @throws KnowledgeException
     */
    public List<ArticleInfoDTO> pageableOfTopicArticleList(int pageIndex, int pageSize, String topicId) throws KnowledgeException {
        return articleInfoService.selectArticleList(ArticleQueryParam.builder().pageIndex(pageIndex).pageSize(pageSize).topicId(topicId).build());
    }

    /**
     * 分页查询新发布文章列表
     *
     * @param pageIndex 分页参数：当前页码
     * @param pageSize  分页参数：每页条数
     * @return
     * @throws KnowledgeException
     */
    public List<ArticleInfoDTO> pageableOfNewArticleList(int pageIndex, int pageSize) throws KnowledgeException {
        return articleInfoService.selectArticleList(ArticleQueryParam.builder().pageSize(pageSize).pageIndex(pageIndex).isNew(Constant.YES).build());
    }

    /**
     * 根据关键字分页查询文章列表
     *
     * @param pageIndex 分页参数：当前页码
     * @param pageSize  分页参数：每页条数
     * @return
     * @throws KnowledgeException
     */
    public List<ArticleInfoDTO> pageableOfKeyWordArticleList(String keyword, int pageIndex, int pageSize) throws KnowledgeException {
        return articleInfoService.selectArticleList(ArticleQueryParam.builder().pageIndex(pageIndex).pageSize(pageSize).keyword(keyword).build());
    }

    /**
     * 查询文章列表
     *
     * @param param 查询参数
     * @return
     * @throws KnowledgeException
     */
    @ResourceLoad
    @ResourceField(name = "convertImage", source = "aiId", type = ResourceType.ARTICLE_COVERT_IMAGE)
    public List<ArticleInfoDTO> selectArticleList(ArticleQueryParam param) throws KnowledgeException {
        // 文章动态查询实体
        DArticleInfoEntity dArticleInfoEntity = DArticleInfoEntity.DSL();
        // 构建查询对象
        Searchable searchable = dslQuery.createSearchable().selectFrom(dArticleInfoEntity).where(dArticleInfoEntity.aiStatus.eq(Constant.OK)).and(dArticleInfoEntity.aiIsRelease.eq(Constant.YES));
        // 热门查询条件
        if (!StringUtils.isEmpty(param.getIsHot())) {
            searchable.and(dArticleInfoEntity.aiIsHot.eq(param.getIsHot()));
        }
        // 推荐查询条件
        if (!StringUtils.isEmpty(param.getIsRecommend())) {
            searchable.and(dArticleInfoEntity.aiIsRecommend.eq(param.getIsRecommend()));
        }
        // 关键字查询
        if (!StringUtils.isEmpty(param.getKeyword())) {
            searchable.and(dArticleInfoEntity.aiTitle.like("%", param.getKeyword(), "%"));
        }
        // 专题编号
        if (!StringUtils.isEmpty(param.getTopicId())) {
            DArticleTopicUniEntity dArticleTopicUniEntity = DArticleTopicUniEntity.DSL();
            searchable.leftJoin(dArticleInfoEntity.aiId, dArticleTopicUniEntity.atuArticleId)
                    .and(dArticleTopicUniEntity.atuTopicId.eq(param.getTopicId()));
        }

        // 非查询最新文章，根据阅读量排序
        if (StringUtils.isEmpty(param.getIsNew())) {
            searchable.orderBy(dArticleInfoEntity.aiReadCount, SortEnum.DESC);
        }
        // 查询最新文章，根据创建时间排序
        else {
            searchable.orderBy(dArticleInfoEntity.aiCreateTime, SortEnum.DESC);
        }
        return /*searchable.limit(param.getPageSize()).offset((param.getPageIndex() - 1) * param.getPageSize())*/searchable.resultType(ArticleInfoDTO.class).fetch();
    }

    public ArticleInfoEntity selectByTitle(String articleTitle) {
        DArticleInfoEntity dArticleInfoEntity = DArticleInfoEntity.DSL();
        return dslQuery.createSearchable()
                .selectFrom(dArticleInfoEntity)
                .where(dArticleInfoEntity.aiTitle.eq(articleTitle))
                .resultType(ArticleInfoEntity.class)
                .fetchOne();
    }

    @ResourceLoad(event = ResourceStoreEvent.INSERT)
    @ResourceField(name = "#p1", source = "#p0.aiId", type = ResourceType.ARTICLE_COVERT_IMAGE)
    public void addArticle(ArticleInfoEntity articleInfoEntity, String covertImage) {
        articleInfoMapper.insert(articleInfoEntity);
    }
}
