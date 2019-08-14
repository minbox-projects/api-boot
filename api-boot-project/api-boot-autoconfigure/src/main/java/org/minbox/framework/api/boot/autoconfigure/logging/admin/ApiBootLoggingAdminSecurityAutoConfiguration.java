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

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

/**
 * ApiBoot Logging Admin SpringSecurity Config
 * {@link WebSecurityConfigurerAdapter}
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass(WebSecurityConfigurerAdapter.class)
public class ApiBootLoggingAdminSecurityAutoConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * Logging Admin Login Page
     */
    private static final String LOGIN_PAGE = "/login";
    /**
     * Logging Admin UI Resource Prefix
     */
    private static final String ASSETS_RESOURCE_PREFIX = "/assets/**";

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        SavedRequestAwareAuthenticationSuccessHandler successHandler = new SavedRequestAwareAuthenticationSuccessHandler();
        successHandler.setTargetUrlParameter("redirectTo");
        http.authorizeRequests()
                .antMatchers(ASSETS_RESOURCE_PREFIX).permitAll()
                .antMatchers(LOGIN_PAGE).permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage(LOGIN_PAGE).successHandler(successHandler).and()
                .logout().and()
                .httpBasic().and()
                .csrf().disable();
    }
}
