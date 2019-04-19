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
import org.minbox.framework.api.boot.plugin.resource.load.aop.advistor.ApiBootResourceLoadAdvisor;
import org.minbox.framework.api.boot.plugin.resource.load.aop.interceptor.ApiBootResourceLoadMethodInterceptor;
import org.minbox.framework.api.boot.plugin.resource.load.pusher.ApiBootResourcePusher;
import org.minbox.framework.api.boot.plugin.resource.load.pusher.support.ApiBootMemoryResourcePusher;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * ApiBoot Resource Load Auto Config
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-12 13:30
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(ApiBootResourceStoreDelegate.class)
@Import(ApiBootResourceRedisLoadAutoConfiguration.class)
public class ApiBootResourceLoadAutoConfiguration {
    /**
     * ApiBoot Resource Load Store Delegate
     */
    private ApiBootResourceStoreDelegate resourceStoreDelegate;

    public ApiBootResourceLoadAutoConfiguration(ObjectProvider<ApiBootResourceStoreDelegate> resourceStoreDelegateObjectProvider) {
        this.resourceStoreDelegate = resourceStoreDelegateObjectProvider.getIfAvailable();
    }

    /**
     * ApiBoot Resource Load Pointcut Advisor
     *
     * @param resourceLoadMethodInterceptor ResourceLoad Annotation Method Interceptor
     * @return ApiBootResourceLoadAdvisor
     */
    @Bean
    @ConditionalOnMissingBean
    ApiBootResourceLoadAdvisor resourceLoadAdvisor(ApiBootResourceLoadMethodInterceptor resourceLoadMethodInterceptor) {
        return new ApiBootResourceLoadAdvisor(resourceLoadMethodInterceptor);
    }

    /**
     * ResourceLoad Annotation Method Interceptor
     * Implementing major business logic
     *
     * @return ApiBootResourceLoadMethodInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    ApiBootResourceLoadMethodInterceptor resourceLoadMethodInterceptor(ApiBootResourcePusher apiBootResourcePusher) {
        return new ApiBootResourceLoadMethodInterceptor(apiBootResourcePusher);
    }

    /**
     * ApiBoot Jdbc Resource Pusher
     * extends from ApiBootJdbcResourcePusher
     *
     * @return ApiBootJdbcResourcePusher
     */
    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnMissingClass("org.springframework.data.redis.core.RedisTemplate")
    ApiBootMemoryResourcePusher apiBootMemoryResourcePusher() {
        return new ApiBootMemoryResourcePusher(resourceStoreDelegate);
    }
}
