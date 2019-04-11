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

import org.minbox.framework.api.boot.plugin.security.ApiBootWebSecurityConfiguration;
import org.minbox.framework.api.boot.plugin.security.SecurityUser;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.security.ApiBootSecurityProperties.API_BOOT_SECURITY_PREFIX;

/**
 * ApiBoot SpringSecurity自动化封装配置内存的实现
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 15:46
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(ApiBootSecurityProperties.class)
@ConditionalOnClass(ApiBootWebSecurityConfiguration.class)
@ConditionalOnProperty(prefix = API_BOOT_SECURITY_PREFIX, name = "away", havingValue = "memory", matchIfMissing = true)
public class ApiBootWebSecurityMemoryAutoConfiguration extends ApiBootWebSecurityAutoConfiguration {
    public ApiBootWebSecurityMemoryAutoConfiguration(ApiBootSecurityProperties apiBootSecurityProperties, ObjectProvider<AccessDeniedHandler> accessDeniedHandler, ObjectProvider<AuthenticationEntryPoint> authenticationEntryPoint) {
        super(apiBootSecurityProperties, accessDeniedHandler.getIfAvailable(), authenticationEntryPoint.getIfAvailable());
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager memoryUserDetails = new InMemoryUserDetailsManager();
        List<SecurityUser> users = apiBootSecurityProperties.getUsers();
        if (!ObjectUtils.isEmpty(users)) {
            for (SecurityUser securityUser : users) {
                memoryUserDetails.createUser(User.builder().username(securityUser.getUsername()).password(passwordEncoder().encode(securityUser.getPassword())).roles(securityUser.getRoles()).build());
            }
        }
        return memoryUserDetails;
    }
}
