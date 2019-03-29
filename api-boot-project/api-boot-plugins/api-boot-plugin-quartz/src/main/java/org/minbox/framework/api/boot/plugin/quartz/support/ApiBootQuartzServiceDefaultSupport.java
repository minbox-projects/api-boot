package org.minbox.framework.api.boot.plugin.quartz.support;

import org.minbox.framework.api.boot.plugin.quartz.ApiBootQuartzService;
import org.minbox.framework.api.boot.plugin.quartz.wrapper.ApiBootJobWrapper;
import org.minbox.framework.api.boot.plugin.quartz.wrapper.support.ApiBootCronJobWrapper;
import org.minbox.framework.api.boot.plugin.quartz.wrapper.support.ApiBootLoopJobWrapper;
import org.minbox.framework.api.boot.plugin.quartz.wrapper.support.ApiBootOnceJobWrapper;
import org.quartz.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.UUID;

/**
 * ApiBoot Quartz Service 默认实现
 * 提供Quartz操作任务的基本方法实现：
 * - 创建任务
 * - 删除任务
 * - 更新任务Cron表达式
 * - 更新任务执行次数
 * - 暂停任务
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-27 10:18
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootQuartzServiceDefaultSupport implements ApiBootQuartzService {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootQuartzServiceDefaultSupport.class);

    /**
     * Quartz Scheduler Instance
     */
    private Scheduler scheduler;

    public ApiBootQuartzServiceDefaultSupport(Scheduler scheduler) {
        this.scheduler = scheduler;
    }

    /**
     * 获取Quartz Scheduler
     *
     * @return Scheduler Instance
     * @throws SchedulerException 调度器异常
     */
    @Override
    public Scheduler getScheduler() throws SchedulerException {
        return this.scheduler;
    }

    /**
     * 创建新任务
     *
     * @param jobWrapper 定时任务封装对象
     * @return Job Key
     */
    @Override
    public String newJob(ApiBootJobWrapper jobWrapper) {
        try {
            if (ObjectUtils.isEmpty(jobWrapper)) {
                throw new SchedulerException("When creating a new task, parameters must be passed.");
            }
            // 默认使用uuid作为Job Key
            if (StringUtils.isEmpty(jobWrapper.getJobKey())) {
                jobWrapper.setJobKey(UUID.randomUUID().toString());
            }
            // cron job
            if (jobWrapper instanceof ApiBootCronJobWrapper) {
                newCronJob((ApiBootCronJobWrapper) jobWrapper);
            }
            // loop job
            else if (jobWrapper instanceof ApiBootLoopJobWrapper) {
                newLoopJob((ApiBootLoopJobWrapper) jobWrapper);
            }
            // once job
            else if (jobWrapper instanceof ApiBootOnceJobWrapper) {
                newOnceJob((ApiBootOnceJobWrapper) jobWrapper);
            }
        } catch (Exception e) {
            logger.error("Create new job error.", e);
        }
        return jobWrapper.getJobKey();
    }

    /**
     * 删除任务
     *
     * @param jobKey Job Key
     */
    @Override
    public void deleteJob(String jobKey) {
        try {
            TriggerKey triggerKey = TriggerKey.triggerKey(jobKey);
            // 暂停触发器
            scheduler.pauseTrigger(triggerKey);
            // 移除触发器
            scheduler.unscheduleJob(triggerKey);
            // 删除任务
            scheduler.deleteJob(JobKey.jobKey(jobKey));
        } catch (Exception e) {
            logger.error("Delete job error.", e);
        }
    }

    /**
     * 删除数组内的所有任务
     *
     * @param jobKeys Job Key Array
     */
    @Override
    public void deleteJobs(String... jobKeys) {
        if (!ObjectUtils.isEmpty(jobKeys)) {
            Arrays.stream(jobKeys).forEach(jobKey -> deleteJob(jobKey));
        }
    }

    /**
     * 删除集合内的所有任务
     *
     * @param jobKeys Job Key Collection
     */
    @Override
    public void deleteJobs(Collection<String> jobKeys) {
        if (!ObjectUtils.isEmpty(jobKeys)) {
            jobKeys.stream().forEach(jobKey -> deleteJob(jobKey));
        }
    }

    /**
     * 暂停定时任务
     *
     * @param jobKey Job Key
     */
    @Override
    public void pauseJob(String jobKey) {
        try {
            scheduler.pauseJob(JobKey.jobKey(jobKey));
        } catch (Exception e) {
            logger.error("Pause job error.", e);
        }
    }

    /**
     * 暂停数组内的所有定时任务
     *
     * @param jobKeys Job Key Array
     */
    @Override
    public void pauseJobs(String... jobKeys) {
        if (!ObjectUtils.isEmpty(jobKeys)) {
            Arrays.stream(jobKeys).forEach(jobKey -> pauseJob(jobKey));
        }
    }

    /**
     * 暂停集合内的所有定时任务
     *
     * @param jobKeys Job Key Collection
     */
    @Override
    public void pauseJobs(Collection<String> jobKeys) {
        if (!ObjectUtils.isEmpty(jobKeys)) {
            jobKeys.stream().forEach(jobKey -> pauseJob(jobKey));
        }
    }

    /**
     * 恢复任务执行
     *
     * @param jobKey Job Key
     */
    @Override
    public void resumeJob(String jobKey) {
        try {
            scheduler.resumeJob(JobKey.jobKey(jobKey));
        } catch (Exception e) {
            logger.error("Resume job error.", e);
        }
    }

    /**
     * 恢复数组内的所有任务执行
     *
     * @param jobKeys Job Key Array
     */
    @Override
    public void resumeJobs(String... jobKeys) {
        if (!ObjectUtils.isEmpty(jobKeys)) {
            Arrays.stream(jobKeys).forEach(jobKey -> resumeJob(jobKey));
        }
    }

    /**
     * 恢复集合内的所有任务执行
     *
     * @param jobKeys Job Key Collection
     */
    @Override
    public void resumeJobs(Collection<String> jobKeys) {
        if (!ObjectUtils.isEmpty(jobKeys)) {
            jobKeys.stream().forEach(jobKey -> resumeJob(jobKey));
        }
    }

    /**
     * 更新定时任务Cron表达式
     *
     * @param jobKey Job Key
     * @param cron   Job Cron Expression
     */
    @Override
    public void updateJobCron(String jobKey, String cron) {
        try {
            CronTrigger cronTrigger = (CronTrigger) getTrigger(jobKey);
            // 如果表达式一致，不执行更新
            if (!cronTrigger.getCronExpression().equals(cron)) {
                TriggerKey triggerKey = TriggerKey.triggerKey(jobKey);
                cronTrigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(cron)).startNow().build();
                scheduler.rescheduleJob(triggerKey, cronTrigger);
            }
        } catch (Exception e) {
            logger.error("Update job cron error.", e);
        }
    }

    /**
     * 更新定时任务启动时间
     *
     * @param jobKey       Job Key
     * @param jobStartTime Job New Start Time
     */
    @Override
    public void updateJobStartTime(String jobKey, Date jobStartTime) {
        try {
            // old trigger
            Trigger trigger = getTrigger(jobKey);
            // update start time
            trigger = TriggerBuilder.newTrigger().withIdentity(trigger.getKey()).withSchedule(trigger.getScheduleBuilder()).startAt(jobStartTime).build();

            scheduler.rescheduleJob(trigger.getKey(), trigger);
        } catch (Exception e) {
            logger.error("Update job start time error", e);
        }
    }

    /**
     * 启动所有定时任务
     *
     * @throws SchedulerException 调度器异常
     */
    @Override
    public void startAllJobs() throws SchedulerException {
        scheduler.start();
    }

    /**
     * 关闭所有定时任务
     *
     * @throws SchedulerException 调度器异常
     */
    @Override
    public void shutdownAllJobs() throws SchedulerException {
        if (!scheduler.isShutdown()) {
            scheduler.shutdown();
        }
    }

    /**
     * 通过触发器Key名称获取触发器信息
     *
     * @param triggerKeyName Job Trigger Name
     * @return Trigger
     * @throws SchedulerException 调度器异常
     */
    protected Trigger getTrigger(String triggerKeyName) throws SchedulerException {
        Trigger trigger = scheduler.getTrigger(TriggerKey.triggerKey(triggerKeyName));
        if (ObjectUtils.isEmpty(trigger)) {
            throw new SchedulerException("Unable to get " + triggerKeyName + " information for task trigger");
        }
        return trigger;
    }

    /**
     * 通过定时任务key名称查询定时任务
     *
     * @param jobKey Job Key Name
     * @return JobDetail
     * @throws SchedulerException 调度器异常
     */
    protected JobDetail getJobDetail(String jobKey) throws SchedulerException {
        JobDetail jobDetail = scheduler.getJobDetail(JobKey.jobKey(jobKey));
        if (ObjectUtils.isEmpty(jobDetail)) {
            throw new SchedulerException("Unable to get " + jobKey + " information for task jobDetail");
        }
        return jobDetail;
    }

    /**
     * 获取一个新的JobKey对象
     *
     * @param jobKey Job Key Name
     * @return JobKey
     * @throws SchedulerException 调度器异常
     */
    protected JobKey newJobKey(String jobKey) throws SchedulerException {
        return JobKey.jobKey(jobKey);
    }

    /**
     * New Job Detail
     *
     * @param wrapper ApiBoot Job Wrapper
     * @return Job Detail
     */
    protected JobDetail newJobDetail(ApiBootJobWrapper wrapper) {
        // job detail
        JobDetail jobDetail = JobBuilder.newJob(wrapper.getJobClass()).withIdentity(wrapper.getJobKey()).build();
        if (!ObjectUtils.isEmpty(wrapper.getParam())) {
            // put params
            jobDetail.getJobDataMap().putAll(wrapper.getParam().getAllParam());
        }
        return jobDetail;
    }

    /**
     * 获取一个新的JobKey对象
     *
     * @param triggerKey Trigger Key Name
     * @return TriggerKey
     * @throws SchedulerException 调度器异常
     */
    protected TriggerKey newTriggerKey(String triggerKey) throws SchedulerException {
        return TriggerKey.triggerKey(triggerKey);
    }


    /**
     * 创建一个Cron表达式任务
     *
     * @param wrapper Cron表达式任务封装对象
     * @return Date
     * @throws SchedulerException 调度器异常
     */
    protected Date newCronJob(ApiBootCronJobWrapper wrapper) throws SchedulerException {
        // new trigger key
        TriggerKey triggerKey = newTriggerKey(wrapper.getJobKey());

        // job detail
        JobDetail jobDetail = newJobDetail(wrapper);
        // trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(CronScheduleBuilder.cronSchedule(wrapper.getCron())).build();

        return scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 创建一个Loop循环执行任务
     *
     * @param wrapper Loop任务封装对象
     * @return Date
     * @throws SchedulerException 调度器异常
     */
    protected Date newLoopJob(ApiBootLoopJobWrapper wrapper) throws SchedulerException {
        // new trigger key
        TriggerKey triggerKey = newTriggerKey(wrapper.getJobKey());

        // job detail
        JobDetail jobDetail = newJobDetail(wrapper);
        // trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(SimpleScheduleBuilder.simpleSchedule().withRepeatCount(wrapper.getRepeatTimes()).withIntervalInMilliseconds(wrapper.getLoopIntervalTime())).startAt(wrapper.getStartAtTime()).build();

        return scheduler.scheduleJob(jobDetail, trigger);
    }

    /**
     * 创建Once任务
     *
     * @param wrapper Once任务封装对象
     * @return Date
     * @throws SchedulerException 调度器异常
     */
    protected Date newOnceJob(ApiBootOnceJobWrapper wrapper) throws SchedulerException {
        // new trigger key
        TriggerKey triggerKey = newTriggerKey(wrapper.getJobKey());

        // new job detail
        JobDetail jobDetail = newJobDetail(wrapper);
        // new trigger
        Trigger trigger = TriggerBuilder.newTrigger().withIdentity(triggerKey).withSchedule(SimpleScheduleBuilder.simpleSchedule()).startAt(wrapper.getStartAtTime()).build();

        return scheduler.scheduleJob(jobDetail, trigger);
    }
}
