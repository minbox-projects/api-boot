package org.minbox.framework.api.boot.plugin.sms.request;

import com.alibaba.fastjson.JSON;
import lombok.Getter;

import java.util.HashMap;

/**
 * ApiBoot Send SMS Request Parameter Encapsulation Object
 *
 * @author 恒宇少年
 */
@Getter
public class ApiBootSmsRequestParam {
    /**
     * parameters
     */
    private static final HashMap<String, Object> params = new HashMap();

    /**
     * put one parameter to {@link #params}
     *
     * @param name  the parameter name
     * @param value the parameter value
     * @return {@link ApiBootSmsRequestParam} this object instance
     */
    public ApiBootSmsRequestParam put(String name, Object value) {
        params.put(name, value);
        return this;
    }

    /**
     * get the {@link #params} after formatting the json string
     *
     * @return parameters json string
     */
    public String getParamJson() {
        return JSON.toJSONString(params);
    }

}
