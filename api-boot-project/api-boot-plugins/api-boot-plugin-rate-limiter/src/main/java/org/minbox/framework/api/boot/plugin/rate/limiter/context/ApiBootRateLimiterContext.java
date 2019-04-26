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

package org.minbox.framework.api.boot.plugin.rate.limiter.context;

import com.google.common.util.concurrent.RateLimiter;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ApiBoot Rate Limiter Context
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-26 13:36
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootRateLimiterContext {
    /**
     * Cache a rateLimiter for each request address
     */
    private static final ConcurrentMap<String, RateLimiter> RATE_LIMITER_MAP = new ConcurrentHashMap();

    /**
     * get rate limiter
     *
     * @param requestUri request uri
     * @return RateLimiter
     */
    public static RateLimiter getRateLimiter(String requestUri) {
        return RATE_LIMITER_MAP.get(requestUri);
    }

    /**
     * cache request uri rate limiter
     *
     * @param requestUri request uri
     * @param qps        qps
     * @return RateLimiter
     */
    public static RateLimiter cacheRateLimiter(String requestUri, double qps) {
        RateLimiter rateLimiter = getRateLimiter(requestUri);
        if (ObjectUtils.isEmpty(rateLimiter)) {
            rateLimiter = RateLimiter.create(qps);
            RATE_LIMITER_MAP.put(requestUri, rateLimiter);
        }
        return rateLimiter;
    }
}
