package org.minbox.framework.api.boot.sample.quartz;

import com.alibaba.fastjson.JSON;
import org.minbox.framework.api.boot.quartz.ApiBootQuartzService;
import org.minbox.framework.api.boot.quartz.wrapper.ApiBootJobParamWrapper;
import org.minbox.framework.api.boot.quartz.wrapper.support.ApiBootCronJobWrapper;
import org.minbox.framework.api.boot.quartz.wrapper.support.ApiBootLoopJobWrapper;
import org.minbox.framework.api.boot.quartz.wrapper.support.ApiBootOnceJobWrapper;
import org.minbox.framework.api.boot.sample.quartz.jobs.DemoJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * ApiBoot Quartz 示例控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-29 10:03
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
public class ApiBootQuartzController {
    /**
     * 注入ApiBoot Quartz Service
     */
    @Autowired
    private ApiBootQuartzService apiBootQuartzService;

    /**
     * 创建loop 任务
     *
     * @return 任务 Job Key
     */
    @GetMapping(value = "/start-loop-job")
    public String startLoopJob() {
        return apiBootQuartzService.newJob(
                ApiBootLoopJobWrapper.Context()
                        // 参数
                        .param(
                                ApiBootJobParamWrapper.wrapper()
                                        .put("userName", "恒宇少年")
                                        .put("userAge", 24)
                        )
                        // 每次循环的间隔时间，单位：毫秒
                        .loopIntervalTime(2000L)
                        // 循环次数
                        .repeatTimes(5)
                        // 开始时间，10秒后执行
                        .startAtTime(new Date(System.currentTimeMillis() + 10000))
                        // 任务类
                        .jobClass(DemoJob.class)
                        .wrapper()
        );
    }

    /**
     * 创建Once Job
     *
     * @return 任务Job Key
     */
    @GetMapping(value = "/start-once-job")
    public String startOnceJob() {
        Map paramMap = new HashMap(1);
        paramMap.put("paramKey", "参数值");

        return apiBootQuartzService.newJob(
                ApiBootOnceJobWrapper.Context()
                        .jobClass(DemoJob.class)
                        // 参数
                        .param(
                                ApiBootJobParamWrapper.wrapper()
                                        .put("mapJson", JSON.toJSONString(paramMap))
                        )
                        // 开始时间，2秒后执行
                        .startAtTime(new Date(System.currentTimeMillis() + 2000))
                        .wrapper()
        );
    }

    /**
     * 创建Cron Job
     *
     * @return 任务Job Key
     */
    @GetMapping(value = "/start-cron-job")
    public String startCronJob() {

        return apiBootQuartzService.newJob(
                ApiBootCronJobWrapper.Context()
                        .jobClass(DemoJob.class)
                        .cron("0/5 * * * * ?")
                        .param(
                                ApiBootJobParamWrapper.wrapper()
                                        .put("param", "测试")
                        )
                        .wrapper()
        );
    }

    /**
     * 删除任务
     *
     * @param jobKey 任务 Job Key
     */
    @GetMapping(value = "/delete-job")
    public void deleteJob(String jobKey) {
        apiBootQuartzService.deleteJob(jobKey);
    }

    /**
     * 暂停任务
     *
     * @param jobKey 任务Job Key
     */
    @GetMapping(value = "/pause-job")
    public void pauseJob(String jobKey) {
        apiBootQuartzService.pauseJob(jobKey);
    }

    /**
     * 恢复任务
     *
     * @param jobKey 任务Job Key
     */
    @GetMapping(value = "/resume-job")
    public void resumeJob(String jobKey) {
        apiBootQuartzService.resumeJob(jobKey);
    }

    /**
     * 更新Cron任务的Cron表达式
     *
     * @param jobKey 任务 Job Key
     */
    @GetMapping(value = "/update-cron")
    public void updateCron(String jobKey) {
        apiBootQuartzService.updateJobCron(jobKey, "0/5 * * * * ?");
    }
}
