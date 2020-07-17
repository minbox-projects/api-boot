package org.minbox.framework.api.boot.quartz.wrapper.support;

import lombok.Builder;
import org.minbox.framework.api.boot.quartz.wrapper.ApiBootJobParamWrapper;
import org.minbox.framework.api.boot.quartz.wrapper.ApiBootJobWrapper;
import org.springframework.scheduling.quartz.QuartzJobBean;


/**
 * Cron job wrapper class，extend {@link ApiBootJobWrapper}
 *
 * @author 恒宇少年
 */
public class ApiBootCronJobWrapper extends ApiBootJobWrapper {
    /**
     * Job execution cron expression
     */
    private String cron;

    /**
     * Constructor initialization {@link ApiBootCronJobWrapper}
     *
     * @param jobKey   {@link ApiBootJobWrapper#getJobKey()}
     * @param jobClass {@link ApiBootJobWrapper#getJobClass()}
     * @param cron     {@link ApiBootCronJobWrapper#getCron()}
     * @param param    {@link ApiBootJobWrapper#getParam()}
     */
    @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
    public ApiBootCronJobWrapper(String jobKey, Class<? extends QuartzJobBean> jobClass, String cron, ApiBootJobParamWrapper param) {
        super(jobKey, jobClass, null, param);
        this.cron = cron;
    }

    public String getCron() {
        return cron;
    }
}
