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

import org.minbox.framework.resource.ResourceStoreDelegate;
import org.minbox.framework.resource.aop.advistor.ResourceLoadAdvisor;
import org.minbox.framework.resource.aop.interceptor.ResourceLoadMethodInterceptor;
import org.minbox.framework.resource.pusher.ResourcePusher;
import org.minbox.framework.resource.pusher.support.MemoryResourcePusher;
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
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass(ResourceStoreDelegate.class)
@Import(ApiBootResourceRedisLoadAutoConfiguration.class)
public class ApiBootResourceLoadAutoConfiguration {
    /**
     * ApiBoot Resource Load Store Delegate
     */
    private ResourceStoreDelegate resourceStoreDelegate;

    public ApiBootResourceLoadAutoConfiguration(ObjectProvider<ResourceStoreDelegate> resourceStoreDelegateObjectProvider) {
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
    ResourceLoadAdvisor resourceLoadAdvisor(ResourceLoadMethodInterceptor resourceLoadMethodInterceptor) {
        return new ResourceLoadAdvisor(resourceLoadMethodInterceptor);
    }

    /**
     * ResourceLoad Annotation Method Interceptor
     * Implementing major business logic
     *
     * @return ApiBootResourceLoadMethodInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    ResourceLoadMethodInterceptor resourceLoadMethodInterceptor(ResourcePusher apiBootResourcePusher) {
        return new ResourceLoadMethodInterceptor(apiBootResourcePusher);
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
    MemoryResourcePusher apiBootMemoryResourcePusher() {
        return new MemoryResourcePusher(resourceStoreDelegate);
    }
}
