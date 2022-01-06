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

package org.minbox.framework.api.boot.autoconfigure.oauth;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.oauth.AuthorizationServerConfiguration;
import org.minbox.framework.oauth.grant.OAuth2TokenGranter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;
import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.oauth.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;

/**
 * Redis authorization server
 *
 * @author 恒宇少年
 */
@Configuration
@EnableConfigurationProperties(ApiBootOauthProperties.class)
@EnableAuthorizationServer
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnClass({AuthorizationServerConfiguration.class})
@ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "away", havingValue = "redis")
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class ApiBootAuthorizationServerRedisAutoConfiguration extends ApiBootAuthorizationServerAutoConfiguration {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootAuthorizationServerRedisAutoConfiguration.class);
    /**
     * redis connection factory
     */
    private RedisConnectionFactory redisConnectionFactory;
    private DataSource dataSource;

    public ApiBootAuthorizationServerRedisAutoConfiguration(ObjectProvider<List<OAuth2TokenGranter>> objectProvider,
                                                            ApiBootOauthProperties apiBootOauthProperties,
                                                            ObjectProvider<RedisConnectionFactory> connectionFactoryProvider,
                                                            ObjectProvider<DataSource> dataSourceProvider) {
        super(objectProvider, apiBootOauthProperties);
        this.redisConnectionFactory = connectionFactoryProvider.getIfAvailable();
        this.dataSource = dataSourceProvider.getIfAvailable();
        logger.info("ApiBoot Oauth2 initialize using redis.");
    }

    /**
     * configuration clients
     *
     * @param clients client details service configuration
     * @throws Exception exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        if (OAuthClientStorageAway.jdbc == apiBootOauthProperties.getClientStorageAway() && dataSource == null) {
            throw new ApiBootException("If you use jdbc to store the client, please instantiate the datasource.");
        }
        switch (apiBootOauthProperties.getClientStorageAway()) {
            case memory:
                InMemoryClientDetailsServiceBuilder inMemoryClientDetailsServiceBuilder = clients.inMemory();
                apiBootOauthProperties.getClients().stream().forEach(client -> inMemoryClientDetailsServiceBuilder.withClient(client.getClientId())
                    .secret(passwordEncoder().encode(client.getClientSecret()))
                    .authorizedGrantTypes(client.getGrantTypes())
                    .scopes(client.getScopes())
                    .resourceIds(client.getResourceId())
                    .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds())
                    .refreshTokenValiditySeconds(client.getRefreshTokenValiditySeconds()));
                break;
            case jdbc:
                clients.jdbc(dataSource);
                break;
        }
    }

    /**
     * Configure Redis {@link TokenStore}
     *
     * @return {@link RedisTokenStore} instance
     */
    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
