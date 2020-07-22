/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.minbox.framework.api.boot.autoconfigure.security;

import lombok.Data;
import org.minbox.framework.security.SecurityUser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.security.ApiBootSecurityProperties.API_BOOT_SECURITY_PREFIX;

/**
 * Integrate the properties of SpringSecurity
 *
 * @author 恒宇少年
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_SECURITY_PREFIX)
public class ApiBootSecurityProperties {
    /**
     * security prefix
     */
    public static final String API_BOOT_SECURITY_PREFIX = "api.boot.security";
    /**
     * Default set of excluded urls
     */
    public static final String[] DEFAULT_IGNORE_URLS = new String[]{
        "/v2/api-docs",
        "/swagger-ui.html",
        "/swagger-resources/configuration/security",
        "/META-INF/resources/webjars/**",
        "/webjars/**",
        "/swagger-resources",
        "/swagger-resources/configuration/ui",
        "/actuator/**"
    };
    /**
     * Authentication address prefix
     * <p>
     * By default, only the interface address under "/api/**" is intercepted
     */
    private String[] authPrefix = new String[]{"/api/**"};
    /**
     * Authentication user storage method
     * <p>
     * By default, use {@link SecurityAway#memory} away
     *
     * @see SecurityAway
     */
    private SecurityAway away = SecurityAway.memory;
    /**
     * List of authenticated users
     * <p>
     * This property will only take effect if you use {@link SecurityAway#memory}
     */
    private List<SecurityUser> users = new ArrayList<>();
    /**
     * Exclude permissions blocked path
     * <p>
     * By default, use {@link #DEFAULT_IGNORE_URLS}
     */
    private String[] ignoringUrls;
    /**
     * Whether to enable the default user information storage delegation
     * <p>
     * When using {@link SecurityAway#jdbc} to store authenticated users,
     * the data in the "api_boot_user_info" table will be read by default for authentication
     * <p>
     * If the value is set to false,
     * you need to implement the {@link org.minbox.framework.security.delegate.SecurityStoreDelegate} interface to complete the custom method of reading user data
     */
    private boolean enableDefaultStoreDelegate = true;
    /**
     * Whether to disable http basic authentication
     */
    private boolean disableHttpBasic = true;
    /**
     * Whether to disable csrf
     */
    private boolean disableCsrf = true;
}
