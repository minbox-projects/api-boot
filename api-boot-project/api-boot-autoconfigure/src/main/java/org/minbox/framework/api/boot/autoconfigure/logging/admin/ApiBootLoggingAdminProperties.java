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

package org.minbox.framework.api.boot.autoconfigure.logging.admin;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.logging.admin.ApiBootLoggingAdminProperties.API_BOOT_LOGGING_ADMIN_PREFIX;

/**
 * ApiBoot Logging Admin Properties
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-19 12:56
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConfigurationProperties(prefix = API_BOOT_LOGGING_ADMIN_PREFIX)
@Data
public class ApiBootLoggingAdminProperties {
    /**
     * ApiBoot logging properties config prefix
     */
    public static final String API_BOOT_LOGGING_ADMIN_PREFIX = "api.boot.logging.admin";
    /**
     * Whether to print the logs reported on the console
     */
    private boolean showConsoleReportLog = false;
    /**
     * Format console log JSON
     */
    private boolean formatConsoleLogJson = false;
}
