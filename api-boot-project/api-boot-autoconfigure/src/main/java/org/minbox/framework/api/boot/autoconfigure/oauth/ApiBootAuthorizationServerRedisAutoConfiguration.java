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

import org.minbox.framework.api.boot.plugin.oauth.ApiBootAuthorizationServerConfiguration;
import org.minbox.framework.api.boot.plugin.oauth.grant.ApiBootOauthTokenGranter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.config.annotation.builders.InMemoryClientDetailsServiceBuilder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JdbcTokenStore;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

import javax.sql.DataSource;

import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.oauth.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-13 09:35
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@EnableConfigurationProperties(ApiBootOauthProperties.class)
@EnableAuthorizationServer
@ConditionalOnBean(RedisConnectionFactory.class)
@ConditionalOnClass({ApiBootAuthorizationServerConfiguration.class})
@ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "away", havingValue = "redis")
@AutoConfigureAfter(RedisAutoConfiguration.class)
public class ApiBootAuthorizationServerRedisAutoConfiguration extends ApiBootAuthorizationServerAutoConfiguration {
    /**
     * redis connection factory
     */
    private RedisConnectionFactory redisConnectionFactory;

    /**
     * constructor instance redis connection factory
     *
     * @param objectProvider         ApiBoot Token Granter
     * @param apiBootOauthProperties ApiBoot Oauth Properties
     * @param redisConnectionFactory Redis Connection Factory
     */
    public ApiBootAuthorizationServerRedisAutoConfiguration(ObjectProvider<List<ApiBootOauthTokenGranter>> objectProvider,
                                                            ApiBootOauthProperties apiBootOauthProperties,
                                                            RedisConnectionFactory redisConnectionFactory) {
        super(objectProvider, apiBootOauthProperties);
        this.redisConnectionFactory = redisConnectionFactory;
    }

    /**
     * configuration clients
     *
     * @param clients client details service configuration
     * @throws Exception exception
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        InMemoryClientDetailsServiceBuilder inMemoryClientDetailsServiceBuilder = clients.inMemory();
        apiBootOauthProperties.getClients().stream().forEach(client -> inMemoryClientDetailsServiceBuilder.withClient(client.getClientId())
            .secret(passwordEncoder().encode(client.getClientSecret()))
            .authorizedGrantTypes(client.getGrantTypes())
            .scopes(client.getScopes())
            .resourceIds(client.getResourceId())
            .accessTokenValiditySeconds(client.getAccessTokenValiditySeconds()));
    }

    /**
     * Redis Token Store
     *
     * @return TokenStore
     */
    @Bean
    public TokenStore redisTokenStore() {
        return new RedisTokenStore(redisConnectionFactory);
    }
}
