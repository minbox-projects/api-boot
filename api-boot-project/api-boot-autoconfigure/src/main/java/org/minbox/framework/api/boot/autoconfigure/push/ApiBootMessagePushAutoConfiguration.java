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

package org.minbox.framework.api.boot.autoconfigure.push;

import cn.jpush.api.JPushClient;
import org.minbox.framework.api.boot.plugin.message.push.ApiBootMessagePushService;
import org.minbox.framework.api.boot.plugin.message.push.aop.advistor.ApiBootMessagePushClientSwitchAdvisor;
import org.minbox.framework.api.boot.plugin.message.push.aop.interceptor.ApiBootMessagePushSwitchAnnotationInterceptor;
import org.minbox.framework.api.boot.plugin.message.push.model.PushClientConfig;
import org.minbox.framework.api.boot.plugin.message.push.support.ApiBootMessagePushJiGuangServiceImpl;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * ApiBoot Message Push Auto Configuration
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-20 14:27
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass({ApiBootMessagePushService.class, JPushClient.class})
@EnableConfigurationProperties(ApiBootMessagePushProperties.class)
public class ApiBootMessagePushAutoConfiguration {
    /**
     * ApiBoot Message Push Properties
     */
    private ApiBootMessagePushProperties apiBootMessagePushProperties;
    /**
     * default client name
     */
    private static final String DEFAULT_CLIENT_NAME = "default";

    public ApiBootMessagePushAutoConfiguration(ApiBootMessagePushProperties apiBootMessagePushProperties) {
        this.apiBootMessagePushProperties = apiBootMessagePushProperties;
    }

    /**
     * instantiation message push service
     *
     * @return ApiBootMessagePushService
     */
    @Bean
    @ConditionalOnMissingBean
    ApiBootMessagePushService apiBootMessagePushService() {
        Map<String, PushClientConfig> configs = getClientConfig();
        return new ApiBootMessagePushJiGuangServiceImpl(configs, apiBootMessagePushProperties.isProduction());
    }

    /**
     * Message Push Aop Interceptor
     *
     * @return ApiBootMessagePushSwitchAnnotationInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    ApiBootMessagePushSwitchAnnotationInterceptor apiBootMessagePushSwitchAnnotationInterceptor() {
        return new ApiBootMessagePushSwitchAnnotationInterceptor();
    }

    /**
     * Message Push Aop Advisor
     *
     * @return ApiBootMessagePushClientSwitchAdvisor
     */
    @Bean
    @ConditionalOnMissingBean
    ApiBootMessagePushClientSwitchAdvisor apiBootMessagePushClientSwitchAdvisor() {
        return new ApiBootMessagePushClientSwitchAdvisor(apiBootMessagePushSwitchAnnotationInterceptor());
    }

    /**
     * get client config
     *
     * @return
     */
    private Map<String, PushClientConfig> getClientConfig() {

        Map<String, PushClientConfig> configs = new HashMap(1);

        // default client config
        configs.put(DEFAULT_CLIENT_NAME, apiBootMessagePushProperties.getClient());

        // multiple
        if (!ObjectUtils.isEmpty(apiBootMessagePushProperties.getMultiple())) {
            configs.putAll(apiBootMessagePushProperties.getMultiple());
        }
        return configs;
    }
}
