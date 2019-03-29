package org.minbox.framework.api.boot.sample.quartz.jobs;

import com.alibaba.fastjson.JSON;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * 任务定义示例
 * 与Quartz使用方法一致，ApiBoot只是在原生基础上进行扩展，不影响原生使用
 * <p>
 * 继承QuartzJobBean抽象类后会在项目启动时会自动加入Spring IOC
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-28 17:26
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class DemoJob extends QuartzJobBean {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(DemoJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("定时任务Job Key ： {}", context.getJobDetail().getKey());
        logger.info("定时任务执行时所携带的参数：{}", JSON.toJSONString(context.getJobDetail().getJobDataMap()));
        //...处理逻辑
    }
}
