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

package org.minbox.framework.api.boot.autoconfigure.security.web;

import org.minbox.framework.api.boot.autoconfigure.security.ApiBootSecurityProperties;
import org.minbox.framework.api.boot.autoconfigure.security.SecurityUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configurers.provisioning.InMemoryUserDetailsManagerConfigurer;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.ObjectUtils;

import javax.annotation.PostConstruct;
import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.security.ApiBootSecurityProperties.API_BOOT_SECURITY_PREFIX;

/**
 * ApiBoot SpringSecurity自动化封装配置内存的实现
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 15:46
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnProperty(prefix = API_BOOT_SECURITY_PREFIX, name = "away", havingValue = "memory", matchIfMissing = true)
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
@Import(ApiBootWebSecurityAutoConfiguration.class)
public class ApiBootWebSecurityMemoryAutoConfiguration {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootWebSecurityMemoryAutoConfiguration.class);

    /**
     * 密码加密方式
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 注入属性配置
     */
    @Autowired
    private ApiBootSecurityProperties apiBootSecurityProperties;
    /**
     * 安全认证管理构建对象
     * 配置指定内存、数据源方式用户认证以及密码加密方式
     */
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;

    /**
     * 配置内存方式读取ApiBootProperties内的配置用户列表
     */
    @PostConstruct
    public void configure() {
        try {
            InMemoryUserDetailsManagerConfigurer<AuthenticationManagerBuilder> userDetailsManagerConfigurer = authenticationManagerBuilder.inMemoryAuthentication().passwordEncoder(passwordEncoder);
            // 获取到配置的用户列表
            List<SecurityUser> users = apiBootSecurityProperties.getUsers();
            if (!ObjectUtils.isEmpty(users)) {
                // 遍历用户列表进行配置
                for (SecurityUser securityUser : users) {
                    userDetailsManagerConfigurer
                            .withUser(securityUser.getUsername())
                            .password(passwordEncoder.encode(securityUser.getPassword()))
                            .roles(securityUser.getRoles());
                }
            }
        } catch (Exception e) {
            logger.error("Exceptions for users configuring memory mode", e);
        }
    }
}
