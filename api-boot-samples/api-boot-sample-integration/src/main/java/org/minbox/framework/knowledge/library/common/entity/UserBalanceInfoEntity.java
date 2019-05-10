package org.minbox.framework.knowledge.library.common.entity;
import java.sql.Timestamp;
import java.math.BigDecimal;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_balance_info - 用户账户信息表 - 数据实体</p>
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
@Table(name = "kl_user_balance_info")
@ApiModel
public class UserBalanceInfoEntity {
    /**
     * UBI_ID - 账户主键
     */
    @Id(generatorType = KeyGeneratorTypeEnum.UUID)
    @Column(name="UBI_ID")
    @ApiModelProperty("账户主键")
    private String ubiId;
    /**
     * UBI_USER_ID - 用户编号，关联kl_user_info主键
     */
    @Column(name="UBI_USER_ID")
    @ApiModelProperty("用户编号，关联kl_user_info主键")
    private String ubiUserId;
    /**
     * UBI_TYPE_ID - 账户类型编号，关联kl_balance_type主键
     */
    @Column(name="UBI_TYPE_ID")
    @ApiModelProperty("账户类型编号，关联kl_balance_type主键")
    private String ubiTypeId;
    /**
     * UBI_BALANCE - 账户余额，保留两位小数点
     */
    @Column(name="UBI_BALANCE")
    @ApiModelProperty("账户余额，保留两位小数点")
    private BigDecimal ubiBalance;
    /**
     * UBI_IS_LOCK - 账户是否锁定，Y：已锁定，N：未锁定
     */
    @Column(name="UBI_IS_LOCK",insertable = false)
    @ApiModelProperty("账户是否锁定，Y：已锁定，N：未锁定")
    private String ubiIsLock;
    /**
     * UBI_CREATE_TIME - 创建时间
     */
    @Column(name="UBI_CREATE_TIME",insertable = false)
    @ApiModelProperty("创建时间")
    private Timestamp ubiCreateTime;
    /**
     * UBI_MARK - 备注信息
     */
    @Column(name="UBI_MARK")
    @ApiModelProperty("备注信息")
    private String ubiMark;
}