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

package org.minbox.framework.api.boot.autoconfigure.logging;

import org.minbox.framework.api.boot.plugin.logging.admin.discovery.support.LoggingRegistryCenterAdminDiscovery;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.logging.ApiBootLoggingProperties.API_BOOT_LOGGING_PREFIX;

/**
 * ApiBoot Logging Admin Discovery Configuration
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-26 15:18
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(LoadBalancerClient.class)
@EnableConfigurationProperties(ApiBootLoggingProperties.class)
@ConditionalOnProperty(prefix = API_BOOT_LOGGING_PREFIX, name = "discovery.service-id")
public class ApiBootLoggingAdminDiscoveryAutoConfiguration {
    /**
     * ApiBoot Logging Properties
     */
    private ApiBootLoggingProperties apiBootLoggingProperties;

    public ApiBootLoggingAdminDiscoveryAutoConfiguration(ApiBootLoggingProperties apiBootLoggingProperties) {
        this.apiBootLoggingProperties = apiBootLoggingProperties;
    }

    /**
     * ApiBoot Logging Admin Registry Center Discovery
     *
     * @return LoggingRegistryCenterAdminDiscovery
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingRegistryCenterAdminDiscovery loggingRegistryCenterAdminDiscovery(LoadBalancerClient loadBalancerClient) {
        return new LoggingRegistryCenterAdminDiscovery(apiBootLoggingProperties.getDiscovery().getServiceId(), apiBootLoggingProperties.getDiscovery().getUsername(), apiBootLoggingProperties.getDiscovery().getPassword(), loadBalancerClient);
    }
}
