package org.minbox.framework.knowledge.library.service.user.service;

import com.gitee.hengboy.mybatis.enhance.sort.SortEnum;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceLoad;
import org.minbox.framework.knowledge.library.common.base.BaseService;
import org.minbox.framework.knowledge.library.common.dsl.DArticleInfoEntity;
import org.minbox.framework.knowledge.library.common.dsl.DUserLikeRecordEntity;
import org.minbox.framework.knowledge.library.common.entity.UserLikeRecordEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.article.dto.ArticleInfoDTO;
import org.minbox.framework.knowledge.library.service.constants.ResourceType;
import org.minbox.framework.knowledge.library.service.user.mapper.UserLikeRecordMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * 喜欢文章业务逻辑
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 10:33
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserLikeRecordService extends BaseService {
    /**
     * 点赞记录
     */
    @Autowired
    private UserLikeRecordMapper userLikeRecordMapper;

    @Autowired
    private UserLikeRecordService userLikeRecordService;

    /**
     * 写入文章喜欢记录
     *
     * @param articleId 文章编号
     * @param userId    用户编号
     * @throws KnowledgeException
     */
    public void likeArticle(String articleId, String userId) throws KnowledgeException {
        boolean isLike = isLikeArticle(userId, articleId);
        if (isLike) {
            throw new KnowledgeException("不能重复喜欢我哟~");
        }
        // 写入喜欢记录
        UserLikeRecordEntity likeRecordEntity = new UserLikeRecordEntity();
        likeRecordEntity.setUlrArticleId(articleId);
        likeRecordEntity.setUlrUserId(userId);
        userLikeRecordMapper.insert(likeRecordEntity);
    }

    /**
     * 查询当前访问接口用户喜欢记录
     * Mybatis Pageable 自动分页
     *
     * @param userId    用户编号
     * @param pageIndex 当前页码
     * @param pageSize  每页条数
     * @return
     * @throws KnowledgeException
     */
    public List<ArticleInfoDTO> pageableOfLikeRecordByUserId(String userId, int pageIndex, int pageSize) throws KnowledgeException {
        return userLikeRecordService.selectLikeRecordByUserId(userId, pageIndex, pageSize);
    }

    /**
     * 是否喜欢文章
     *
     * @param userId    用户编号
     * @param articleId 文章编号
     * @return true：已经喜欢过，false：并未喜欢
     * @throws KnowledgeException
     */
    public boolean isLikeArticle(String userId, String articleId) throws KnowledgeException {
        // 文章喜欢记录动态查询实体
        DUserLikeRecordEntity dUserLikeRecordEntity = DUserLikeRecordEntity.DSL();
        Long count = dslQuery.createSearchable().count(dUserLikeRecordEntity.ulrId).from(dUserLikeRecordEntity).where(dUserLikeRecordEntity.ulrUserId.eq(userId)).and(dUserLikeRecordEntity.ulrArticleId.eq(articleId)).resultType(Long.class).fetchOne();
        return !ObjectUtils.isEmpty(count) && count > 0;
    }

    /**
     * 根据用户编号查询喜欢记录列表
     * 不分页
     *
     * @param userId 用户编号
     * @return
     * @throws KnowledgeException
     */
    @ResourceLoad
    @ResourceField(name = "convertImage", source = "aiId", type = ResourceType.ARTICLE_COVERT_IMAGE)
    public List<ArticleInfoDTO> selectLikeRecordByUserId(String userId, int pageIndex, int pageSize) throws KnowledgeException {
        // 文章动态查询实体
        DArticleInfoEntity dArticleInfoEntity = DArticleInfoEntity.DSL();
        // 文章喜欢记录动态查询实体
        DUserLikeRecordEntity dUserLikeRecordEntity = DUserLikeRecordEntity.DSL();

        // 查询用于喜欢记录
        return dslQuery.createSearchable()
                .selectFrom(dArticleInfoEntity)
                .leftJoin(dArticleInfoEntity.aiId, dUserLikeRecordEntity.ulrArticleId)
                .where(dUserLikeRecordEntity.ulrUserId.eq(userId))
                .orderBy(dUserLikeRecordEntity.ulrCreateTime, SortEnum.DESC)
                .limit(pageSize)
                .offset((pageIndex - 1) * pageSize)
                .resultType(ArticleInfoDTO.class)
                .fetch();
    }
}
