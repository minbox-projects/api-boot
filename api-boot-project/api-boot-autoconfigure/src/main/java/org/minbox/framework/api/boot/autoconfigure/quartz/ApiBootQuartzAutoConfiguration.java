package org.minbox.framework.api.boot.autoconfigure.quartz;

import org.minbox.framework.api.boot.plugin.quartz.ApiBootQuartzService;
import org.minbox.framework.api.boot.plugin.quartz.support.ApiBootQuartzServiceDefaultSupport;
import org.quartz.Calendar;
import org.quartz.JobDetail;
import org.quartz.Scheduler;
import org.quartz.Trigger;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AbstractDependsOnBeanFactoryPostProcessor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnSingleCandidate;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.JobStoreType;
import org.springframework.boot.autoconfigure.quartz.QuartzAutoConfiguration;
import org.springframework.boot.autoconfigure.quartz.QuartzDataSource;
import org.springframework.boot.autoconfigure.quartz.SchedulerFactoryBeanCustomizer;
import org.springframework.boot.autoconfigure.transaction.PlatformTransactionManagerCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ResourceLoader;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;
import org.springframework.scheduling.quartz.SpringBeanJobFactory;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.util.Arrays;
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
@AutoConfigureBefore(QuartzAutoConfiguration.class)
public class ApiBootQuartzAutoConfiguration {

    private ApiBootQuartzProperties properties;
    private ObjectProvider<SchedulerFactoryBeanCustomizer> customizers;
    private JobDetail[] jobDetails;
    private Map<String, Calendar> calendars;
    private Trigger[] triggers;
    private ApplicationContext applicationContext;

    public ApiBootQuartzAutoConfiguration(ApiBootQuartzProperties properties, ObjectProvider<SchedulerFactoryBeanCustomizer> customizers, JobDetail[] jobDetails, Map<String, Calendar> calendars, Trigger[] triggers, ApplicationContext applicationContext) {
        this.properties = properties;
        this.customizers = customizers;
        this.jobDetails = jobDetails;
        this.calendars = calendars;
        this.triggers = triggers;
        this.applicationContext = applicationContext;
    }

    /**
     * 实例化ApiBoot Quartz Service
     *
     * @param scheduler Quartz Scheduler Instance
     * @return ApiBootQuartzService
     */
    @Bean
    @ConditionalOnMissingBean(ApiBootQuartzService.class)
    ApiBootQuartzService apiBootQuartzService(Scheduler scheduler) {
        return new ApiBootQuartzServiceDefaultSupport(scheduler);
    }

    @Bean
    @ConditionalOnMissingBean
    public SchedulerFactoryBean quartzScheduler() {
        SchedulerFactoryBean schedulerFactoryBean = new SchedulerFactoryBean();
        SpringBeanJobFactory jobFactory = new SpringBeanJobFactory();
        jobFactory.setApplicationContext(this.applicationContext);
        schedulerFactoryBean.setJobFactory(jobFactory);
        if (this.properties.getSchedulerName() != null) {
            schedulerFactoryBean.setSchedulerName(this.properties.getSchedulerName());
        }

        schedulerFactoryBean.setAutoStartup(this.properties.isAutoStartup());
        schedulerFactoryBean.setStartupDelay((int) this.properties.getStartupDelay().getSeconds());
        schedulerFactoryBean.setWaitForJobsToCompleteOnShutdown(this.properties.isWaitForJobsToCompleteOnShutdown());
        schedulerFactoryBean.setOverwriteExistingJobs(this.properties.isOverwriteExistingJobs());
        if (!this.properties.getProperties().isEmpty()) {
            schedulerFactoryBean.setQuartzProperties(this.asProperties(this.properties.getProperties()));
        }

        if (this.jobDetails != null && this.jobDetails.length > 0) {
            schedulerFactoryBean.setJobDetails(this.jobDetails);
        }

        if (this.calendars != null && !this.calendars.isEmpty()) {
            schedulerFactoryBean.setCalendars(this.calendars);
        }

        if (this.triggers != null && this.triggers.length > 0) {
            schedulerFactoryBean.setTriggers(this.triggers);
        }

        this.customize(schedulerFactoryBean);
        return schedulerFactoryBean;
    }

    private Properties asProperties(Map<String, String> source) {
        Properties properties = new Properties();
        properties.putAll(source);
        return properties;
    }

    private void customize(SchedulerFactoryBean schedulerFactoryBean) {
        this.customizers.orderedStream().forEach((customizer) -> customizer.customize(schedulerFactoryBean));
    }

    @Configuration
    @ConditionalOnSingleCandidate(DataSource.class)
    protected static class JdbcStoreTypeConfiguration {
        protected JdbcStoreTypeConfiguration() {
        }

        /**
         * properties needed to initialize Jdbc mode
         *
         * @param properties ApiBoot Quartz Properties
         * @return SchedulerFactoryBeanCustomizer
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

        @Bean
        @ConditionalOnMissingBean
        public ApiBootQuartzDataSourceInitializer apiBootQuartzDataSourceInitializer(DataSource dataSource, @QuartzDataSource ObjectProvider<DataSource> quartzDataSource, ResourceLoader resourceLoader, ApiBootQuartzProperties properties) {
            DataSource dataSourceToUse = this.getDataSource(dataSource, quartzDataSource);
            return new ApiBootQuartzDataSourceInitializer(dataSourceToUse, resourceLoader, properties);
        }

        @Bean
        public static ApiBootQuartzAutoConfiguration.JdbcStoreTypeConfiguration.DataSourceInitializerSchedulerDependencyPostProcessor jobDataSourceInitializerSchedulerDependencyPostProcessor() {
            return new ApiBootQuartzAutoConfiguration.JdbcStoreTypeConfiguration.DataSourceInitializerSchedulerDependencyPostProcessor();
        }

        private static class DataSourceInitializerSchedulerDependencyPostProcessor extends AbstractDependsOnBeanFactoryPostProcessor {
            DataSourceInitializerSchedulerDependencyPostProcessor() {
                super(Scheduler.class, SchedulerFactoryBean.class, new String[]{"apiBootQuartzDataSourceInitializer"});
            }
        }
    }
}
