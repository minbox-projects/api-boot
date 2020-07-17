package org.minbox.framework.api.boot.quartz;


import org.minbox.framework.api.boot.quartz.wrapper.ApiBootJobWrapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;
import org.minbox.framework.api.boot.quartz.wrapper.support.ApiBootCronJobWrapper;
import java.util.Collection;
import java.util.Date;

/**
 * ApiBoot integrated Quartz operation task interface method definition
 * Add integration tasksAdd, pause, modify, delete, etc.
 *
 * @author 恒宇少年
 * @see Scheduler
 */
public interface ApiBootQuartzService {
    /**
     * Get Quartz scheduler object instance
     *
     * @return {@link Scheduler}
     * @throws SchedulerException Scheduler Exception
     */
    Scheduler getScheduler() throws SchedulerException;

    /**
     * Create a new job
     *
     * @param jobWrapper {@link ApiBootJobWrapper}
     * @return {@link ApiBootJobWrapper#getJobKey()}
     * @see org.minbox.framework.api.boot.quartz.wrapper.support.ApiBootOnceJobWrapper
     * @see org.minbox.framework.api.boot.quartz.wrapper.support.ApiBootLoopJobWrapper
     * @see org.minbox.framework.api.boot.quartz.wrapper.support.ApiBootCronJobWrapper
     */
    String newJob(ApiBootJobWrapper jobWrapper);

    /**
     * Delete a job
     * pause the job first
     * then remove the job from the scheduler
     * finally delete the job
     *
     * @param jobKey {@link ApiBootJobWrapper#getJobKey()}
     */
    void deleteJob(String jobKey);

    /**
     * Delete based on the given jobKey array
     *
     * @param jobKeys {@link ApiBootJobWrapper#getJobKey()} job key array
     */
    void deleteJobs(String... jobKeys);

    /**
     * Delete based on the given jobKey collection
     *
     * @param jobKeys {@link ApiBootJobWrapper#getJobKey()} job key collection
     */
    void deleteJobs(Collection<String> jobKeys);

    /**
     * Pause job based on jobKey
     *
     * @param jobKey {@link ApiBootJobWrapper#getJobKey()}
     */
    void pauseJob(String jobKey);

    /**
     * Pause based on the given jobKey array
     *
     * @param jobKeys {@link ApiBootJobWrapper#getJobKey()} jobKey array
     */
    void pauseJobs(String... jobKeys);

    /**
     * Pause based on the given jobKey collection
     *
     * @param jobKeys {@link ApiBootJobWrapper#getJobKey()} jobKey collection
     */
    void pauseJobs(Collection<String> jobKeys);

    /**
     * Resume a job
     *
     * @param jobKey {@link ApiBootJobWrapper#getJobKey()}
     */
    void resumeJob(String jobKey);

    /**
     * Resume based on the given jobKey array
     *
     * @param jobKeys {@link ApiBootJobWrapper#getJobKey()} jobKey array
     */
    void resumeJobs(String... jobKeys);

    /**
     * Resume based on the given jobKey collection
     *
     * @param jobKeys {@link ApiBootJobWrapper#getJobKey()} jobKey collection
     */
    void resumeJobs(Collection<String> jobKeys);

    /**
     * Update job cron expression
     * This method works for <code>{@link ApiBootCronJobWrapper}</code>
     *
     * @param jobKey {@link ApiBootJobWrapper#getJobKey()}
     * @param cron   {@link ApiBootCronJobWrapper#getCron()}
     */
    void updateJobCron(String jobKey, String cron);

    /**
     * Update job start time
     * <p>
     * This method works for {@link org.minbox.framework.api.boot.quartz.wrapper.support.ApiBootOnceJobWrapper}
     * or {@link org.minbox.framework.api.boot.quartz.wrapper.support.ApiBootLoopJobWrapper}
     * </p>
     *
     * @param jobKey       {@link ApiBootJobWrapper#getJobKey()}
     * @param jobStartTime {@link ApiBootJobWrapper#getStartAtTime()} job startAtTime
     */
    void updateJobStartTime(String jobKey, Date jobStartTime);

    /**
     * Start all jobs in the <code>{@link Scheduler}</code>
     *
     * @throws SchedulerException Scheduler Exception
     */
    void startAllJobs() throws SchedulerException;

    /**
     * Shutdown all jobs in the <code>{@link Scheduler}</code>
     *
     * @throws SchedulerException Scheduler Exception
     */
    void shutdownAllJobs() throws SchedulerException;
}
