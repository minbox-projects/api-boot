package org.minbox.framework.api.boot.quartz.wrapper;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * 任务参数封装对象
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-30 09:45
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
public class ApiBootJobParamWrapper {
    /**
     * 定时任务执行时的参数
     */
    private static Map<String, Object> param = new HashMap();

    /**
     * 实例化参数对象
     *
     * @return ApiBootJobParamWrapper Instance
     */
    public static ApiBootJobParamWrapper wrapper() {
        return new ApiBootJobParamWrapper();
    }

    /**
     * Put new param to map
     *
     * @param name  param name
     * @param value param value
     * @return this object
     */
    public ApiBootJobParamWrapper put(String name, Object value) {
        param.put(name, value);
        return this;
    }

    /**
     * Get all params
     *
     * @return map instance
     */
    public Map<String, Object> getAllParam() {
        return param;
    }
}
