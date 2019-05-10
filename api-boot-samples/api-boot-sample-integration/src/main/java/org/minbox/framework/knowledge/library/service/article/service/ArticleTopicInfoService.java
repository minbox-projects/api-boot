package org.minbox.framework.knowledge.library.service.article.service;

import com.gitee.hengboy.mybatis.enhance.sort.SortEnum;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceLoad;
import org.minbox.framework.knowledge.library.common.base.BaseService;
import org.minbox.framework.knowledge.library.common.constants.Constant;
import org.minbox.framework.knowledge.library.common.dsl.DArticleTopicInfoEntity;
import org.minbox.framework.knowledge.library.common.dsl.DArticleTopicUniEntity;
import org.minbox.framework.knowledge.library.common.entity.ArticleTopicInfoEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.article.dto.ArticleTopicInfoDTO;
import org.minbox.framework.knowledge.library.service.constants.ResourceType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 文章专题业务逻辑
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 10:45
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class ArticleTopicInfoService extends BaseService {
    /**
     * 查询全部的文章主题
     *
     * @return
     * @throws KnowledgeException
     */
    @ResourceLoad
    @ResourceField(name = "topicIcon",source = "atiId",type = ResourceType.TOPIC)
    public List<ArticleTopicInfoDTO> selectAllTopic() throws KnowledgeException {
        DArticleTopicInfoEntity dArticleTopicInfoEntity = DArticleTopicInfoEntity.DSL();
        return dslQuery.createSearchable()
                .selectFrom(dArticleTopicInfoEntity)
                // 查询正常状态
                .where(dArticleTopicInfoEntity.atiStatus.eq(Constant.OK))
                // 排序
                .orderBy(dArticleTopicInfoEntity.atiSort, SortEnum.DESC)
                .resultType(ArticleTopicInfoDTO.class)
                .fetch();
    }

    /**
     * 查询文章所属专题列表
     *
     * @param articleId 文章编号
     * @return
     * @throws KnowledgeException
     */
    public List<ArticleTopicInfoEntity> selectByArticleId(String articleId) throws KnowledgeException {
        // 文章专题动态查询实体
        DArticleTopicInfoEntity dArticleTopicInfoEntity = DArticleTopicInfoEntity.DSL();
        // 文章 & 专题关联动态查询实体
        DArticleTopicUniEntity dArticleTopicUniEntity = DArticleTopicUniEntity.DSL();
        // 文章专题列表
        return dslQuery.createSearchable().selectFrom(dArticleTopicInfoEntity).leftJoin(dArticleTopicInfoEntity.atiId, dArticleTopicUniEntity.atuTopicId).where(dArticleTopicUniEntity.atuArticleId.eq(articleId)).resultType(ArticleTopicInfoEntity.class).fetch();
    }
}
