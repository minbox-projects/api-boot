package org.minbox.framework.api.boot.autoconfigure.quartz;

import org.minbox.framework.api.boot.quartz.ApiBootQuartzService;
import org.minbox.framework.api.boot.quartz.support.ApiBootQuartzServiceDefaultSupport;
import org.quartz.Scheduler;
import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.transaction.PlatformTransactionManagerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * ApiBoot Quartz 自动化配置类
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-27 15:24
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@EnableConfigurationProperties(ApiBootQuartzProperties.class)
@ConditionalOnClass({Scheduler.class, SchedulerFactoryBean.class, PlatformTransactionManagerCustomizer.class, ApiBootQuartzService.class})
@AutoConfigureAfter({DataSourceAutoConfiguration.class, HibernateJpaAutoConfiguration.class})
public class ApiBootQuartzAutoConfiguration {

    private ApiBootQuartzProperties properties;
    private ObjectProvider<List<SchedulerFactoryBeanCustomizer>> customizers;

    public ApiBootQuartzAutoConfiguration(ApiBootQuartzProperties properties, ObjectProvider<List<SchedulerFactoryBeanCustomizer>> customizers) {
        this.properties = properties;
        this.customizers = customizers;
    }

    /**
     * 实例化ApiBoot Quartz Service
     *
     * @param scheduler Quartz Scheduler Instance
     * @return ApiBootQuartzService
     */
    @Bean
    @ConditionalOnMissingBean(ApiBootQuartzService.class)
    public ApiBootQuartzService apiBootQuartzService(Scheduler scheduler) {
        return new ApiBootQuartzServiceDefaultSupport(scheduler);
    }

    /**
     * APIBoot Quartz Bean Factory
     *
     * @return ApiBootQuartzSpringBeanJobFactory
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiBootQuartzSpringBeanJobFactory apiBootQuartzSpringBeanJobFactory() {
        return new ApiBootQuartzSpringBeanJobFactory();
    }

    /**
     * Quartz Factory Bean
     *
     * @return SchedulerFactoryBean
     */
    @Bean
    @ConditionalOnMissingBean
    public SchedulerFactoryBean quartzScheduler(ApiBootQuartzSpringBeanJobFactory jobFactory) {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();

        schedulerFactoryBean.setJobFactory(jobFactory);
        schedulerFactoryBean.setAutoStartup(this.properties.isAutoStartup());
        schedulerFactoryBean.setStartupDelay((int) this.properties.getStartupDelay().getSeconds());
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(this.properties.isWaitForJobsToCompleteOnShutdown());
        schedulerFactoryBean.setOverwriteExistingJobs(this.properties.isOverwriteExistingJobs());
        if (!this.properties.getProperties().isEmpty()) {
            schedulerFactoryBean.setQuartzProperties(this.asProperties(this.properties.getProperties()));
        }

        // 自定义配置
        this.customize(schedulerFactoryBean);
        return schedulerFactoryBean;
    }


    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    private void customize(SchedulerFactoryBean schedulerFactoryBean) {
        this.customizers.getIfAvailable().stream().forEach((customizer) -> customizer.customize(schedulerFactoryBean));
    }

    /**
     * ApiBoot Quartz Bean Factory
     *
     * @author 恒宇少年
     * @date 2019-3-30
     */
    protected static class ApiBootQuartzSpringBeanJobFactory extends SpringBeanJobFactory implements ApplicationContextAware {
        /**
         * bean factory
         */
        private AutowireCapableBeanFactory beanFactory;

        /**
         * Set Application Context
         *
         * @param applicationContext Spring Context
         * @throws BeansException Bean Exception
         */
        @Override
        public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
            this.beanFactory = applicationContext.getAutowireCapableBeanFactory();
        }

        @Override
        protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
            Object jobInstance = super.createJobInstance(bundle);
            beanFactory.autowireBean(jobInstance);
            return jobInstance;
        }
    }

    /**
     * Job Store Type Config
     */
    @Configuration
    @ConditionalOnSingleCandidate(DataSource.class)
    protected static class JdbcStoreTypeConfiguration {
        protected JdbcStoreTypeConfiguration() {
        }

        /**
         * properties needed to initialize Jdbc mode
         *
         * @return
         */
        @Bean
        @Order(0)
        public SchedulerFactoryBeanCustomizer jobPropertiesCustomizer(ApiBootQuartzProperties properties) {
            return schedulerFactoryBean -> {
                // jdbc away
                if (properties.getJobStoreType() == JobStoreType.JDBC) {

                    ApiBootQuartzProperties.Prop prop = properties.getProp();
                    // get prop class declared fields
                    Field[] fields = prop.getClass().getDeclaredFields();
                    Arrays.stream(fields).forEach(field -> {
                        try {
                            field.setAccessible(true);
                            String value = String.valueOf(field.get(prop));
                            PropKey propKey = field.getDeclaredAnnotation(PropKey.class);

                            // put prop to quartz properties
                            properties.getProperties().put(propKey.value(), value);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }

                    });
                }
            };
        }

        @Bean
        @Order(1)
        public SchedulerFactoryBeanCustomizer jobDataSourceCustomizer(ApiBootQuartzProperties properties, DataSource dataSource, @QuartzDataSource ObjectProvider<DataSource> quartzDataSource, ObjectProvider<PlatformTransactionManager> transactionManager) {
            return (schedulerFactoryBean) -> {
                // 数据源配置
                if (properties.getJobStoreType() == JobStoreType.JDBC) {
                    DataSource dataSourceToUse = this.getDataSource(dataSource, quartzDataSource);
                    schedulerFactoryBean.setDataSource(dataSourceToUse);
                    PlatformTransactionManager txManager = transactionManager.getIfUnique();
                    if (txManager != null) {
                        schedulerFactoryBean.setTransactionManager(txManager);
                    }
                }

            };
        }

        private DataSource getDataSource(DataSource dataSource, ObjectProvider<DataSource> quartzDataSource) {
            DataSource dataSourceIfAvailable = quartzDataSource.getIfAvailable();
            return dataSourceIfAvailable != null ? dataSourceIfAvailable : dataSource;
        }
    }
}
