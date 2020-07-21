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

import org.minbox.framework.limiter.MinBoxRateLimiter;
import org.minbox.framework.limiter.aop.advisor.RateLimiterAdvisor;
import org.minbox.framework.limiter.aop.interceptor.RateLimiterMethodInterceptor;
import org.minbox.framework.limiter.centre.RateLimiterConfigCentre;
import org.minbox.framework.limiter.centre.support.DefaultRateLimiterConfigCentre;
import org.minbox.framework.limiter.result.RateLimiterOverFlowResponse;
import org.minbox.framework.limiter.support.GoogleGuavaRateLimiter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * ApiBoot RateLimiter Auto Configuration
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass(MinBoxRateLimiter.class)
@EnableConfigurationProperties(ApiBootRateLimiterProperties.class)
@AutoConfigureAfter(WebMvcAutoConfiguration.class)
@Import({ApiBootRateLimiterRedisAutoConfiguration.class, ApiBootRateLimiterNacosConfigConfiguration.class})
public class ApiBootRateLimiterAutoConfiguration {
    /**
     * ApiBoot Rate Limiter Properties
     */
    private ApiBootRateLimiterProperties apiBootRateLimiterProperties;
    /**
     * RateLimiter OverFlow Request
     */
    private RateLimiterOverFlowResponse rateLimiterOverFlowResponse;

    public ApiBootRateLimiterAutoConfiguration(ApiBootRateLimiterProperties apiBootRateLimiterProperties, ObjectProvider<RateLimiterOverFlowResponse> rateLimiterOverFlowRequestObjectProvider) {
        this.apiBootRateLimiterProperties = apiBootRateLimiterProperties;
        this.rateLimiterOverFlowResponse = rateLimiterOverFlowRequestObjectProvider.getIfAvailable();
    }

    /**
     * google guava rate limiter
     *
     * @param rateLimiterConfigCentre RateLimiter Config Centre
     * @return ApiBootRateLimiter
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnMissingClass("org.springframework.data.redis.core.RedisTemplate")
    public MinBoxRateLimiter googleGuavaRateLimiter(RateLimiterConfigCentre rateLimiterConfigCentre) {
        return new GoogleGuavaRateLimiter(apiBootRateLimiterProperties.isEnableGlobalQps() ? apiBootRateLimiterProperties.getGlobalQps() : 0L, rateLimiterConfigCentre);
    }

    /**
     * default config centre
     *
     * @return RateLimiterConfigCentre
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnMissingClass({"com.alibaba.boot.nacos.config.properties.NacosConfigProperties"})
    public RateLimiterConfigCentre defaultRateLimiterConfigCentre() {
        return new DefaultRateLimiterConfigCentre();
    }

    /**
     * ApiBoot RateLimiter Pointcut Advisor
     *
     * @param rateLimiterMethodInterceptor ResourceLoad Annotation Method Interceptor
     * @return ApiBootRateLimiterAdvisor
     */
    @Bean
    @ConditionalOnMissingBean
    RateLimiterAdvisor rateLimiterAdvisor(RateLimiterMethodInterceptor rateLimiterMethodInterceptor) {
        return new RateLimiterAdvisor(rateLimiterMethodInterceptor);
    }

    /**
     * ResourceLoad Annotation Method Interceptor
     * Implementing major business logic
     *
     * @param apiBootRateLimiter apiBootRateLimiter
     * @return ApiBootRateLimiterMethodInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    RateLimiterMethodInterceptor rateLimiterMethodInterceptor(MinBoxRateLimiter apiBootRateLimiter) {
        return new RateLimiterMethodInterceptor(apiBootRateLimiter, rateLimiterOverFlowResponse);
    }
}
