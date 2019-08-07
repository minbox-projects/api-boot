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

import org.minbox.framework.logging.client.admin.discovery.LoggingAdminDiscovery;
import org.minbox.framework.logging.client.admin.report.LoggingAdminReport;
import org.minbox.framework.logging.client.admin.report.LoggingReportScheduled;
import org.minbox.framework.logging.client.admin.report.support.LoggingAdminReportSupport;
import org.minbox.framework.logging.client.cache.LoggingCache;
import org.minbox.framework.logging.client.cache.support.LoggingMemoryCache;
import org.minbox.framework.logging.client.filter.LoggingBodyFilter;
import org.minbox.framework.logging.client.interceptor.LoggingInterceptor;
import org.minbox.framework.logging.client.notice.LoggingNotice;
import org.minbox.framework.logging.client.notice.LoggingNoticeListener;
import org.minbox.framework.logging.client.notice.away.LoggingStorageNotice;
import org.minbox.framework.logging.client.notice.away.support.LoggingAdminStorageNotice;
import org.minbox.framework.logging.client.notice.away.support.LoggingLocalStorageNotice;
import org.minbox.framework.logging.client.span.LoggingSpan;
import org.minbox.framework.logging.client.span.support.LoggingDefaultSpan;
import org.minbox.framework.logging.client.tracer.LoggingTracer;
import org.minbox.framework.logging.client.tracer.support.LoggingDefaultTracer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

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
        ApiBootLoggingRestTemplateAutoConfiguration.class
})
public class ApiBootLoggingAutoConfiguration implements WebMvcConfigurer {
    /**
     * ApiBoot Logging Properties
     */
    private ApiBootLoggingProperties apiBootLoggingProperties;
    /**
     * Configurable Environment
     */
    private ConfigurableEnvironment environment;

    public ApiBootLoggingAutoConfiguration(ApiBootLoggingProperties apiBootLoggingProperties, ConfigurableEnvironment environment) {
        this.apiBootLoggingProperties = apiBootLoggingProperties;
        this.environment = environment;
    }

    /**
     * ApiBoot Logging Tracer
     *
     * @return ApiBootLoggingTracer
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingTracer apiBootLoggingTracer() {
        return new LoggingDefaultTracer();
    }

    /**
     * ApiBoot Logging Span
     *
     * @return ApiBootLoggingSpan
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingSpan apiBootLoggingSpan() {
        return new LoggingDefaultSpan();
    }

    /**
     * ApiBoot Logging Interceptor
     *
     * @return ApiBootLoggingInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingInterceptor apiBootLoggingInterceptor() {
        return new LoggingInterceptor(environment, apiBootLoggingTracer(), apiBootLoggingSpan(), apiBootLoggingProperties.getIgnorePaths());
    }

    /**
     * Instance Transmit Request Body Filter
     *
     * @return ApiBootLoggingBodyFilter
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingBodyFilter apiBootLoggingFilter() {
        return new LoggingBodyFilter();
    }

    /**
     * ApiBoot Logging Console Notice Listener
     *
     * @param loggingStorageNotice ApiBoot Logging Notice Support Instance
     * @return ApiBootLoggingNoticeListener
     * @see org.minbox.framework.logging.client.notice.away.support.LoggingLocalStorageNotice
     * @see org.minbox.framework.logging.client.notice.away.support.LoggingAdminStorageNotice
     */
    @Bean
    public LoggingNoticeListener apiBootLoggingNoticeListener(LoggingStorageNotice loggingStorageNotice) {
        return new LoggingNoticeListener(loggingStorageNotice, apiBootLoggingProperties.isFormatConsoleLogJson(), apiBootLoggingProperties.isShowConsoleLog());
    }


    /**
     * ApiBoot Logging Local Notice
     *
     * @return ApiBootLoggingLocalStorageNotice
     */
    @Bean
    @ConditionalOnMissingBean(LoggingAdminDiscovery.class)
    public LoggingLocalStorageNotice apiBootLoggingLocalNotice(ObjectProvider<List<LoggingNotice>> localNoticeObjectProvider) {
        return new LoggingLocalStorageNotice(localNoticeObjectProvider.getIfAvailable());
    }

    /**
     * ApiBoot Logging Admin Notice
     *
     * @return ApiBootLoggingAdminStorageNotice
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(LoggingAdminDiscovery.class)
    public LoggingAdminStorageNotice apiBootLoggingAdminStorageNotice(LoggingCache loggingCache, LoggingAdminReport loggingAdminReport) {
        return new LoggingAdminStorageNotice(loggingCache, apiBootLoggingProperties.getReportAway(), loggingAdminReport);
    }

    /**
     * Logging Memory Cache
     *
     * @return LoggingMemoryCache
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_LOGGING_PREFIX, name = "logging-cache-away", havingValue = "memory", matchIfMissing = true)
    @ConditionalOnMissingBean
    public LoggingCache loggingMemoryCache() {
        return new LoggingMemoryCache();
    }

    /**
     * Logging Admin Report
     *
     * @param loggingAdminDiscovery Logging Admin Discovery
     * @param restTemplate          RestTemplate
     * @param loggingCache          Logging Cache
     * @return LoggingAdminReportSupport
     */
    @Bean
    @ConditionalOnBean(LoggingAdminDiscovery.class)
    @ConditionalOnMissingBean
    public LoggingAdminReport loggingAdminReportSupport(LoggingAdminDiscovery loggingAdminDiscovery, RestTemplate restTemplate, LoggingCache loggingCache, ConfigurableEnvironment environment) {
        return new LoggingAdminReportSupport(loggingAdminDiscovery, restTemplate, loggingCache, apiBootLoggingProperties.getReportNumberOfRequestLog(), environment);
    }

    /**
     * Logging Report Scheduled Task Job
     *
     * @param loggingAdminReport Logging Admin Report
     * @return LoggingReportScheduled
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_LOGGING_PREFIX, name = "report-away", havingValue = "timing")
    @ConditionalOnMissingBean
    public LoggingReportScheduled loggingReportScheduled(LoggingAdminReport loggingAdminReport) {
        return new LoggingReportScheduled(loggingAdminReport, apiBootLoggingProperties.getReportInitialDelaySecond(), apiBootLoggingProperties.getReportIntervalSecond());
    }

    /**
     * registry logging interceptor
     *
     * @param registry registry interceptor
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiBootLoggingInterceptor()).addPathPatterns(apiBootLoggingProperties.getLoggingPathPrefix());
    }

    /**
     * Rest Template Instance
     *
     * @return RestTemplate
     */
    @Bean
    @Primary
    @ConditionalOnMissingBean
    public RestTemplate restTemplate(ObjectProvider<List<ClientHttpRequestInterceptor>> interceptorObjectProvider) {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setInterceptors(interceptorObjectProvider.getIfAvailable());
        return restTemplate;
    }
}
