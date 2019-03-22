package org.minbox.framework.api.boot.plugin.sms;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.sms.request.ApiBootSmsRequest;
import org.minbox.framework.api.boot.plugin.sms.response.ApiBootSmsResponse;

/**
 * ApiBoot集成短信服务接口定义
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-22 11:20
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
public interface ApiBootSmsService {
    /**
     * 发送短信
     *
     * @param request 请求对象
     * @return 发送短信响应
     * @throws ApiBootException 异常信息
     */
    ApiBootSmsResponse send(ApiBootSmsRequest request) throws ApiBootException;
}
