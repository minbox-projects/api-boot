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

import org.minbox.framework.api.boot.plugin.logging.ApiBootLog;
import org.minbox.framework.api.boot.plugin.logging.admin.discovery.LoggingAdminDiscovery;
import org.minbox.framework.api.boot.plugin.logging.admin.discovery.support.LoggingAppointAdminDiscovery;
import org.minbox.framework.api.boot.plugin.logging.admin.discovery.support.LoggingRegistryCenterAdminDiscovery;
import org.minbox.framework.api.boot.plugin.logging.admin.report.LoggingAdminReport;
import org.minbox.framework.api.boot.plugin.logging.admin.report.LoggingReportScheduled;
import org.minbox.framework.api.boot.plugin.logging.admin.report.support.LoggingAdminReportSupport;
import org.minbox.framework.api.boot.plugin.logging.cache.LoggingCache;
import org.minbox.framework.api.boot.plugin.logging.cache.support.LoggingMemoryCache;
import org.minbox.framework.api.boot.plugin.logging.filter.ApiBootLoggingBodyFilter;
import org.minbox.framework.api.boot.plugin.logging.interceptor.ApiBootLoggingInterceptor;
import org.minbox.framework.api.boot.plugin.logging.notice.away.ApiBootLogStorageNotice;
import org.minbox.framework.api.boot.plugin.logging.notice.ApiBootLoggingNoticeListener;
import org.minbox.framework.api.boot.plugin.logging.notice.ApiBootLogNotice;
import org.minbox.framework.api.boot.plugin.logging.notice.away.support.ApiBootLoggingAdminStorageNotice;
import org.minbox.framework.api.boot.plugin.logging.notice.away.support.ApiBootLoggingLocalStorageNotice;
import org.minbox.framework.api.boot.plugin.logging.span.ApiBootLoggingSpan;
import org.minbox.framework.api.boot.plugin.logging.span.support.ApiBootLoggingDefaultSpan;
import org.minbox.framework.api.boot.plugin.logging.tracer.ApiBootLoggingTracer;
import org.minbox.framework.api.boot.plugin.logging.tracer.support.ApiBootLoggingDefaultTracer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static org.minbox.framework.api.boot.autoconfigure.logging.ApiBootLoggingProperties.API_BOOT_LOGGING_PREFIX;

import java.util.List;

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
@ConditionalOnClass(ApiBootLoggingInterceptor.class)
@EnableConfigurationProperties(ApiBootLoggingProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@ConditionalOnWebApplication
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
    public ApiBootLoggingTracer apiBootLoggingTracer() {
        return new ApiBootLoggingDefaultTracer();
    }

    /**
     * ApiBoot Logging Span
     *
     * @return ApiBootLoggingSpan
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiBootLoggingSpan apiBootLoggingSpan() {
        return new ApiBootLoggingDefaultSpan();
    }

    /**
     * ApiBoot Logging Interceptor
     *
     * @return ApiBootLoggingInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiBootLoggingInterceptor apiBootLoggingInterceptor() {
        return new ApiBootLoggingInterceptor(environment, apiBootLoggingTracer(), apiBootLoggingSpan(), apiBootLoggingProperties.getIgnorePaths());
    }

    /**
     * Instance Transmit Request Body Filter
     *
     * @return ApiBootLoggingBodyFilter
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiBootLoggingBodyFilter apiBootLoggingFilter() {
        return new ApiBootLoggingBodyFilter();
    }

    /**
     * ApiBoot Logging Console Notice Listener
     *
     * @param apiBootLogStorageNotice ApiBoot Logging Notice Support Instance
     * @return ApiBootLoggingNoticeListener
     * @see ApiBootLoggingLocalStorageNotice
     * @see org.minbox.framework.api.boot.plugin.logging.notice.away.support.ApiBootLoggingAdminStorageNotice
     * @see org.minbox.framework.api.boot.plugin.logging.notice.away.support.ApiBootLoggingLocalStorageNotice
     */
    @Bean
    public ApiBootLoggingNoticeListener apiBootLoggingNoticeListener(ApiBootLogStorageNotice apiBootLogStorageNotice) {
        return new ApiBootLoggingNoticeListener(apiBootLogStorageNotice, apiBootLoggingProperties.isFormatConsoleLogJson());
    }


    /**
     * ApiBoot Logging Local Notice
     *
     * @return ApiBootLoggingLocalStorageNotice
     */
    @Bean
    @ConditionalOnMissingBean(LoggingAdminDiscovery.class)
    public ApiBootLoggingLocalStorageNotice apiBootLoggingLocalNotice(ObjectProvider<List<ApiBootLogNotice>> localNoticeObjectProvider) {
        return new ApiBootLoggingLocalStorageNotice(localNoticeObjectProvider.getIfAvailable());
    }

    /**
     * ApiBoot Logging Admin Notice
     *
     * @return ApiBootLoggingAdminStorageNotice
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnBean(LoggingAdminDiscovery.class)
    public ApiBootLoggingAdminStorageNotice apiBootLoggingAdminStorageNotice(LoggingCache loggingCache, LoggingAdminReport loggingAdminReport) {
        return new ApiBootLoggingAdminStorageNotice(loggingCache, apiBootLoggingProperties.getReportAway(), loggingAdminReport);
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
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * ApiBoot Logging Admin Appoint Away
     */
    @Configuration
    @EnableConfigurationProperties(ApiBootLoggingProperties.class)
    @ConditionalOnMissingBean(LoggingRegistryCenterAdminDiscovery.class)
    @ConditionalOnProperty(prefix = API_BOOT_LOGGING_PREFIX, name = "admin.server-address")
    public static class ApiBootLoggingAdminAppointAutoConfiguration {
        /**
         * ApiBoot Logging Properties
         */
        private ApiBootLoggingProperties apiBootLoggingProperties;

        public ApiBootLoggingAdminAppointAutoConfiguration(ApiBootLoggingProperties apiBootLoggingProperties) {
            this.apiBootLoggingProperties = apiBootLoggingProperties;
        }

        /**
         * ApiBoot Logging Admin Config Discovery
         * Multiple Use "," Separation
         *
         * @return LoggingAdminDiscovery
         */
        @Bean
        @ConditionalOnMissingBean
        public LoggingAppointAdminDiscovery loggingConfigAdminDiscovery() {
            String[] serverAddressArray = apiBootLoggingProperties.getAdmin().getServerAddress().split(",");
            return new LoggingAppointAdminDiscovery(serverAddressArray);
        }
    }

    /**
     * ApiBoot Logging Admin Discovery Away
     */
    @Configuration
    @ConditionalOnClass(LoadBalancerClient.class)
    @EnableConfigurationProperties(ApiBootLoggingProperties.class)
    @ConditionalOnProperty(prefix = API_BOOT_LOGGING_PREFIX, name = "discovery.service-id")
    public static class ApiBootLoggingAdminDiscoveryAutoConfiguration {
        /**
         * ApiBoot Logging Properties
         */
        private ApiBootLoggingProperties apiBootLoggingProperties;

        public ApiBootLoggingAdminDiscoveryAutoConfiguration(ApiBootLoggingProperties apiBootLoggingProperties) {
            this.apiBootLoggingProperties = apiBootLoggingProperties;
        }

        /**
         * ApiBoot Logging Admin Registry Center Discovery
         *
         * @return LoggingRegistryCenterAdminDiscovery
         */
        @Bean
        @ConditionalOnMissingBean
        public LoggingRegistryCenterAdminDiscovery loggingRegistryCenterAdminDiscovery(LoadBalancerClient loadBalancerClient) {
            return new LoggingRegistryCenterAdminDiscovery(apiBootLoggingProperties.getDiscovery().getServiceId(), apiBootLoggingProperties.getDiscovery().getUsername(), apiBootLoggingProperties.getDiscovery().getPassword(), loadBalancerClient);
        }
    }
}
