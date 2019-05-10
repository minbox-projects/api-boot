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
 * <p>表名: kl_common_resource - 统一资源 - 数据实体</p>
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
@Table(name = "kl_common_resource")
@ApiModel
public class CommonResourceEntity {
    /**
     * CR_ID - 主键自增
     */
    @Id(generatorType = KeyGeneratorTypeEnum.AUTO)
    @Column(name="CR_ID")
    @ApiModelProperty("主键自增")
    private Integer crId;
    /**
     * CR_TYPE_ID - 资源类型，关联kl_common_resource_type主键
     */
    @Column(name="CR_TYPE_ID")
    @ApiModelProperty("资源类型，关联kl_common_resource_type主键")
    private String crTypeId;
    /**
     * CR_TARGET_ID - 资源所属目标编号
     */
    @Column(name="CR_TARGET_ID")
    @ApiModelProperty("资源所属目标编号")
    private String crTargetId;
    /**
     * CR_URL - 资源路径
     */
    @Column(name="CR_URL")
    @ApiModelProperty("资源路径")
    private String crUrl;
    /**
     * CR_CREATE_TIME - 创建时间
     */
    @Column(name="CR_CREATE_TIME",insertable = false)
    @ApiModelProperty("创建时间")
    private Timestamp crCreateTime;
}