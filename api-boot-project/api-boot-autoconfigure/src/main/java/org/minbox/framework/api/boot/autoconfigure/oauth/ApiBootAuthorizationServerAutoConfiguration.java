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

import org.minbox.framework.oauth.AuthorizationServerConfiguration;
import org.minbox.framework.oauth.OAuthConfigProperties;
import org.minbox.framework.oauth.grant.OAuth2TokenGranter;
import org.minbox.framework.oauth.response.AuthorizationDeniedResponse;
import org.minbox.framework.oauth.response.DefaultAuthorizationDeniedResponse;
import org.minbox.framework.oauth.translator.DefaultWebResponseExceptionTranslator;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
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
 * Authorization server configuration
 *
 * @author 恒宇少年
 */
public class ApiBootAuthorizationServerAutoConfiguration extends AuthorizationServerConfiguration {

    protected ApiBootOauthProperties apiBootOauthProperties;

    public ApiBootAuthorizationServerAutoConfiguration(ObjectProvider<List<OAuth2TokenGranter>> objectProvider, ApiBootOauthProperties apiBootOauthProperties) {
        super(objectProvider);
        this.apiBootOauthProperties = apiBootOauthProperties;
    }

    /**
     * Configure {@link OAuthConfigProperties}
     *
     * @return {@link OAuthConfigProperties}
     */
    @Bean
    @ConditionalOnMissingBean
    public OAuthConfigProperties oAuthConfigProperties() {
        OAuthConfigProperties properties = new OAuthConfigProperties();
        properties.setAlwaysCreateToken(this.apiBootOauthProperties.isAlwaysCreateToken());
        return properties;
    }

    /**
     * Configure jwt {@link AccessTokenConverter}
     * <p>
     * If the value of the configuration "api.boot.oauth.jwt.enable" is "true"
     * Use {@link JwtAccessTokenConverter}
     *
     * @return {@link JwtAccessTokenConverter} instance
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "jwt.enable", havingValue = "true")
    public AccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(apiBootOauthProperties.getJwt().getSignKey());
        return converter;
    }

    /**
     * Configure default {@link AccessTokenConverter}
     * <p>
     * If the value of the configuration "api.boot.oauth.jwt.enable" is "false" or missing
     * Use {@link DefaultAccessTokenConverter}
     *
     * @return {@link DefaultAccessTokenConverter} instance
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "jwt.enable", havingValue = "false", matchIfMissing = true)
    public AccessTokenConverter defaultAccessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }

    /**
     * Configure custom serialization authentication error format
     *
     * @return The {@link DefaultAuthorizationDeniedResponse} instance
     * @see DefaultAuthorizationDeniedResponse
     */
    @Bean
    @ConditionalOnMissingBean
    public AuthorizationDeniedResponse authorizationDeniedResponse() {
        return new DefaultAuthorizationDeniedResponse();
    }

    /**
     * Configure {@link WebResponseExceptionTranslator}
     *
     * @param authorizationDeniedResponse The {@link AuthorizationDeniedResponse} instance
     * @return {@link WebResponseExceptionTranslator} instance
     * @see AuthorizationServerConfiguration#configure(org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer)
     * @see DefaultWebResponseExceptionTranslator
     */
    @Bean
    @ConditionalOnBean(AuthorizationDeniedResponse.class)
    @ConditionalOnMissingBean
    public WebResponseExceptionTranslator webResponseExceptionTranslator(AuthorizationDeniedResponse authorizationDeniedResponse) {
        return new DefaultWebResponseExceptionTranslator(authorizationDeniedResponse);
    }
}
