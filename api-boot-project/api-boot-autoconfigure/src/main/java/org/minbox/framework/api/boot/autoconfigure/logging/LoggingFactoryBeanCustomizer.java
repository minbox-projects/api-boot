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

import org.minbox.framework.logging.client.LoggingFactoryBean;

/**
 * logging factory bean customizer
 * Initialization logging factory bean calls all implementation classes
 *
 * @author 恒宇少年
 */
public interface LoggingFactoryBeanCustomizer {
    /**
     * Customize the given a {@link LoggingFactoryBean} object.
     *
     * @param factoryBean the logging factory bean
     */
    void customize(LoggingFactoryBean factoryBean);
}
