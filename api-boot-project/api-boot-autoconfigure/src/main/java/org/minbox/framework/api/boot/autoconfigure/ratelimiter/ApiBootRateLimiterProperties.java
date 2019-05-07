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

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.ratelimiter.ApiBootRateLimiterProperties.API_BOOT_RATE_LIMITER_PREFIX;

/**
 * ApiBoot Rate Limiter Properties
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-26 11:27
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_RATE_LIMITER_PREFIX)
public class ApiBootRateLimiterProperties {
    /**
     * rate limiter prefix
     */
    public static final String API_BOOT_RATE_LIMITER_PREFIX = "api.boot.rate-limiter";
    /**
     * 限流请求地址前缀
     */
    private String[] interceptorUrl = {"/**"};
    /**
     * 全局QPS配置
     * 默认每秒限流10次请求
     */
    private Long globalQps = 10L;
    /**
     * 开启全局QPS配置
     */
    private boolean enableGlobalQps = false;
}
