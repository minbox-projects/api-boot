package org.minbox.framework.knowledge.library.service.user.service;

import com.gitee.hengboy.mybatis.enhance.sort.SortEnum;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceLoad;
import org.minbox.framework.knowledge.library.common.base.BaseService;
import org.minbox.framework.knowledge.library.common.dsl.DArticleInfoEntity;
import org.minbox.framework.knowledge.library.common.dsl.DUserReadRecordEntity;
import org.minbox.framework.knowledge.library.common.entity.UserReadRecordEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.article.dto.ArticleInfoDTO;
import org.minbox.framework.knowledge.library.service.constants.ResourceType;
import org.minbox.framework.knowledge.library.service.user.mapper.UserReadRecordMapper;
import org.minbox.framework.knowledge.library.service.user.struct.UserReadRecordStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.List;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-09 17:27
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserReadRecordService extends BaseService {
    /**
     * 阅读记录数据接口
     */
    @Autowired
    private UserReadRecordMapper userReadRecordMapper;

    @Autowired
    private UserReadRecordService userReadRecordService;

    /**
     * 是否阅读文章
     *
     * @param userId    用户编号
     * @param articleId 文章编号
     * @return true：阅读过，false：未阅读
     * @throws KnowledgeException
     */
    public UserReadRecordEntity selectReadRecord(String userId, String articleId) throws KnowledgeException {
        // 文章阅读记录动态查询实体
        DUserReadRecordEntity dArticleReadRecordEntity = DUserReadRecordEntity.DSL();
        return dslQuery.createSearchable()
                .selectFrom(dArticleReadRecordEntity)
                .where(dArticleReadRecordEntity.urrArticleId.eq(articleId))
                .and(dArticleReadRecordEntity.urrUserId.eq(userId))
                .limit(1)
                .resultType(UserReadRecordEntity.class)
                .fetchOne();
    }

    /**
     * 执行添加阅读记录
     * 如果已经存阅读记录进行更新时间
     * 如果不存在执行添加
     *
     * @param userId    用户编号
     * @param articleId 文章编号
     * @throws KnowledgeException
     */
    public void insertReadRecord(String userId, String articleId) throws KnowledgeException {
        UserReadRecordEntity readRecordEntity = selectReadRecord(userId, articleId);
        if (ObjectUtils.isEmpty(readRecordEntity)) {
            // 执行添加阅读记录
            readRecordEntity = UserReadRecordStruct.INSTANCE.from(userId, articleId);
            userReadRecordMapper.insert(readRecordEntity);
        } else {
            // 更新阅读时间为当前时间
            readRecordEntity.setUrrCreateTime(new Timestamp(System.currentTimeMillis()));
            userReadRecordMapper.update(readRecordEntity);
        }
    }

    /**
     * 查询当前访问接口用户阅读记录
     * Mybatis Pageable 自动分页
     *
     * @param userId    用户编号
     * @param pageIndex 当前页码
     * @param pageSize  每页条数
     * @return
     * @throws KnowledgeException
     */
    public List<ArticleInfoDTO> pageableOfReadRecordByUserId(String userId, int pageIndex, int pageSize) throws KnowledgeException {
        return userReadRecordService.selectReadRecordByUserId(userId, pageIndex, pageSize);
    }

    /**
     * 根据用户编号查询阅读记录列表
     * 不分页
     *
     * @param userId 用户编号
     * @return
     * @throws KnowledgeException
     */
    @ResourceLoad
    @ResourceField(name = "convertImage", source = "aiId", type = ResourceType.ARTICLE_COVERT_IMAGE)
    public List<ArticleInfoDTO> selectReadRecordByUserId(String userId, int pageIndex, int pageSize) throws KnowledgeException {
        // 文章动态查询实体
        DArticleInfoEntity dArticleInfoEntity = DArticleInfoEntity.DSL();
        // 文章阅读记录动态查询实体
        DUserReadRecordEntity dArticleReadRecordEntity = DUserReadRecordEntity.DSL();

        // 查询用于阅读记录
        return dslQuery.createSearchable()
                .selectFrom(dArticleInfoEntity)
                .leftJoin(dArticleInfoEntity.aiId, dArticleReadRecordEntity.urrArticleId)
                .where(dArticleReadRecordEntity.urrUserId.eq(userId))
                .orderBy(dArticleReadRecordEntity.urrCreateTime, SortEnum.DESC)
                .limit(pageSize)
                .offset((pageIndex - 1) * pageSize)
                .resultType(ArticleInfoDTO.class)
                .fetch();
    }
}
