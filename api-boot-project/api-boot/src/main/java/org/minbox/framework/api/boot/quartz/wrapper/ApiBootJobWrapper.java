package org.minbox.framework.api.boot.quartz.wrapper;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Create a parameter wrapper class for a job
 *
 * @author 恒宇少年
 */
@AllArgsConstructor
public class ApiBootJobWrapper implements Serializable {
    /**
     * Job unique key
     * Can operate jobs based on jobKey
     */
    @Setter
    private String jobKey;
    /**
     * Job execution class
     */
    private Class<? extends QuartzJobBean> jobClass;
    /**
     * Start execution time
     */
    private Date startAtTime;
    /**
     * Job execution params
     */
    private ApiBootJobParamWrapper param;

    /**
     * Get job start time
     * If {@link #startAtTime} is empty, the current time is used
     *
     * @return Job start time
     */
    public Date getStartAtTime() {
        return startAtTime == null ? new Date() : startAtTime;
    }

    public String getJobKey() {
        return jobKey;
    }

    public Class<? extends QuartzJobBean> getJobClass() {
        return jobClass;
    }

    public ApiBootJobParamWrapper getParam() {
        return param;
    }
}
