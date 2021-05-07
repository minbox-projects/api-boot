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

package org.minbox.framework.api.boot.autoconfigure.oauth;

import org.minbox.framework.api.boot.autoconfigure.security.ApiBootSecurityProperties;
import org.minbox.framework.oauth.entrypoint.AccessTokenInvalidAuthenticationEntryPoint;
import org.minbox.framework.oauth.response.AccessTokenInvalidResponse;
import org.minbox.framework.oauth.response.DefaultAccessTokenInvalidResponse;
import org.minbox.framework.security.SecurityUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
import org.springframework.security.web.AuthenticationEntryPoint;

/**
 * Resource server configuration
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass({ResourceServerConfigurerAdapter.class, SecurityUser.class})
@EnableConfigurationProperties({ApiBootSecurityProperties.class, ApiBootOauthProperties.class})
@EnableResourceServer
public class ApiBootResourceServerAutoConfiguration extends ResourceServerConfigurerAdapter {

    private ApiBootSecurityProperties apiBootSecurityProperties;
    private ApiBootOauthProperties apiBootOauthProperties;

    public ApiBootResourceServerAutoConfiguration(ApiBootSecurityProperties apiBootSecurityProperties, ApiBootOauthProperties apiBootOauthProperties) {
        this.apiBootSecurityProperties = apiBootSecurityProperties;
        this.apiBootOauthProperties = apiBootOauthProperties;
    }

    /**
     * Configure resource server auth prefix
     *
     * @param http {@link HttpSecurity}
     * @throws Exception The exception instance
     */
    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
            .anyRequest()
            .authenticated()
            .and()
            .requestMatchers()
            .antMatchers(apiBootSecurityProperties.getAuthPrefix());
    }

    /**
     * Configure custom serialization authentication error format
     *
     * @return The {@link DefaultAccessTokenInvalidResponse} instance
     * @see DefaultAccessTokenInvalidResponse
     */
    @Bean
    @ConditionalOnMissingBean
    public AccessTokenInvalidResponse accessTokenInvalidResponse() {
        return new DefaultAccessTokenInvalidResponse();
    }

    /**
     * Instance {@link AuthenticationEntryPoint} support class
     *
     * @return {@link AccessTokenInvalidAuthenticationEntryPoint}
     */
    @Bean
    public AuthenticationEntryPoint tokenInvalidAuthenticationEntryPoint() {
        return new AccessTokenInvalidAuthenticationEntryPoint(accessTokenInvalidResponse());
    }

    @Override
    public void configure(ResourceServerSecurityConfigurer resources) throws Exception {
        resources
            .resourceId(apiBootOauthProperties.getResourceId())
            .authenticationEntryPoint(tokenInvalidAuthenticationEntryPoint());
    }
}
