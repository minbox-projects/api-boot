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
import org.minbox.framework.api.boot.plugin.logging.filter.ApiBootLoggingBodyFilter;
import org.minbox.framework.api.boot.plugin.logging.interceptor.ApiBootLoggingInterceptor;
import org.minbox.framework.api.boot.plugin.logging.notice.away.ApiBootLogStorageNotice;
import org.minbox.framework.api.boot.plugin.logging.notice.ApiBootLoggingNoticeListener;
import org.minbox.framework.api.boot.plugin.logging.notice.ApiBootLogNotice;
import org.minbox.framework.api.boot.plugin.logging.notice.away.support.ApiBootLoggingLocalStorageNotice;
import org.minbox.framework.api.boot.plugin.logging.span.ApiBootLoggingSpan;
import org.minbox.framework.api.boot.plugin.logging.span.support.ApiBootLoggingDefaultSpan;
import org.minbox.framework.api.boot.plugin.logging.tracer.ApiBootLoggingTracer;
import org.minbox.framework.api.boot.plugin.logging.tracer.support.ApiBootLoggingDefaultTracer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

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
@ConditionalOnClass({ApiBootLog.class, HandlerInterceptor.class})
@EnableConfigurationProperties(ApiBootLoggingProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
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
     * ApiBoot Logging Local Notice
     *
     * @return ApiBootLoggingLocalStorageNotice
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiBootLoggingLocalStorageNotice apiBootLoggingLocalNotice(ObjectProvider<List<ApiBootLogNotice>> localNoticeObjectProvider) {
        return new ApiBootLoggingLocalStorageNotice(localNoticeObjectProvider.getIfAvailable());
    }

    /**
     * ApiBoot Logging Console Notice Listener
     *
     * @param apiBootLogStorageNotice ApiBoot Logging Notice Support Instance
     * @return ApiBootLoggingNoticeListener
     * @see ApiBootLoggingLocalStorageNotice
     * @see org.minbox.framework.api.boot.plugin.logging.notice.away.support.ApiBootLoggingRestStorageNotice
     * @see org.minbox.framework.api.boot.plugin.logging.notice.away.support.ApiBootLoggingMqStorageNotice
     */
    @Bean
    public ApiBootLoggingNoticeListener apiBootLoggingNoticeListener(ApiBootLogStorageNotice apiBootLogStorageNotice) {
        return new ApiBootLoggingNoticeListener(apiBootLogStorageNotice);
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
}
