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
 * <p>表名: kl_article_topic_uni - 文章专题关联信息表 - 数据实体</p>
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
@Table(name = "kl_article_topic_uni")
@ApiModel
public class ArticleTopicUniEntity {
    /**
     * ATU_ID - 
     */
    @Id(generatorType = KeyGeneratorTypeEnum.AUTO)
    @Column(name="ATU_ID")
    @ApiModelProperty("")
    private Integer atuId;
    /**
     * ATU_ARTICLE_ID - 文章编号，关联kl_article_info主键
     */
    @Column(name="ATU_ARTICLE_ID")
    @ApiModelProperty("文章编号，关联kl_article_info主键")
    private String atuArticleId;
    /**
     * ATU_TOPIC_ID - 文章专题，关联kl_article_topic_info主键
     */
    @Column(name="ATU_TOPIC_ID")
    @ApiModelProperty("文章专题，关联kl_article_topic_info主键")
    private Integer atuTopicId;
    /**
     * ATU_CREATE_TIME - 创建时间
     */
    @Column(name="ATU_CREATE_TIME",insertable = false)
    @ApiModelProperty("创建时间")
    private Timestamp atuCreateTime;
}