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

package org.minbox.framework.api.boot.autoconfigure.resource;

import org.minbox.framework.api.boot.plugin.resource.load.ApiBootResourceStoreDelegate;
import org.minbox.framework.api.boot.plugin.resource.load.pusher.support.ApiBootRedisResourcePusher;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-19 10:46
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(RedisTemplate.class)
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class ApiBootResourceRedisLoadAutoConfiguration {
    /**
     * ApiBoot Resource Load Store Delegate
     */
    private ApiBootResourceStoreDelegate resourceStoreDelegate;

    public ApiBootResourceRedisLoadAutoConfiguration(ObjectProvider<ApiBootResourceStoreDelegate> resourceStoreDelegateObjectProvider) {
        this.resourceStoreDelegate = resourceStoreDelegateObjectProvider.getIfAvailable();
    }

    /**
     * ApiBoot Redis Resource Pusher
     *
     * @param redisTemplate Redis Template
     * @return ApiBootRedisResourcePusher
     */
    @Bean
    @ConditionalOnMissingBean
    ApiBootRedisResourcePusher apiBootRedisResourcePusher(RedisTemplate redisTemplate) {
        return new ApiBootRedisResourcePusher(resourceStoreDelegate, redisTemplate);
    }
}
