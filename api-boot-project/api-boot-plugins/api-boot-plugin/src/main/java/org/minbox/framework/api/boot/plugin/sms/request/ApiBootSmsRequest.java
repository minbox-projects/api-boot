package org.minbox.framework.api.boot.plugin.sms.request;

import lombok.Builder;
import lombok.Data;

/**
 * ApiBoot 发送短信时请求实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-22 11:24
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@Builder
public class ApiBootSmsRequest {
    /**
     * 接收手机号
     */
    private String phone;
    /**
     * 模板Code
     * 对应阿里云控制台信息
     */
    private String templateCode;
    /**
     * 对应模板内的参数列表
     */
    private ApiBootSmsRequestParam param = new ApiBootSmsRequestParam();
}
