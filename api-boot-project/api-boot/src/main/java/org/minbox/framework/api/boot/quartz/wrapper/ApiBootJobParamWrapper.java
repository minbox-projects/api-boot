package org.minbox.framework.api.boot.quartz.wrapper;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Job execution param wrapper class
 *
 * @author 恒宇少年
 */
@Data
public class ApiBootJobParamWrapper {
    /**
     * job params
     */
    private static Map<String, Object> param = new HashMap();

    /**
     * Disable constructor instantiation
     */
    private ApiBootJobParamWrapper() {
    }

    /**
     * Create a instance
     *
     * @return {@link ApiBootJobParamWrapper}
     */
    public static ApiBootJobParamWrapper wrapper() {
        return new ApiBootJobParamWrapper();
    }

    /**
     * Put new param to map
     *
     * @param name  param name
     * @param value param value
     * @return {@link ApiBootJobParamWrapper} current object instance
     */
    public ApiBootJobParamWrapper put(String name, Object value) {
        param.put(name, value);
        return this;
    }

    /**
     * Get all params
     *
     * @return {@link #param}
     */
    public Map<String, Object> getAllParam() {
        return param;
    }
}
