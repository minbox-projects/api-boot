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
 * <p>表名: kl_user_balance_record - 账户变动记录 - 数据实体</p>
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
@Table(name = "kl_user_balance_record")
@ApiModel
public class UserBalanceRecordEntity {
    /**
     * BR_ID - 
     */
    @Id(generatorType = KeyGeneratorTypeEnum.AUTO)
    @Column(name="BR_ID")
    @ApiModelProperty("")
    private Integer brId;
    /**
     * BR_BALANCE_ID - 所属账户编号，关联kl_balance_info主键
     */
    @Column(name="BR_BALANCE_ID")
    @ApiModelProperty("所属账户编号，关联kl_balance_info主键")
    private String brBalanceId;
    /**
     * BR_BEFORE - 变动之前的值
     */
    @Column(name="BR_BEFORE")
    @ApiModelProperty("变动之前的值")
    private BigDecimal brBefore;
    /**
     * BR_COUNT - 变动的值
     */
    @Column(name="BR_COUNT")
    @ApiModelProperty("变动的值")
    private BigDecimal brCount;
    /**
     * BR_AFTER - 变动后的值
     */
    @Column(name="BR_AFTER")
    @ApiModelProperty("变动后的值")
    private BigDecimal brAfter;
    /**
     * BR_CREATE_TIME - 创建时间
     */
    @Column(name="BR_CREATE_TIME",insertable = false)
    @ApiModelProperty("创建时间")
    private Timestamp brCreateTime;
    /**
     * BR_MARK - 备注信息
     */
    @Column(name="BR_MARK")
    @ApiModelProperty("备注信息")
    private String brMark;
}