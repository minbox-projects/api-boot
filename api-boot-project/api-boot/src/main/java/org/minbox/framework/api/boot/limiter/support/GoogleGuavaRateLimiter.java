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

package org.minbox.framework.api.boot.limiter.support;

import org.minbox.framework.api.boot.limiter.annotation.RateLimiter;
import org.minbox.framework.api.boot.limiter.context.ApiBootRateLimiterContext;
import org.minbox.framework.api.boot.limiter.centre.RateLimiterConfigCentre;

/**
 * Google guava rate limiter support
 *
 * @author 恒宇少年
 */
public class GoogleGuavaRateLimiter extends AbstractRateLimiter {
    public GoogleGuavaRateLimiter(Long globalQPS, RateLimiterConfigCentre rateLimiterConfigCentre) {
        super(globalQPS, rateLimiterConfigCentre);
    }

    /**
     * google guava away
     *
     * @param annotationQPS {@link RateLimiter#QPS()}
     * @param requestKey    request key
     * @return true : allow access to
     */
    @Override
    public boolean tryAcquire(Double annotationQPS, String requestKey) {
        Long QPS = getPriorityQPS(requestKey, annotationQPS);
        if (QPS <= 0) {
            return true;
        }
        com.google.common.util.concurrent.RateLimiter rateLimiter = ApiBootRateLimiterContext.cacheRateLimiter(requestKey, QPS);
        return rateLimiter.tryAcquire();
    }
}
