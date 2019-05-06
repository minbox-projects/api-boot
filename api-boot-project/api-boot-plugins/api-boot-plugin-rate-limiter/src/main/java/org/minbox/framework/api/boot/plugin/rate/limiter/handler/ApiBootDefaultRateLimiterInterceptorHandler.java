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

package org.minbox.framework.api.boot.plugin.rate.limiter.handler;

import org.minbox.framework.api.boot.plugin.rate.limiter.ApiBootRateLimiter;
import org.minbox.framework.api.boot.plugin.rate.limiter.annotation.RateLimiter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ApiBoot Default Rate Limiter Interceptor Implement
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-26 10:50
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootDefaultRateLimiterInterceptorHandler implements HandlerInterceptor {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootDefaultRateLimiterInterceptorHandler.class);
    /**
     * ApiBoot RateLimiter
     */
    private ApiBootRateLimiter apiBootRateLimiter;

    public ApiBootDefaultRateLimiterInterceptorHandler(ApiBootRateLimiter apiBootRateLimiter) {
        this.apiBootRateLimiter = apiBootRateLimiter;
        Assert.notNull(apiBootRateLimiter, "No ApiBootRateLimiter implementation class instance.");
        logger.info("ApiBootDefaultRateLimiterInterceptorHandler load complete.");
    }

    /**
     * Executing traffic restrictions
     *
     * @param request  http request
     * @param response http response
     * @param handler  method handler
     * @return true：pass,false：intercept
     * @throws Exception Method Execute Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        try {
            // Do not intercept resource requests
            if (handler instanceof ResourceHttpRequestHandler) {
                return true;
            }
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // get method declared RateLimiter
            RateLimiter rateLimiterAnnotation = handlerMethod.getMethodAnnotation(RateLimiter.class);
            // if dont't set RateLimiter
            if (ObjectUtils.isEmpty(rateLimiterAnnotation)) {
                return true;
            }
            return apiBootRateLimiter.tryAcquire(rateLimiterAnnotation.QPS(), request.getRequestURI());
        } catch (Exception e) {
            logger.error("Current Limiting Request Encountered Exception.", e);
            return false;
        }
    }
}
