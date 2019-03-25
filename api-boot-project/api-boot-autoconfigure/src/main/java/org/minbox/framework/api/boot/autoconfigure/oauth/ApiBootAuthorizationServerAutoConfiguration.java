package org.minbox.framework.api.boot.autoconfigure.oauth;

import org.minbox.framework.api.boot.plugin.oauth2.ApiBootAuthorizationServerConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.DefaultAccessTokenConverter;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;

import static org.minbox.framework.api.boot.autoconfigure.oauth.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;

/**
 * ApiBoot OAuth2 自动化配置
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-25 11:49
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
public class ApiBootAuthorizationServerAutoConfiguration extends ApiBootAuthorizationServerConfiguration {
    /**
     * 注入属性配置
     */
    protected ApiBootOauthProperties apiBootOauthProperties;

    public ApiBootAuthorizationServerAutoConfiguration(ApiBootOauthProperties apiBootOauthProperties) {
        this.apiBootOauthProperties = apiBootOauthProperties;
    }

    /**
     * 配置jwt生成token的转换
     * 使用自定义Sign Key 进行加密
     *
     * @return Jwt Access Token转换实例
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "jwt.enable", havingValue = "true")
    public AccessTokenConverter jwtAccessTokenConverter() {
        JwtAccessTokenConverter converter = new JwtAccessTokenConverter();
        converter.setSigningKey(apiBootOauthProperties.getJwt().getSignKey());
        return converter;
    }

    /**
     * 默认token转换
     * 不配置jwt转换时
     *
     * @return AccessTokenConverter
     */
    @Bean
    @ConditionalOnProperty(prefix = API_BOOT_OAUTH_PREFIX, name = "jwt.enable", havingValue = "false", matchIfMissing = true)
    public AccessTokenConverter defaultAccessTokenConverter() {
        return new DefaultAccessTokenConverter();
    }
}
