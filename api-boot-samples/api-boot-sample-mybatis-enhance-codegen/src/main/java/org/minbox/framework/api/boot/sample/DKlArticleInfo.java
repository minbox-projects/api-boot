package org.minbox.framework.api.boot.sample;

import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;

/**
 * 文章信息表
 * @author ApiBoot Mybatis Enhance Codegen
 */
public class DKlArticleInfo extends TableExpression<KlArticleInfo> {

    public DKlArticleInfo(String root) {
        super(root);
    }

    public static DKlArticleInfo DSL() {
        return new DKlArticleInfo("kl_article_info");
    }

    /**
     * 主键自增
     */
    public ColumnExpression aiId = new ColumnExpression("AI_ID", this);
    /**
     * 文章所属用户
     */
    public ColumnExpression aiUserId = new ColumnExpression("AI_USER_ID", this);
    /**
     * 文章标题
     */
    public ColumnExpression aiTitle = new ColumnExpression("AI_TITLE", this);
    /**
     * 阅读量
     */
    public ColumnExpression aiReadCount = new ColumnExpression("AI_READ_COUNT", this);
    /**
     * 喜欢数量
     */
    public ColumnExpression aiLikeCount = new ColumnExpression("AI_LIKE_COUNT", this);
    /**
     * 评论数量
     */
    public ColumnExpression aiCommentCount = new ColumnExpression("AI_COMMENT_COUNT", this);
    /**
     * 分享数量
     */
    public ColumnExpression aiShareCount = new ColumnExpression("AI_SHARE_COUNT", this);
    /**
     * 文章内容
     */
    public ColumnExpression aiContent = new ColumnExpression("AI_CONTENT", this);
    /**
     * 是否为原创文章，Y：原创，N：转载
     */
    public ColumnExpression aiIsOriginal = new ColumnExpression("AI_IS_ORIGINAL", this);
    /**
     * 文章是否发布，Y：已发布，N：未发布
     */
    public ColumnExpression aiIsRelease = new ColumnExpression("AI_IS_RELEASE", this);
    /**
     * 是否热门,Y：热门，N：非热门
     */
    public ColumnExpression aiIsHot = new ColumnExpression("AI_IS_HOT", this);
    /**
     * 是否置顶，Y：置顶，N：普通
     */
    public ColumnExpression aiIsTop = new ColumnExpression("AI_IS_TOP", this);
    /**
     * 是否推荐，Y：推荐，N：不推荐
     */
    public ColumnExpression aiIsRecommend = new ColumnExpression("AI_IS_RECOMMEND", this);
    /**
     * 是否为markdown语法文章
     */
    public ColumnExpression aiIsMarkdown = new ColumnExpression("AI_IS_MARKDOWN", this);
    /**
     * 发布时间
     */
    public ColumnExpression aiReleaseTime = new ColumnExpression("AI_RELEASE_TIME", this);
    /**
     * 文章状态，O：正常，D：已删除
     */
    public ColumnExpression aiStatus = new ColumnExpression("AI_STATUS", this);
    /**
     * 备注信息
     */
    public ColumnExpression aiMark = new ColumnExpression("AI_MARK", this);
    /**
     * 文章创建时间
     */
    public ColumnExpression aiCreateTime = new ColumnExpression("AI_CREATE_TIME", this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{aiId, aiUserId, aiTitle, aiReadCount, aiLikeCount, aiCommentCount, aiShareCount, aiContent, aiIsOriginal, aiIsRelease, aiIsHot, aiIsTop, aiIsRecommend, aiIsMarkdown, aiReleaseTime, aiStatus, aiMark, aiCreateTime};
    }

}

