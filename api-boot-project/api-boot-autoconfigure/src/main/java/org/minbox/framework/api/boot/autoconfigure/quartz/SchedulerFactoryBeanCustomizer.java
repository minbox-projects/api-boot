package org.minbox.framework.api.boot.autoconfigure.quartz;

import org.springframework.scheduling.quartz.SchedulerFactoryBean;

import javax.sql.DataSource;

/**
 * Callback interface that can be implemented by beans wishing to customize the Quartz
 * {@link SchedulerFactoryBean} before it is fully initialized, in particular to tune its
 * configuration.
 * <p>
 * For customization of the {@link DataSource} used by Quartz, use of
 * {@link QuartzDataSource @QuartzDataSource} is preferred. It will ensure consistent
 * customization of both the {@link SchedulerFactoryBean} and the
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-30 13:14
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@FunctionalInterface
public interface SchedulerFactoryBeanCustomizer {
    /**
     * Customize the {@link SchedulerFactoryBean}.
     *
     * @param schedulerFactoryBean the scheduler to customize
     */
    void customize(SchedulerFactoryBean schedulerFactoryBean);
}
