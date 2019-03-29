package org.minbox.framework.api.boot.plugin.sms.request;

import com.alibaba.fastjson.JSON;
import lombok.Getter;

import java.util.HashMap;

/**
 * ApiBoot 发送短信请求参数
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-22 11:27
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Getter
public class ApiBootSmsRequestParam {
    /**
     * 参数集合
     */
    private static final HashMap<String, Object> params = new HashMap();

    /**
     * 写入参数到集合
     *
     * @param name  参数名
     * @param value 参数值
     * @return 本类实例
     */
    public ApiBootSmsRequestParam put(String name, Object value) {
        params.put(name, value);
        return this;
    }

    /**
     * 参数转换为json字符串
     *
     * @return json
     */
    public String getParamJson() {
        return JSON.toJSONString(params);
    }

}
