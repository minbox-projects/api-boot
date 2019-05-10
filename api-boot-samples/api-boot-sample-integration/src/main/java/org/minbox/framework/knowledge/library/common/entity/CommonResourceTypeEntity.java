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
 * <p>表名: kl_common_resource_type - 统一资源类型 - 数据实体</p>
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
@Table(name = "kl_common_resource_type")
@ApiModel
public class CommonResourceTypeEntity {
    /**
     * CRT_ID - 资源编号
     */
    @Id(generatorType = KeyGeneratorTypeEnum.UUID)
    @Column(name="CRT_ID")
    @ApiModelProperty("资源编号")
    private String crtId;
    /**
     * CRT_NAME - 资源类型名称
     */
    @Column(name="CRT_NAME")
    @ApiModelProperty("资源类型名称")
    private String crtName;
    /**
     * CRT_FLAG - 资源类型标识
     */
    @Column(name="CRT_FLAG")
    @ApiModelProperty("资源类型标识")
    private String crtFlag;
    /**
     * CRT_CREATE_TIME - 创建时间
     */
    @Column(name="CRT_CREATE_TIME",insertable = false)
    @ApiModelProperty("创建时间")
    private Timestamp crtCreateTime;
    /**
     * CRT_MARK - 备注信息
     */
    @Column(name="CRT_MARK")
    @ApiModelProperty("备注信息")
    private String crtMark;
}