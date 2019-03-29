package org.minbox.framework.api.boot.plugin.quartz;

import org.minbox.framework.api.boot.plugin.quartz.wrapper.ApiBootJobWrapper;
import org.quartz.Scheduler;
import org.quartz.SchedulerException;

import java.util.Collection;
import java.util.Date;

/**
 * ApiBoot 集成 Quartz 操作任务接口方法定义
 * 添加集成任务添加、暂停、修改、删除等
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-26 16:25
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface ApiBootQuartzService {
    /**
     * 获取Quartz 调度器对象实例
     *
     * @return Scheduler
     * @throws SchedulerException 调度器异常
     */
    Scheduler getScheduler() throws SchedulerException;

    /**
     * 创建一个新任务
     *
     * @param jobWrapper 定时任务封装对象
     * @return Job Key
     * @throws SchedulerException 调度器异常
     */
    String newJob(ApiBootJobWrapper jobWrapper);

    /**
     * 删除一个任务
     *
     * @param jobKey Job Key
     * @throws SchedulerException 调度器异常
     */
    void deleteJob(String jobKey);

    /**
     * 删除一系列任务
     *
     * @param jobKeys Job Key Array
     * @throws SchedulerException 调度器异常
     */
    void deleteJobs(String... jobKeys);

    /**
     * 删除集合内的所有任务
     *
     * @param jobKeys Job Key Collection
     * @throws SchedulerException 调度器异常
     */
    void deleteJobs(Collection<String> jobKeys);

    /**
     * 暂停一个任务
     *
     * @param jobKey Job Key
     * @throws SchedulerException 调度器异常
     */
    void pauseJob(String jobKey);

    /**
     * 暂停传递的所有任务
     *
     * @param jobKeys Job Key Array
     * @throws SchedulerException 调度器异常
     */
    void pauseJobs(String... jobKeys);

    /**
     * 暂停集合内的所有任务
     *
     * @param jobKeys Job Key Collection
     * @throws SchedulerException 调度器异常
     */
    void pauseJobs(Collection<String> jobKeys);

    /**
     * 恢复任务执行
     *
     * @param jobKey Job Key
     */
    void resumeJob(String jobKey);

    /**
     * 恢复数组内的所有任务执行
     *
     * @param jobKeys Job Key Array
     */
    void resumeJobs(String... jobKeys);

    /**
     * 恢复集合内的所有任务执行
     *
     * @param jobKeys Job Key Collection
     */
    void resumeJobs(Collection<String> jobKeys);

    /**
     * 更新任务Cron表达式
     *
     * @param jobKey Job Key
     * @param cron   Job Cron Expression
     * @throws SchedulerException 调度器异常
     */
    void updateJobCron(String jobKey, String cron);

    /**
     * 更新任务开始时间
     *
     * @param jobKey       Job Key
     * @param jobStartTime Job New Start Time
     * @throws SchedulerException 调度器异常
     */
    void updateJobStartTime(String jobKey, Date jobStartTime);

    /**
     * 启动所有定时任务
     *
     * @throws SchedulerException
     */
    void startAllJobs() throws SchedulerException;

    /**
     * 关闭所有定时任务
     *
     * @throws SchedulerException
     */
    void shutdownAllJobs() throws SchedulerException;
}
