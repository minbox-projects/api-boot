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
 * <p>表名: kl_feedback - 意见反馈信息表 - 数据实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：Apr 25, 2019 2:42:31 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
@Data
@Table(name = "kl_feedback")
@ApiModel
public class FeedbackEntity implements Serializable {
    /**
     * KF_ID - 反馈主键自增
     */
    @Id(generatorType = KeyGeneratorTypeEnum.AUTO)
    @Column(name="KF_ID")
    @ApiModelProperty("反馈主键自增")
    private Integer kfId;
    /**
     * KF_USER_ID - 反馈用户编号，关联kl_user_info主键
     */
    @Column(name="KF_USER_ID")
    @ApiModelProperty("反馈用户编号，关联kl_user_info主键")
    private String kfUserId;
    /**
     * KF_CONTENT - 反馈内容
     */
    @Column(name="KF_CONTENT")
    @ApiModelProperty("反馈内容")
    private String kfContent;
    /**
     * KF_CREATE_TIME - 反馈时间
     */
    @Column(name="KF_CREATE_TIME",insertable = false)
    @ApiModelProperty("反馈时间")
    private Timestamp kfCreateTime;
}
