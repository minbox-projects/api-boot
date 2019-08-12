/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.minbox.framework.api.boot.autoconfigure.logging;

import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.minbox.framework.logging.client.admin.discovery.LoggingAdminDiscovery;
import org.minbox.framework.logging.client.admin.report.LoggingReportScheduled;
import org.minbox.framework.logging.client.filter.LoggingBodyFilter;
import org.minbox.framework.logging.client.interceptor.LoggingInterceptor;
import org.minbox.framework.logging.client.notice.LoggingNoticeListener;
import org.minbox.framework.logging.client.notice.support.LoggingAdminNotice;
import org.minbox.framework.logging.client.notice.support.LoggingLocalNotice;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

import static org.minbox.framework.api.boot.autoconfigure.logging.ApiBootLoggingProperties.API_BOOT_LOGGING_PREFIX;

/**
 * ApiBoot Logging Auto Configuration
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-15 18:33
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(LoggingInterceptor.class)
@EnableConfigurationProperties(ApiBootLoggingProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@ConditionalOnWebApplication
@EnableAsync
@Import({
        ApiBootLoggingAdminDiscoveryAutoConfiguration.class,
        ApiBootLoggingAdminAppointAutoConfiguration.class,
        ApiBootLoggingOpenfeignAutoConfiguration.class,
        ApiBootLoggingWebAutoConfiguration.class
})
public class ApiBootLoggingAutoConfiguration {
    /**
     * ApiBoot Logging Properties
     */
    private ApiBootLoggingProperties apiBootLoggingProperties;

    public ApiBootLoggingAutoConfiguration(ApiBootLoggingProperties apiBootLoggingProperties) {
        this.apiBootLoggingProperties = apiBootLoggingProperties;
    }

    /**
     * logging factory bean
     * {@link LoggingFactoryBean}
     *
     * @return LoggingFactoryBean
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingFactoryBean loggingFactoryBean(ObjectProvider<LoggingAdminDiscovery> loggingAdminDiscoveryObjectProvider) {
        LoggingFactoryBean factoryBean = new LoggingFactoryBean();
        factoryBean.setIgnorePaths(apiBootLoggingProperties.getIgnorePaths());
        factoryBean.setReportAway(apiBootLoggingProperties.getReportAway());
        factoryBean.setNumberOfRequestLog(apiBootLoggingProperties.getReportNumberOfRequestLog());
        factoryBean.setReportInitialDelaySecond(apiBootLoggingProperties.getReportInitialDelaySecond());
        factoryBean.setReportIntervalSecond(apiBootLoggingProperties.getReportIntervalSecond());
        factoryBean.setLoggingAdminDiscovery(loggingAdminDiscoveryObjectProvider.getIfAvailable());
        return factoryBean;
    }

    /**
     * logging request interceptor
     * {@link LoggingInterceptor}
     *
     * @param factoryBean logging factory bean
     * @return LoggingInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingInterceptor loggingInterceptor(LoggingFactoryBean factoryBean) {
        return new LoggingInterceptor(factoryBean);
    }

    /**
     * Instance Transmit Request Body Filter
     * {@link LoggingBodyFilter}
     *
     * @return ApiBootLoggingBodyFilter
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingBodyFilter apiBootLoggingFilter() {
        return new LoggingBodyFilter();
    }

    /**
     * logging notice listener
     * {@link LoggingNoticeListener}
     *
     * @return LoggingNoticeListener
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingNoticeListener loggingNoticeListener() {
        return new LoggingNoticeListener();
    }

    /**
     * logging local notice
     * {@link LoggingLocalNotice}
     *
     * @return LoggingLocalNotice
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingLocalNotice loggingLocalNotice() {
        LoggingLocalNotice localNotice = new LoggingLocalNotice();
        localNotice.setShowConsoleLog(apiBootLoggingProperties.isShowConsoleLog());
        localNotice.setFormatConsoleLogJson(apiBootLoggingProperties.isFormatConsoleLogJson());
        return localNotice;
    }

    /**
     * logging admin notice
     * report request logging to admins
     * {@link LoggingAdminNotice}
     * {@link LoggingAdminDiscovery}
     *
     * @param factoryBean logging factory bean
     * @return LoggingAdminNotice
     */
    @Bean
    @ConditionalOnBean(LoggingAdminDiscovery.class)
    public LoggingAdminNotice loggingAdminNotice(LoggingFactoryBean factoryBean) {
        return new LoggingAdminNotice(factoryBean);
    }

    /**
     * Logging Report Scheduled Task Job
     * When the configuration parameter "api.boot.logging.report-away=timing" is configured,
     * the creation timing task is performed to report log information to admin node
     * {@link ApiBootLoggingProperties#getReportAway()}
     * {@link LoggingReportScheduled}
     *
     * @param factoryBean logging factory bean
     * @return LoggingReportScheduled
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_LOGGING_PREFIX, name = "report-away", havingValue = "timing")
    @ConditionalOnMissingBean
    public LoggingReportScheduled loggingReportScheduled(LoggingFactoryBean factoryBean) {
        return new LoggingReportScheduled(factoryBean);
    }
}
