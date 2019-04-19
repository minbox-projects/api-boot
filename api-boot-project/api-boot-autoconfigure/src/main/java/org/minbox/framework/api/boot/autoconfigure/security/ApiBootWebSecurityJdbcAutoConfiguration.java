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
import org.minbox.framework.api.boot.plugin.security.delegate.ApiBootDefaultStoreDelegate;
import org.minbox.framework.api.boot.plugin.security.delegate.ApiBootStoreDelegate;
import org.minbox.framework.api.boot.plugin.security.userdetails.ApiBootUserDetailsService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.sql.DataSource;

import static org.minbox.framework.api.boot.autoconfigure.security.ApiBootSecurityProperties.API_BOOT_SECURITY_PREFIX;

/**
 * ApiBoot SpringSecurity自动化封装配置Jdbc的实现
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 15:58
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
@ConditionalOnBean(DataSource.class)
@ConditionalOnProperty(prefix = API_BOOT_SECURITY_PREFIX, name = "away", havingValue = "jdbc")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class ApiBootWebSecurityJdbcAutoConfiguration extends ApiBootWebSecurityAutoConfiguration {

    public ApiBootWebSecurityJdbcAutoConfiguration(ApiBootSecurityProperties apiBootSecurityProperties, ObjectProvider<AccessDeniedHandler> accessDeniedHandler, ObjectProvider<AuthenticationEntryPoint> authenticationEntryPoint) {
        super(apiBootSecurityProperties, accessDeniedHandler.getIfAvailable(), authenticationEntryPoint.getIfAvailable());
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        return new ApiBootUserDetailsService();
    }

    /**
     * 开启使用ApiBoot默认自带的用户信息表
     *
     * @return ApiBootStoreDelegate
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_SECURITY_PREFIX, name = "enable-default-store-delegate", havingValue = "true", matchIfMissing = true)
    public ApiBootStoreDelegate apiBootStoreDelegate(DataSource dataSource) {
        return new ApiBootDefaultStoreDelegate(dataSource);
    }
}
