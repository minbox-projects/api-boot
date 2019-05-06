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

/**
 * ApiBoot RateLimiter
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-05-05 17:12
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface ApiBootRateLimiter {
    /**
     * Attempt to obtain a request current limit token
     *
     * @param QPS        queries per second
     * @param requestUri request uri
     * @return true : allow access to
     */
    boolean tryAcquire(Double QPS, String requestUri);

    class Response {
        private final boolean allowed;
        private final long tokensRemaining;

        public Response(boolean allowed, long tokensRemaining) {
            this.allowed = allowed;
            this.tokensRemaining = tokensRemaining;
        }

        public boolean isAllowed() {
            return allowed;
        }

        @Override
        public String toString() {
            return "Response{" +
                    "allowed=" + allowed +
                    ", tokensRemaining=" + tokensRemaining +
                    '}';
        }
    }
}
