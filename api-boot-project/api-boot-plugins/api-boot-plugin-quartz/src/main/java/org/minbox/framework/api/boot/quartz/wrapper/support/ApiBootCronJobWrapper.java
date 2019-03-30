package org.minbox.framework.api.boot.quartz.wrapper.support;

import lombok.Builder;
import lombok.Getter;
import org.minbox.framework.api.boot.quartz.wrapper.ApiBootJobParamWrapper;
import org.minbox.framework.api.boot.quartz.wrapper.ApiBootJobWrapper;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * CRON任务类型封装
 *
 * @author：于起宇 <p>
 * ================================
 * Created with IDEA.
 * Date：2019-01-23
 * Time：13:42
 * 个人博客：http://blog.yuqiyu.com
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 * </p>
 */
@Getter
public class ApiBootCronJobWrapper extends ApiBootJobWrapper {
    /**
     * cron表达式
     * 任务执行方式如果为CRON_EXPRESSION时，该方法必须调用并设置值
     */
    private String cron;

    /**
     * 构造函数初始化父类的相关字段
     *
     * @param jobKey       任务key
     * @param cron         cron表达式
     * @param jobClass     执行任务类
     * @param param 参数封装对象
     */
    @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
    public ApiBootCronJobWrapper(String jobKey, Class<? extends QuartzJobBean> jobClass, String cron, ApiBootJobParamWrapper param) {
        super(jobKey, jobClass, null, param);
        this.cron = cron;
    }
}
