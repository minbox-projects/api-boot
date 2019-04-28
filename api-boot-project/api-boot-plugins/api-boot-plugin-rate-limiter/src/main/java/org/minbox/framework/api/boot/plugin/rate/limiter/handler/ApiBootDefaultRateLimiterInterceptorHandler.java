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

import org.minbox.framework.api.boot.plugin.rate.limiter.annotation.RateLimiter;
import org.minbox.framework.api.boot.plugin.rate.limiter.context.ApiBootRateLimiterContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

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
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            // get method declared RateLimiter
            RateLimiter rateLimiterAnnotation = handlerMethod.getMethodAnnotation(RateLimiter.class);
            com.google.common.util.concurrent.RateLimiter rateLimiter = ApiBootRateLimiterContext.cacheRateLimiter(request.getRequestURI(), rateLimiterAnnotation.QPS());
            double acquire = rateLimiter.acquire();
            logger.debug("ApiBoot rate limiter acquire：{}", acquire);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
