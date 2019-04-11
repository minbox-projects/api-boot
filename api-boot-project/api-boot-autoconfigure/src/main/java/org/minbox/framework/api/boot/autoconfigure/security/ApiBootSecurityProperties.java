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
import org.minbox.framework.api.boot.plugin.security.SecurityUser;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.security.ApiBootSecurityProperties.API_BOOT_SECURITY_PREFIX;

/**
 * 整合Spring Security 相关属性配置
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 15:26
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_SECURITY_PREFIX)
public class ApiBootSecurityProperties {
    /**
     * 安全配置前缀
     */
    public static final String API_BOOT_SECURITY_PREFIX = "api.boot.security";
    /**
     * 默认的排除路径列表
     */
    public static final String[] DEFAULT_IGNORE_URLS = new String[]{
            "/v2/api-docs",
            "/swagger-ui.html",
            "/swagger-resources/configuration/security",
            "/META-INF/resources/webjars/**",
            "/swagger-resources",
            "/swagger-resources/configuration/ui",
            "/actuator/**"
    };
    /**
     * 认证接口地址的前缀
     * 默认只拦截/api/**下的接口地址
     */
    private String[] authPrefix = new String[]{"/api/**"};
    /**
     * 认证用户存储方式，默认为内存方式
     *
     * @see SecurityAway
     */
    private SecurityAway away = SecurityAway.memory;
    /**
     * 配置内存方式的用户列表
     * key：用户名
     * value：密码
     */
    private List<SecurityUser> users = new ArrayList<>();
    /**
     * 排除路径列表，默认排除swagger2以及actuator路径
     */
    private String[] ignoringUrls;
    /**
     * 是否启用默认的用户信息存储委托
     * 如果away=jdbc时，该配置会自动读取api_boot_user_info表内的用户数据
     * 如果配置该值为false，需要实现ApiBootStoreDelegate接口来完成自定义读取用户数据方法
     */
    private boolean enableDefaultStoreDelegate = true;
    /**
     * 禁用Http Basic
     */
    private boolean disableHttpBasic = true;
    /**
     * 禁用CSRF
     */
    private boolean disableCsrf = true;
}
