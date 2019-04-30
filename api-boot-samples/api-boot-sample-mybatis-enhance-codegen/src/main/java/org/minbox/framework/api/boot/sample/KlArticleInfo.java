package org.minbox.framework.api.boot.sample;

import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import lombok.Data;

import java.sql.Timestamp;

/**
 * 文章信息表
 * @author ApiBoot Mybatis Enhance Codegen
 */
@Data
@Table(name = "kl_article_info")
public class KlArticleInfo {

    /**
     * 主键自增
     */
    @Id(generatorType = KeyGeneratorTypeEnum.UUID)
    @Column(name = "AI_ID")
    private String aiId;
    /**
     * 文章所属用户
     */
    @Column(name = "AI_USER_ID")
    private String aiUserId;
    /**
     * 文章标题
     */
    @Column(name = "AI_TITLE")
    private String aiTitle;
    /**
     * 阅读量
     */
    @Column(name = "AI_READ_COUNT")
    private Integer aiReadCount;
    /**
     * 喜欢数量
     */
    @Column(name = "AI_LIKE_COUNT")
    private Integer aiLikeCount;
    /**
     * 评论数量
     */
    @Column(name = "AI_COMMENT_COUNT")
    private Integer aiCommentCount;
    /**
     * 分享数量
     */
    @Column(name = "AI_SHARE_COUNT")
    private Integer aiShareCount;
    /**
     * 文章内容
     */
    @Column(name = "AI_CONTENT")
    private String aiContent;
    /**
     * 是否为原创文章，Y：原创，N：转载
     */
    @Column(name = "AI_IS_ORIGINAL")
    private String aiIsOriginal;
    /**
     * 文章是否发布，Y：已发布，N：未发布
     */
    @Column(name = "AI_IS_RELEASE")
    private String aiIsRelease;
    /**
     * 是否热门,Y：热门，N：非热门
     */
    @Column(name = "AI_IS_HOT")
    private String aiIsHot;
    /**
     * 是否置顶，Y：置顶，N：普通
     */
    @Column(name = "AI_IS_TOP")
    private String aiIsTop;
    /**
     * 是否推荐，Y：推荐，N：不推荐
     */
    @Column(name = "AI_IS_RECOMMEND")
    private String aiIsRecommend;
    /**
     * 是否为markdown语法文章
     */
    @Column(name = "AI_IS_MARKDOWN")
    private String aiIsMarkdown;
    /**
     * 发布时间
     */
    @Column(name = "AI_RELEASE_TIME")
    private Timestamp aiReleaseTime;
    /**
     * 文章状态，O：正常，D：已删除
     */
    @Column(name = "AI_STATUS")
    private String aiStatus;
    /**
     * 备注信息
     */
    @Column(name = "AI_MARK")
    private String aiMark;
    /**
     * 文章创建时间
     */
    @Column(name = "AI_CREATE_TIME")
    private Timestamp aiCreateTime;
}

