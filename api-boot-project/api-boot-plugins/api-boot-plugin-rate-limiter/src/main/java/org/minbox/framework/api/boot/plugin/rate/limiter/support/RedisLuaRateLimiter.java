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

package org.minbox.framework.api.boot.plugin.rate.limiter.support;

import org.minbox.framework.api.boot.plugin.rate.limiter.annotation.RateLimiter;
import org.minbox.framework.api.boot.plugin.rate.limiter.centre.RateLimiterConfigCentre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.core.script.RedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.scripting.support.ResourceScriptSource;
import org.springframework.util.Assert;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

/**
 * Redis lua rate limiter support
 *
 * @author 恒宇少年
 */
public class RedisLuaRateLimiter extends AbstractRateLimiter {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(RedisLuaRateLimiter.class);

    /**
     * Redis Script file name.
     */
    private static final String QPS_LUA_PATH = "META-INF/scripts/qps-rate-limiter.lua";
    /**
     * redis template
     */
    private RedisTemplate redisTemplate;
    /**
     * Redis Script.
     */
    private RedisScript<List<Long>> redisScript;

    public RedisLuaRateLimiter(Long globalQPS, RateLimiterConfigCentre rateLimiterConfigCentre, RedisTemplate redisTemplate) {
        super(globalQPS, rateLimiterConfigCentre);
        this.redisTemplate = redisTemplate;
        this.redisScript = getRedisScript();

        Assert.notNull(redisTemplate, "No RedisTemplate implementation class was found.");
        Assert.notNull(redisScript, "Unable to load Lua script.");
    }

    /**
     * redis lua away
     * Processing traffic restrictions using LUA scripts
     * Processing with Spring Cloud Gateway official script
     *
     * @param annotationQPS {@link RateLimiter#QPS()}
     * @param requestKey    request key
     * @return true : allow access to
     */
    @Override
    public boolean tryAcquire(Double annotationQPS, String requestKey) {
        try {
            Long QPS = getPriorityQPS(requestKey, annotationQPS);
            if (QPS <= 0) {
                return true;
            }
            // get keys
            List<String> keys = getKeys(requestKey);

            // Parameters and serialization of return values
            RedisSerializer<String> serializer = new StringRedisSerializer();

            // execute lua script
            List<Long> tokenResult = (List<Long>) this.redisTemplate.execute(this.redisScript, serializer, serializer, keys, String.valueOf(QPS), String.valueOf(QPS), Instant.now().getEpochSecond() + "", "1");

            // Index 1 value is the number of remaining requestable tokens
            Long newTokens = tokenResult.get(1);
            if (logger.isDebugEnabled()) {
                logger.debug("Number of remaining tokens for this request is {}", newTokens);
            }
            return newTokens > 0;
        } catch (Exception e) {
            /*
             * We don't want a hard dependency on Redis to allow traffic. Make sure to set
             * an alert so you know if this is happening too much. Stripe's observed
             * failure rate is 0.01%.
             */
            logger.error("Error determining if user allowed from redis", e);
        }
        return true;
    }

    /**
     * get Redis Script
     *
     * @return RedisScript
     */
    RedisScript<List<Long>> getRedisScript() {
        DefaultRedisScript redisScript = new DefaultRedisScript<>();
        redisScript.setScriptSource(new ResourceScriptSource(new ClassPathResource(QPS_LUA_PATH)));
        redisScript.setResultType(List.class);
        return redisScript;
    }

    /**
     * get Keys
     *
     * @param id resource key（request uri）
     * @return
     */
    static List<String> getKeys(String id) {
        // use `{}` around keys to use Redis Key hash tags
        // this allows for using redis cluster

        // Make a unique key per user.
        String prefix = "qps_rate_limiter.{" + id;

        // You need two Redis keys for Token Bucket.
        String tokenKey = prefix + "}.tokens";
        String timestampKey = prefix + "}.timestamp";
        return Arrays.asList(tokenKey, timestampKey);
    }
}
