package org.minbox.framework.api.boot.plugin.quartz.wrapper.support;


import lombok.Builder;
import lombok.Getter;
import org.minbox.framework.api.boot.plugin.quartz.wrapper.ApiBootJobParamWrapper;
import org.minbox.framework.api.boot.plugin.quartz.wrapper.ApiBootJobWrapper;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * Once job wrapper class，extend {@link ApiBootJobWrapper}
 *
 * @author 恒宇少年
 */
public class ApiBootOnceJobWrapper extends ApiBootJobWrapper {
    /**
     * Constructor initialization {@link ApiBootOnceJobWrapper}
     *
     * @param jobKey      {@link ApiBootJobWrapper#getJobKey()}
     * @param jobClass    {@link ApiBootJobWrapper#getJobClass()}
     * @param startAtTime {@link ApiBootJobWrapper#getStartAtTime()}
     * @param param       {@link ApiBootJobWrapper#getParam()}
     */
    @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
    public ApiBootOnceJobWrapper(String jobKey, Class<? extends QuartzJobBean> jobClass, Date startAtTime, ApiBootJobParamWrapper param) {
        super(jobKey, jobClass, startAtTime, param);
    }
}
