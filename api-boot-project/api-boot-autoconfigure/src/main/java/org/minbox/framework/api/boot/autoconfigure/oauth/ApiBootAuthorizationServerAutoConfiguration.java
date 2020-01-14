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

import org.minbox.framework.api.boot.plugin.oauth.ApiBootAuthorizationServerConfiguration;
import org.minbox.framework.api.boot.plugin.oauth.grant.ApiBootOauthTokenGranter;
import org.minbox.framework.api.boot.plugin.oauth.response.AuthorizationDeniedResponse;
import org.minbox.framework.api.boot.plugin.oauth.response.DefaultAuthorizationDeniedResponse;
import org.minbox.framework.api.boot.plugin.oauth.translator.ApiBootWebResponseExceptionTranslator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.oauth.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;

/**
 * ApiBoot Authorization Server Configuration
 * Define {@link AccessTokenConverter} based on configuration
 * Specify the {@link WebResponseExceptionTranslator} used by the exception
 *
 * @author：恒宇少年 - 于起宇
 * @see JwtAccessTokenConverter
 * @see DefaultAccessTokenConverter
 * @see org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator
 * @see ApiBootWebResponseExceptionTranslator
 */
public class ApiBootAuthorizationServerAutoConfiguration extends ApiBootAuthorizationServerConfiguration {
    /**
     * Oauth Config Properties
     */
    protected ApiBootOauthProperties apiBootOauthProperties;

    public ApiBootAuthorizationServerAutoConfiguration(ObjectProvider<List<ApiBootOauthTokenGranter>> objectProvider,
                                                       ApiBootOauthProperties apiBootOauthProperties) {
        super(objectProvider);
        this.apiBootOauthProperties = apiBootOauthProperties;
    }

    /**
     * This method will be instantiated if "api.boot.oauth.jwt.enable = true" is configured
     * The "api.boot.oauth.jwt.sign-key" parameter will be used as the encrypted key value
     * the sign-key parameter default value is "ApiBoot"
     *
     * @return {@link AccessTokenConverter}
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "jwt.enable", havingValue = "true")
    public AccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(apiBootOauthProperties.getJwt().getSignKey());
        return converter;
    }

    /**
     * The default {@link AccessTokenConverter}
     *
     * @return {@link DefaultAccessTokenConverter}
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "jwt.enable", havingValue = "false", matchIfMissing = true)
    public AccessTokenConverter defaultAccessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }

    /**
     * ApiBoot's authentication server refuses to authorize the response interface
     * Because "@ConditionalOnMissingBean" is used,
     * only the AuthorizationDeniedResponse interface needs to be implemented when customizing the authentication failure response content,
     * and then added to the Spring IOC
     *
     * @return {@link DefaultAuthorizationDeniedResponse}
     * @see AuthorizationDeniedResponse
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthorizationDeniedResponse authorizationDeniedResponse() {
        return new DefaultAuthorizationDeniedResponse();
    }

    /**
     * Customize the exception output format of the authentication server
     *
     * @return {@link ApiBootWebResponseExceptionTranslator}
     * @see WebResponseExceptionTranslator
     * @see org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator
     * @see ApiBootWebResponseExceptionTranslator
     */
    @Bean
    @ConditionalOnMissingBean
    public WebResponseExceptionTranslator webResponseExceptionTranslator(AuthorizationDeniedResponse response) {
        return new ApiBootWebResponseExceptionTranslator(response);
    }
}
