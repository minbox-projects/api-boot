package org.minbox.framework.knowledge.library.common.entity;
import java.sql.Timestamp;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import java.io.Serializable;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_article_info - 文章信息表 - 数据实体</p>
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
@Data
@Table(name = "kl_article_info")
@ApiModel
public class ArticleInfoEntity implements Serializable {
    /**
     * AI_ID - 主键自增
     */
    @Id(generatorType = KeyGeneratorTypeEnum.UUID)
    @Column(name="AI_ID")
    @ApiModelProperty("主键自增")
    private String aiId;
    /**
     * AI_USER_ID - 文章所属用户
     */
    @Column(name="AI_USER_ID")
    @ApiModelProperty("文章所属用户")
    private String aiUserId;
    /**
     * AI_TITLE - 文章标题
     */
    @Column(name="AI_TITLE")
    @ApiModelProperty("文章标题")
    private String aiTitle;
    /**
     * AI_READ_COUNT - 阅读量
     */
    @Column(name="AI_READ_COUNT")
    @ApiModelProperty("阅读量")
    private Integer aiReadCount;
    /**
     * AI_LIKE_COUNT - 喜欢数量
     */
    @Column(name="AI_LIKE_COUNT")
    @ApiModelProperty("喜欢数量")
    private Integer aiLikeCount;
    /**
     * AI_COMMENT_COUNT - 评论数量
     */
    @Column(name="AI_COMMENT_COUNT")
    @ApiModelProperty("评论数量")
    private Integer aiCommentCount;
    /**
     * AI_SHARE_COUNT - 分享数量
     */
    @Column(name="AI_SHARE_COUNT")
    @ApiModelProperty("分享数量")
    private Integer aiShareCount;
    /**
     * AI_CONTENT - 文章内容
     */
    @Column(name="AI_CONTENT")
    @ApiModelProperty("文章内容")
    private String aiContent;
    /**
     * AI_IS_ORIGINAL - 是否为原创文章，Y：原创，N：转载
     */
    @Column(name="AI_IS_ORIGINAL")
    @ApiModelProperty("是否为原创文章，Y：原创，N：转载")
    private String aiIsOriginal;
    /**
     * AI_IS_RELEASE - 文章是否发布，Y：已发布，N：未发布
     */
    @Column(name="AI_IS_RELEASE")
    @ApiModelProperty("文章是否发布，Y：已发布，N：未发布")
    private String aiIsRelease;
    /**
     * AI_IS_HOT - 是否热门,Y：热门，N：非热门
     */
    @Column(name="AI_IS_HOT")
    @ApiModelProperty("是否热门,Y：热门，N：非热门")
    private String aiIsHot;
    /**
     * AI_IS_TOP - 是否置顶，Y：置顶，N：普通
     */
    @Column(name="AI_IS_TOP")
    @ApiModelProperty("是否置顶，Y：置顶，N：普通")
    private String aiIsTop;
    /**
     * AI_IS_RECOMMEND - 是否推荐，Y：推荐，N：不推荐
     */
    @Column(name="AI_IS_RECOMMEND")
    @ApiModelProperty("是否推荐，Y：推荐，N：不推荐")
    private String aiIsRecommend;
    /**
     * AI_IS_MARKDOWN - 是否为markdown语法文章
     */
    @Column(name="AI_IS_MARKDOWN")
    @ApiModelProperty("是否为markdown语法文章")
    private String aiIsMarkdown;
    /**
     * AI_RELEASE_TIME - 发布时间
     */
    @Column(name="AI_RELEASE_TIME")
    @ApiModelProperty("发布时间")
    private Timestamp aiReleaseTime;
    /**
     * AI_STATUS - 文章状态，O：正常，D：已删除
     */
    @Column(name="AI_STATUS",insertable = false)
    @ApiModelProperty("文章状态，O：正常，D：已删除")
    private String aiStatus;
    /**
     * AI_MARK - 备注信息
     */
    @Column(name="AI_MARK")
    @ApiModelProperty("备注信息")
    private String aiMark;
    /**
     * AI_CREATE_TIME - 文章创建时间
     */
    @Column(name="AI_CREATE_TIME",insertable = false)
    @ApiModelProperty("文章创建时间")
    private Timestamp aiCreateTime;
}
