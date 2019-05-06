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

package org.minbox.framework.api.boot.plugin.rate.limiter;

import org.minbox.framework.api.boot.plugin.rate.limiter.config.RateLimiterConfig;
import org.springframework.util.Assert;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ApiBoot RateLimiter Configuration
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-26 11:07
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootRateLimiterConfiguration implements WebMvcConfigurer {
    /**
     * rate limiter config
     */
    private RateLimiterConfig rateLimiterConfig;
    /**
     * spring mvc interceptor
     */
    private HandlerInterceptor handlerInterceptor;

    /**
     * init instance
     *
     * @param rateLimiterConfig  rate limiter config
     * @param handlerInterceptor rate limiter interceptor
     */
    public ApiBootRateLimiterConfiguration(RateLimiterConfig rateLimiterConfig, HandlerInterceptor handlerInterceptor) {
        this.rateLimiterConfig = rateLimiterConfig;
        this.handlerInterceptor = handlerInterceptor;
        Assert.notNull(rateLimiterConfig, "RateLimiterConfig can not be null.");
        Assert.notNull(handlerInterceptor, "HandlerInterceptor can not be null.");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(handlerInterceptor).addPathPatterns(rateLimiterConfig.getInterceptorUrl());
    }
}
