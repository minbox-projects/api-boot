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

package org.minbox.framework.api.boot.autoconfigure.logging.admin.ui;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.logging.admin.ui.ApiBootLoggingAdminUiProperties.API_BOOT_LOGGING_ADMIN_UI_PREFIX;

/**
 * ApiBoot Logging Admin Ui Properties
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-31 17:09
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConfigurationProperties(prefix = API_BOOT_LOGGING_ADMIN_UI_PREFIX)
@Data
public class ApiBootLoggingAdminUiProperties {
    /**
     * ApiBoot logging properties config prefix
     */
    public static final String API_BOOT_LOGGING_ADMIN_UI_PREFIX = "api.boot.logging.admin.ui";
    /**
     * ApiBoot Logging Admin Ui Resource Locations
     */
    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {"classpath:/META-INF/api-boot-logging-admin-ui/"};
    /**
     * Locations of ApiBoot Logging Admin ui resources.
     */
    private String[] resourceLocations = CLASSPATH_RESOURCE_LOCATIONS;
    /**
     * Locations of ApiBoot Logging Admin ui template.
     */
    private String templateLocation = CLASSPATH_RESOURCE_LOCATIONS[0];
    /**
     * Wether the thymeleaf templates should be cached.
     */
    private boolean cacheTemplates = true;
    /**
     * Page Title
     */
    private String title = "ApiBoot Logging Admin";
    /**
     * ApiBoot Logo
     */
    private String brand = "<img src=\"assets/img/apiboot-white.png\">";
}
