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

package org.minbox.framework.api.boot.autoconfigure.security.authorization;

import org.minbox.framework.api.boot.autoconfigure.security.ApiBootOauthProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;

import javax.annotation.PostConstruct;

import static org.minbox.framework.api.boot.autoconfigure.security.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;



/**
 * ApiBoot 授权服务器内存方式实现
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 16:53
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(AuthorizationServerConfigurerAdapter.class)
@ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "away", havingValue = "memory", matchIfMissing = true)
@Import(ApiBootAuthorizationServerAutoConfiguration.class)
public class ApiBootAuthorizationMemoryServerAutoConfiguration {
    /**
     * 注入客户端详情配置
     */
    @Autowired
    private ClientDetailsServiceConfigurer clientDetailsServiceConfigurer;

    /**
     * 加密方式
     */
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 注入属性配置
     */
    @Autowired
    private ApiBootOauthProperties apiBootOauthProperties;

    /**
     * 配置内存方式Oauth2的客户端信息
     *
     * @throws Exception 异常信息
     */
    @PostConstruct
    public void configure() throws Exception {
        clientDetailsServiceConfigurer.inMemory()
                .withClient(apiBootOauthProperties.getClientId())
                .secret(passwordEncoder.encode(apiBootOauthProperties.getClientSecret()))
                .authorizedGrantTypes(apiBootOauthProperties.getGrantTypes())
                .scopes(apiBootOauthProperties.getScopes());
    }

    /**
     * 令牌存储方式（内存方式）
     *
     * @return Token存储方式
     */
    @Bean
    TokenStore memoryTokenStore() {
        return new InMemoryTokenStore();
    }
}
