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
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.TokenStore;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import static org.minbox.framework.api.boot.autoconfigure.security.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;

/**
 * ApiBoot授权服务器配置
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 16:51
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(AuthorizationServerConfigurerAdapter.class)
@EnableConfigurationProperties(ApiBootOauthProperties.class)
@EnableAuthorizationServer
public class ApiBootAuthorizationServerAutoConfiguration extends AuthorizationServerConfigurerAdapter {
    /**
     * 用户授权认证
     */
    @Autowired
    private AuthenticationManager authenticationManager;

    /**
     * 注入属性配置
     */
    @Autowired
    private ApiBootOauthProperties apiBootOauthProperties;
    /**
     * accessToken存储方式
     * 默认内存方式
     *
     * @see ApiBootAuthorizationMemoryServerAutoConfiguration
     * @see ApiBootAuthorizationServerJdbcAutoConfiguration
     */
    @Autowired
    private TokenStore tokenStore;

    /**
     * 配置整合SpringSecurity后需要开放OAuth2内部的访问地址
     * 默认开放：/oauth/token
     * 如果你需要开放其他的如：/oauth/check_token、/oauth/token_key等地址
     * 需要在该方法配置权限过滤或者直接去SpringSecurity配置HttpSecurity地址过滤
     *
     * @param oauthServer oauth服务安全配置
     * @throws Exception 异常信息
     */
    @Override
    public void configure(
            AuthorizationServerSecurityConfigurer oauthServer)
            throws Exception {
        oauthServer
                // 配置开放/oauth/token_key访问地址
                .tokenKeyAccess("permitAll()")
                // 配置开放/oauth/check_token访问地址
                // 必须登录有权限后才可以访问
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置jwt生成token的转换
     * 使用自定义Sign Key 进行加密
     *
     * @return Jwt Access Token转换实例
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "jwt.enable", havingValue = "true")
    public JwtAccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(apiBootOauthProperties.getJwt().getSignKey());
        return converter;
    }

    /**
     * 配置授权服务端点
     *
     * @param endpoints 授权服务端点
     * @throws Exception 异常信息
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                // 配置SpringSecurity安全管理对象
                .authenticationManager(authenticationManager)
                // 配置令牌存储方式
                .tokenStore(tokenStore);

        // 开启Jwt，进行配置accessToken的转换
        if (apiBootOauthProperties.getJwt().isEnable()) {
            endpoints.accessTokenConverter(jwtAccessTokenConverter());
        }
    }
}
