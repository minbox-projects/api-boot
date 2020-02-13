package org.minbox.framework.api.boot.plugin.quartz.wrapper.support;

import lombok.Builder;
import lombok.Getter;
import org.minbox.framework.api.boot.plugin.quartz.wrapper.ApiBootJobParamWrapper;
import org.minbox.framework.api.boot.plugin.quartz.wrapper.ApiBootJobWrapper;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * Loop job wrapper class，extend {@link ApiBootJobWrapper}
 *
 * @author 恒宇少年
 */
public class ApiBootLoopJobWrapper extends ApiBootJobWrapper {
    /**
     * Job execution repeat times
     */
    private int repeatTimes;
    /**
     * Interval between repeated Job
     * <p>
     * Type changed from int to Long
     * https://gitee.com/minbox-projects/api-boot/issues/I18HTM
     */
    private Long loopIntervalTime;

    /**
     * Constructor initialization {@link ApiBootLoopJobWrapper}
     *
     * @param jobKey           {@link ApiBootJobWrapper#getJobKey()}
     * @param jobClass         {@link ApiBootJobWrapper#getJobClass()}
     * @param repeatTimes      {@link ApiBootLoopJobWrapper#getRepeatTimes()}
     * @param loopIntervalTime {@link ApiBootLoopJobWrapper#getLoopIntervalTime()}
     * @param startAtTime      {@link ApiBootJobWrapper#getStartAtTime()}
     * @param param            {@link ApiBootJobWrapper#getParam()}
     */
    @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
    public ApiBootLoopJobWrapper(String jobKey, Class<? extends QuartzJobBean> jobClass, int repeatTimes, Long loopIntervalTime, Date startAtTime, ApiBootJobParamWrapper param) {
        super(jobKey, jobClass, startAtTime, param);
        this.repeatTimes = repeatTimes;
        this.loopIntervalTime = loopIntervalTime;
    }

    /**
     * Get interval time in milliseconds
     *
     * @return interval time
     */
    public Long getLoopIntervalTime() {
        return loopIntervalTime <= 0 ? 1000L : loopIntervalTime;
    }

    public int getRepeatTimes() {
        return repeatTimes;
    }
}
