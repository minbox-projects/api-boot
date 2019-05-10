package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_comment_info - 文章评论信息表 - 动态查询实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：Apr 9, 2019 1:08:01 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
public class DCommentInfoEntity extends TableExpression<CommentInfoEntity> {
    public DCommentInfoEntity(String root) {
        super(root);
    }
    public static final DCommentInfoEntity DSL(){return new DCommentInfoEntity("kl_comment_info");}
    public ColumnExpression ciId = new ColumnExpression("CI_ID",this);
    public ColumnExpression ciArticleId = new ColumnExpression("CI_ARTICLE_ID",this);
    public ColumnExpression ciCreatorId = new ColumnExpression("CI_CREATOR_ID",this);
    public ColumnExpression ciCommentId = new ColumnExpression("CI_COMMENT_ID",this);
    public ColumnExpression ciTargetId = new ColumnExpression("CI_TARGET_ID",this);
    public ColumnExpression ciContent = new ColumnExpression("CI_CONTENT",this);
    public ColumnExpression ciIsAuthor = new ColumnExpression("CI_IS_AUTHOR",this);
    public ColumnExpression ciLikeCount = new ColumnExpression("CI_LIKE_COUNT",this);
    public ColumnExpression ciCommentCount = new ColumnExpression("CI_COMMENT_COUNT",this);
    public ColumnExpression ciCreateTime = new ColumnExpression("CI_CREATE_TIME",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            ciId
            ,
            ciArticleId
            ,
            ciCreatorId
            ,
            ciCommentId
            ,
            ciTargetId
            ,
            ciContent
            ,
            ciIsAuthor
            ,
            ciLikeCount
            ,
            ciCommentCount
            ,
            ciCreateTime
            
        };
    }
}
