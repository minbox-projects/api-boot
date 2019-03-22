package org.minbox.framework.api.boot.plugin.sms.response;

import lombok.Builder;
import lombok.Data;

/**
 * ApiBoot发送短信时响应实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-22 11:24
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@Data
@Builder
public class ApiBootSmsResponse {
    /**
     * 是否发送成功
     */
    private boolean success;
}
