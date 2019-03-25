package org.minbox.framework.api.boot.plugin.oauth2;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.AccessTokenConverter;
import org.springframework.security.oauth2.provider.token.TokenStore;

/**
 * ApiBoot 集成Oauth2 相关配置实现
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-25 11:47
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
public class ApiBootAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    /**
     * 认证管理
     * 整合SpringSecurity
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * 令牌存储
     */
    @Autowired
    private TokenStore tokenStore;
    /**
     * 令牌转换
     */
    @Autowired
    private AccessTokenConverter accessTokenConverter;

    /**
     * 配置secret的加密方式与ApiBoot Security一致
     *
     * @param security AuthorizationServerSecurityConfigurer
     * @throws Exception 异常信息
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(passwordEncoder())
                // 配置开放/oauth/token_key访问地址
                .tokenKeyAccess("permitAll()")
                // 配置开放/oauth/check_token访问地址
                // 必须登录有权限后才可以访问
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * 配置整合SpringSecurity完成用户有效性认证
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @throws Exception 异常信息
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                .accessTokenConverter(accessTokenConverter);
    }

    /**
     * 用户登录或者获取Token时的密码加密方式
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
