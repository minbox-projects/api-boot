package org.minbox.framework.knowledge.library.common.entity;
import java.sql.Timestamp;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_comment_info - 文章评论信息表 - 数据实体</p>
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
@Data
@Table(name = "kl_comment_info")
@ApiModel
public class CommentInfoEntity {
    /**
     * CI_ID - 留言主键
     */
    @Id(generatorType = KeyGeneratorTypeEnum.UUID)
    @Column(name="CI_ID")
    @ApiModelProperty("留言主键")
    private String ciId;
    /**
     * CI_ARTICLE_ID - 文章编号，关联kl_article_info主键
     */
    @Column(name="CI_ARTICLE_ID")
    @ApiModelProperty("文章编号，关联kl_article_info主键")
    private String ciArticleId;
    /**
     * CI_CREATOR_ID - 评论创建者，关联kl_user_info主键
     */
    @Column(name="CI_CREATOR_ID")
    @ApiModelProperty("评论创建者，关联kl_user_info主键")
    private String ciCreatorId;
    /**
     * CI_COMMENT_ID - 上级评论编号，关联本表主键
     */
    @Column(name="CI_COMMENT_ID")
    @ApiModelProperty("上级评论编号，关联本表主键")
    private String ciCommentId;
    /**
     * CI_TARGET_ID - 回复目标用户编号，关联kl_user_info主键
     */
    @Column(name="CI_TARGET_ID")
    @ApiModelProperty("回复目标用户编号，关联kl_user_info主键")
    private String ciTargetId;
    /**
     * CI_CONTENT - 留言内容
     */
    @Column(name="CI_CONTENT")
    @ApiModelProperty("留言内容")
    private String ciContent;
    /**
     * CI_IS_AUTHOR - 是否为作者评论，Y：作者评论，N：非作者
     */
    @Column(name="CI_IS_AUTHOR")
    @ApiModelProperty("是否为作者评论，Y：作者评论，N：非作者")
    private String ciIsAuthor;
    /**
     * CI_LIKE_COUNT - 评论喜欢数量
     */
    @Column(name="CI_LIKE_COUNT")
    @ApiModelProperty("评论喜欢数量")
    private Integer ciLikeCount;
    /**
     * CI_COMMENT_COUNT - 评论的评论、回复数量
     */
    @Column(name="CI_COMMENT_COUNT")
    @ApiModelProperty("评论的评论、回复数量")
    private Integer ciCommentCount;
    /**
     * CI_CREATE_TIME - 留言时间
     */
    @Column(name="CI_CREATE_TIME",insertable = false)
    @ApiModelProperty("留言时间")
    private Timestamp ciCreateTime;
}