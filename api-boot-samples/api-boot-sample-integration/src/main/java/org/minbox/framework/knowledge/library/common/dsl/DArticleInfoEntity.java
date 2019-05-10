package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_article_info - 文章信息表 - 动态查询实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：Apr 10, 2019 1:23:07 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
public class DArticleInfoEntity extends TableExpression<ArticleInfoEntity> {
    public DArticleInfoEntity(String root) {
        super(root);
    }
    public static final DArticleInfoEntity DSL(){return new DArticleInfoEntity("kl_article_info");}
    public ColumnExpression aiId = new ColumnExpression("AI_ID",this);
    public ColumnExpression aiUserId = new ColumnExpression("AI_USER_ID",this);
    public ColumnExpression aiTitle = new ColumnExpression("AI_TITLE",this);
    public ColumnExpression aiReadCount = new ColumnExpression("AI_READ_COUNT",this);
    public ColumnExpression aiLikeCount = new ColumnExpression("AI_LIKE_COUNT",this);
    public ColumnExpression aiCommentCount = new ColumnExpression("AI_COMMENT_COUNT",this);
    public ColumnExpression aiShareCount = new ColumnExpression("AI_SHARE_COUNT",this);
    public ColumnExpression aiContent = new ColumnExpression("AI_CONTENT",this);
    public ColumnExpression aiIsOriginal = new ColumnExpression("AI_IS_ORIGINAL",this);
    public ColumnExpression aiIsRelease = new ColumnExpression("AI_IS_RELEASE",this);
    public ColumnExpression aiIsHot = new ColumnExpression("AI_IS_HOT",this);
    public ColumnExpression aiIsTop = new ColumnExpression("AI_IS_TOP",this);
    public ColumnExpression aiIsRecommend = new ColumnExpression("AI_IS_RECOMMEND",this);
    public ColumnExpression aiIsMarkdown = new ColumnExpression("AI_IS_MARKDOWN",this);
    public ColumnExpression aiReleaseTime = new ColumnExpression("AI_RELEASE_TIME",this);
    public ColumnExpression aiStatus = new ColumnExpression("AI_STATUS",this);
    public ColumnExpression aiMark = new ColumnExpression("AI_MARK",this);
    public ColumnExpression aiCreateTime = new ColumnExpression("AI_CREATE_TIME",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            aiId
            ,
            aiUserId
            ,
            aiTitle
            ,
            aiReadCount
            ,
            aiLikeCount
            ,
            aiCommentCount
            ,
            aiShareCount
            ,
            aiContent
            ,
            aiIsOriginal
            ,
            aiIsRelease
            ,
            aiIsHot
            ,
            aiIsTop
            ,
            aiIsRecommend
            ,
            aiIsMarkdown
            ,
            aiReleaseTime
            ,
            aiStatus
            ,
            aiMark
            ,
            aiCreateTime
            
        };
    }
}
