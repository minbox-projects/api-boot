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

package org.minbox.framework.api.boot.autoconfigure.ratelimiter;

import org.minbox.framework.api.boot.plugin.rate.limiter.ApiBootRateLimiterConfiguration;
import org.minbox.framework.api.boot.plugin.rate.limiter.config.RateLimiterConfig;
import org.minbox.framework.api.boot.plugin.rate.limiter.handler.ApiBootDefaultRateLimiterInterceptorHandler;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ApiBoot RateLimiter Auto Configuration
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-26 09:36
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(ApiBootRateLimiterConfiguration.class)
@EnableConfigurationProperties(ApiBootRateLimiterProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
public class ApiBootRateLimiterAutoConfiguration {
    /**
     * ApiBoot Rate Limiter Properties
     */
    private ApiBootRateLimiterProperties apiBootRateLimiterProperties;

    public ApiBootRateLimiterAutoConfiguration(ApiBootRateLimiterProperties apiBootRateLimiterProperties) {
        this.apiBootRateLimiterProperties = apiBootRateLimiterProperties;
    }

    /**
     * ApiBoot Rate Limiter Interceptor
     *
     * @return ApiBootDefaultRateLimiterInterceptorHandler
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiBootDefaultRateLimiterInterceptorHandler apiBootDefaultRateLimiterInterceptorHandler() {
        return new ApiBootDefaultRateLimiterInterceptorHandler();
    }

    /**
     * ApiBoot Rate Limiter Configuration
     *
     * @return ApiBootRateLimiterConfiguration
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiBootRateLimiterConfiguration apiBootRateLimiterConfiguration() {
        // rate limiter config
        RateLimiterConfig rateLimiterConfig = new RateLimiterConfig();
        rateLimiterConfig.setInterceptorUrl(apiBootRateLimiterProperties.getInterceptorUrl());

        return new ApiBootRateLimiterConfiguration(rateLimiterConfig, apiBootDefaultRateLimiterInterceptorHandler());
    }
}
