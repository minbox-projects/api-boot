package org.minbox.framework.knowledge.library.service.comment.service;

import com.gitee.hengboy.mybatis.enhance.dsl.update.filter.SetFilter;
import org.minbox.framework.knowledge.library.common.base.BaseService;
import org.minbox.framework.knowledge.library.common.dsl.DCommentInfoEntity;
import org.minbox.framework.knowledge.library.common.entity.CommentInfoEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.comment.mapper.CommentInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

/**
 * 文章留言业务逻辑
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-11 11:33
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentService extends BaseService {
    /**
     * 数据接口
     */
    @Autowired
    private CommentInfoMapper commentInfoMapper;

    /**
     * 提交留言信息
     * 1、检查文章是否有效
     * 2、如果存在留言编号检查留言是否有效
     * 3、处理留言内容
     *
     * @param userId    用户编号
     * @param articleId 文章编号
     * @param commentId 留言编号
     * @param content   留言内容
     * @throws KnowledgeException
     */
    public void submit(String userId, String articleId, String commentId, String content) throws KnowledgeException {

    }

    /**
     * 文章总数
     *
     * @param articleId 文章编号
     * @return
     * @throws KnowledgeException
     */
    public Long countByArticle(String articleId) throws KnowledgeException {
        DCommentInfoEntity dCommentInfoEntity = DCommentInfoEntity.DSL();
        return dslQuery.createSearchable()
                .count(dCommentInfoEntity.ciId)
                .from(dCommentInfoEntity)
                .where(dCommentInfoEntity.ciArticleId.eq(articleId))
                .resultType(Long.class)
                .fetchOne();
    }

    /**
     * 喜欢留言
     *
     * @param commentId 留言编号
     * @throws KnowledgeException
     */
    public void likeComment(String commentId) throws KnowledgeException {
        // 查询留言信息
        CommentInfoEntity comment = commentInfoMapper.selectOne(commentId);
        if (ObjectUtils.isEmpty(comment)) {
            throw new KnowledgeException("该条留言已失效，无法点赞.");
        }
        // 执行更新留言喜欢数量
        DCommentInfoEntity dCommentInfoEntity = DCommentInfoEntity.DSL();
        dslQuery.createUpdateable()
                .update(dCommentInfoEntity)
                .set(SetFilter.set(dCommentInfoEntity.ciLikeCount, comment.getCiLikeCount() + 1))
                .where(dCommentInfoEntity.ciId.eq(commentId))
                .execute();
    }
}
