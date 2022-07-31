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

import lombok.Data;
import org.minbox.framework.api.boot.push.model.PushClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.minbox.framework.api.boot.autoconfigure.push.ApiBootMessagePushProperties.API_BOOT_PUSH_PREFIX;

/**
 * ApiBoot Message Push Properties
 *
 * @author 恒宇少年
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_PUSH_PREFIX)
public class ApiBootMessagePushProperties {
    /**
     * config prefix
     */
    public static final String API_BOOT_PUSH_PREFIX = "api.boot.push";
    /**
     * The simple push client config parameters
     */
    @NestedConfigurationProperty
    private PushClientConfig client;
    /**
     * The multiple push client config parameters
     */
    private Map<String, PushClientConfig> multiple;
    /**
     * Configure whether to push in the production environment, applicable to IOS platform
     */
    private boolean production = false;
}
