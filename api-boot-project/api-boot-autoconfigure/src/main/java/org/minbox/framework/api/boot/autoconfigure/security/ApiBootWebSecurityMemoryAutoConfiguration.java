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

import org.minbox.framework.security.SecurityUser;
import org.minbox.framework.security.WebSecurityConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.security.ApiBootSecurityProperties.API_BOOT_SECURITY_PREFIX;

/**
 * Automatic configuration to authenticate users using memory
 *
 * @author 恒宇少年
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(ApiBootSecurityProperties.class)
@ConditionalOnClass(WebSecurityConfiguration.class)
@ConditionalOnProperty(prefix = API_BOOT_SECURITY_PREFIX, name = "away", havingValue = "memory", matchIfMissing = true)
public class ApiBootWebSecurityMemoryAutoConfiguration extends ApiBootWebSecurityAutoConfiguration {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootWebSecurityMemoryAutoConfiguration.class);

    public ApiBootWebSecurityMemoryAutoConfiguration(ApiBootSecurityProperties apiBootSecurityProperties, ObjectProvider<AccessDeniedHandler> accessDeniedHandler, ObjectProvider<AuthenticationEntryPoint> authenticationEntryPoint) {
        super(apiBootSecurityProperties, accessDeniedHandler.getIfAvailable(), authenticationEntryPoint.getIfAvailable());
        logger.info("ApiBoot Security initialize using memory.");
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager memoryUserDetailsManager = new InMemoryUserDetailsManager();
        List<SecurityUser> users = apiBootSecurityProperties.getUsers();
        users.forEach(securityUser -> {
            String encoderPassword = passwordEncoder().encode(securityUser.getPassword());
            UserDetails userDetails =
                User.builder()
                    .username(securityUser.getUsername())
                    .password(encoderPassword)
                    .roles(securityUser.getRoles())
                    .build();
            memoryUserDetailsManager.createUser(userDetails);
        });
        return memoryUserDetailsManager;
    }
}
