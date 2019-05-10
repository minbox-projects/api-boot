package org.minbox.framework.knowledge.library.common.entity;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_balance_type - 用户账户类型 - 数据实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：Apr 9, 2019 1:08:02 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
@Data
@Table(name = "kl_user_balance_type")
@ApiModel
public class UserBalanceTypeEntity {
    /**
     * UBT_ID - 账户类型主键
     */
    @Id(generatorType = KeyGeneratorTypeEnum.UUID)
    @Column(name="UBT_ID")
    @ApiModelProperty("账户类型主键")
    private String ubtId;
    /**
     * UBT_NAME - 账户类型名称
     */
    @Column(name="UBT_NAME")
    @ApiModelProperty("账户类型名称")
    private String ubtName;
    /**
     * UBT_FLAG - 账户类型标识
     */
    @Column(name="UBT_FLAG")
    @ApiModelProperty("账户类型标识")
    private String ubtFlag;
    /**
     * UBT_MARK - 备注信息
     */
    @Column(name="UBT_MARK")
    @ApiModelProperty("备注信息")
    private String ubtMark;
}