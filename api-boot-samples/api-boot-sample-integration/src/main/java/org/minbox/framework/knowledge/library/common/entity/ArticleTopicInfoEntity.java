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
 * <p>表名: kl_article_topic_info - 文章专题信息表 - 数据实体</p>
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
@Table(name = "kl_article_topic_info")
@ApiModel
public class ArticleTopicInfoEntity {
    /**
     * ATI_ID - 专题主键
     */
    @Id(generatorType = KeyGeneratorTypeEnum.UUID)
    @Column(name="ATI_ID")
    @ApiModelProperty("专题主键")
    private String atiId;
    /**
     * ATI_NAME - 专题名称
     */
    @Column(name="ATI_NAME")
    @ApiModelProperty("专题名称")
    private String atiName;
    /**
     * ATI_SORT - 排序字段，值越大越靠前
     */
    @Column(name="ATI_SORT")
    @ApiModelProperty("排序字段，值越大越靠前")
    private Integer atiSort;
    /**
     * ATI_STATUS - 专题状态，O：正常，D：已删除
     */
    @Column(name="ATI_STATUS",insertable = false)
    @ApiModelProperty("专题状态，O：正常，D：已删除")
    private String atiStatus;
    /**
     * ATI_CREATE_TIME - 创建时间
     */
    @Column(name="ATI_CREATE_TIME",insertable = false)
    @ApiModelProperty("创建时间")
    private Timestamp atiCreateTime;
    /**
     * ATI_MARK - 备注信息
     */
    @Column(name="ATI_MARK")
    @ApiModelProperty("备注信息")
    private String atiMark;
}