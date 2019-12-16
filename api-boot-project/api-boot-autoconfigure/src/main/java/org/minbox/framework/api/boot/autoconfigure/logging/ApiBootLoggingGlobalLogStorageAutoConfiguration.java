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

import org.minbox.framework.logging.client.global.GlobalLogging;
import org.minbox.framework.logging.client.global.support.GlobalLoggingMemoryStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.logging.ApiBootLoggingProperties.API_BOOT_LOGGING_PREFIX;

/**
 * the "minbox-logging" global log storage configuration
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass(GlobalLogging.class)
public class ApiBootLoggingGlobalLogStorageAutoConfiguration {
    /**
     * Instance global log memory mode storage
     *
     * @return {@link GlobalLoggingMemoryStorage}
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_LOGGING_PREFIX,
        name = "global-logging-storage-away", havingValue = "memory", matchIfMissing = true)
    public GlobalLogging globalLogging() {
        return new GlobalLoggingMemoryStorage();
    }
}
