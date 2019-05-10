package org.minbox.framework.knowledge.library.common.entity;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_info - 用户基本信息 - 数据实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：Apr 9, 2019 1:27:51 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
@Data
@Table(name = "kl_user_info")
@ApiModel
public class UserInfoEntity implements Serializable {
    /**
     * UI_ID - 主键自增
     */
    @Id(generatorType = KeyGeneratorTypeEnum.UUID)
    @Column(name="UI_ID")
    @ApiModelProperty("主键自增")
    private String uiId;
    /**
     * UI_NICK_NAME - 用户昵称
     */
    @Column(name="UI_NICK_NAME")
    @ApiModelProperty("用户昵称")
    private String uiNickName;
    /**
     * UI_OPEN_ID - 用户微信openId
     */
    @Column(name="UI_OPEN_ID")
    @ApiModelProperty("用户微信openId")
    private String uiOpenId;
    /**
     * UI_PASSWORD - 用户密码
     */
    @Column(name="UI_PASSWORD")
    @ApiModelProperty("用户密码")
    private String uiPassword;
    /**
     * UI_LAST_LOGIN_TIME - 最后登录时间
     */
    @Column(name="UI_LAST_LOGIN_TIME")
    @ApiModelProperty("最后登录时间")
    private Timestamp uiLastLoginTime;
    /**
     * UI_STATUS - 用户状态，O：正常，D：已删除
     */
    @Column(name="UI_STATUS",insertable = false)
    @ApiModelProperty("用户状态，O：正常，D：已删除")
    private String uiStatus;
    /**
     * UI_IS_LOCK - 是否锁定，Y：锁定，N：未锁定
     */
    @Column(name="UI_IS_LOCK",insertable = false)
    @ApiModelProperty("是否锁定，Y：锁定，N：未锁定")
    private String uiIsLock;
    /**
     * UI_CREATE_TIME - 创建时间
     */
    @Column(name="UI_CREATE_TIME",insertable = false)
    @ApiModelProperty("创建时间")
    private Timestamp uiCreateTime;
    /**
     * UI_MARK - 备注信息
     */
    @Column(name="UI_MARK")
    @ApiModelProperty("备注信息")
    private String uiMark;
}
