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

import org.minbox.framework.api.boot.plugin.rate.limiter.ApiBootRateLimiter;
import org.minbox.framework.api.boot.plugin.rate.limiter.centre.RateLimiterConfigCentre;
import org.minbox.framework.api.boot.plugin.rate.limiter.support.RedisLuaRateLimiter;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * RateLimiter Automation Configuration in redis Mode
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-05-05 17:50
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@EnableConfigurationProperties(ApiBootRateLimiterProperties.class)
@ConditionalOnClass(name = "org.springframework.data.redis.core.RedisTemplate")
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class ApiBootRateLimiterRedisAutoConfiguration {
    /**
     * ApiBoot Rate Limiter Properties
     */
    private ApiBootRateLimiterProperties apiBootRateLimiterProperties;

    public ApiBootRateLimiterRedisAutoConfiguration(ApiBootRateLimiterProperties apiBootRateLimiterProperties) {
        this.apiBootRateLimiterProperties = apiBootRateLimiterProperties;
    }

    /**
     * redis lua rate limiter
     *
     * @param redisTemplate           redis template
     * @param rateLimiterConfigCentre RateLimiter Config Centre
     * @return ApiBootRateLimiter
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiBootRateLimiter redisLuaRateLimiter(RedisTemplate redisTemplate, RateLimiterConfigCentre rateLimiterConfigCentre) {
        return new RedisLuaRateLimiter(apiBootRateLimiterProperties.isEnableGlobalQps() ? apiBootRateLimiterProperties.getGlobalQps() : 0L, rateLimiterConfigCentre, redisTemplate);
    }
}
