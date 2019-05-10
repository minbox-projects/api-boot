package org.minbox.framework.knowledge.library.service.wx.model;

import com.alibaba.fastjson.annotation.JSONField;
import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 小程序jsCode登录后的实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-11 14:58
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@ApiModel
public class SmallProgramLoginResult {
    /**
     * 获取到的openId
     */
    @JSONField(name = "openid")
    private String openId;
    /**
     * 获取到的sessionKey
     */
    @JSONField(name = "session_key")
    private String sessionKey;
    /**
     * 获取到的unionid
     */
    @JSONField(name = "unionid")
    private String unionId;
    /**
     * 错误码
     */
    @JSONField(name = "errcode")
    private String errCode;
    /**
     * 错误消息
     */
    @JSONField(name = "errmsg")
    private String errMsg;
}
