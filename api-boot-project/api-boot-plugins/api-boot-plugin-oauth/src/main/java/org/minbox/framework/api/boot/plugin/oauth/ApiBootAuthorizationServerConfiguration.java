package org.minbox.framework.api.boot.plugin.oauth;

import org.minbox.framework.api.boot.plugin.oauth.grant.ApiBootOauthTokenGranter;
import org.minbox.framework.api.boot.plugin.oauth.grant.DefaultApiBootOauthTokenGranter;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.client.ClientCredentialsTokenGranter;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeServices;
import org.springframework.security.oauth2.provider.code.AuthorizationCodeTokenGranter;
import org.springframework.security.oauth2.provider.code.InMemoryAuthorizationCodeServices;
import org.springframework.security.oauth2.provider.implicit.ImplicitTokenGranter;
import org.springframework.security.oauth2.provider.password.ResourceOwnerPasswordTokenGranter;
import org.springframework.security.oauth2.provider.refresh.RefreshTokenGranter;
import org.springframework.security.oauth2.provider.request.DefaultOAuth2RequestFactory;
import org.springframework.security.oauth2.provider.token.*;
import org.springframework.security.oauth2.provider.token.store.JwtAccessTokenConverter;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * ApiBoot 集成Oauth2 相关配置实现
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-25 18:07
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootAuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
    /**
     * authentication manager
     */
    @Autowired
    private AuthenticationManager authenticationManager;
    /**
     * Token Store
     */
    @Autowired
    private TokenStore tokenStore;
    /**
     * Access Token Converter
     */
    @Autowired
    private AccessTokenConverter accessTokenConverter;
    /**
     * Oauth Client Detail Service
     */
    @Autowired
    private ClientDetailsService clientDetailsService;
    /**
     * Instance of custom authorization provided by ApiBoot
     */
    private List<ApiBootOauthTokenGranter> apiBootOauthTokenGranters;

    public ApiBootAuthorizationServerConfiguration(ObjectProvider<List<ApiBootOauthTokenGranter>> objectProvider) {
        this.apiBootOauthTokenGranters = objectProvider.getIfAvailable();
    }

    /**
     * Configure secret encryption in the same way as ApiBoot Security
     *
     * @param security AuthorizationServerSecurityConfigurer
     * @throws Exception 异常信息
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer security) throws Exception {
        security
                .passwordEncoder(passwordEncoder())
                // Configure open/oauth/token_key access address
                .tokenKeyAccess("permitAll()")
                // Configure Open /oauth/check_token Access Address
                // Access must be accessible after login privileges
                .checkTokenAccess("isAuthenticated()");
    }

    /**
     * Configuration and Integration of Spring Security to Complete User Validity Authentication
     *
     * @param endpoints AuthorizationServerEndpointsConfigurer
     * @throws Exception 异常信息
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints
                .authenticationManager(authenticationManager)
                .tokenStore(tokenStore)
                // ApiBoot custom token granter
                .tokenGranter(tokenGranter())
                .accessTokenConverter(accessTokenConverter);
    }

    /**
     * Password Encryption for Users Logging in or Obtaining Token
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * token granter
     *
     * @return TokenGranter
     */
    private TokenGranter tokenGranter() {
        TokenGranter tokenGranter = new TokenGranter() {
            private CompositeTokenGranter delegate;

            @Override
            public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
                if (delegate == null) {
                    delegate = new CompositeTokenGranter(getDefaultTokenGranters());
                }
                return delegate.grant(grantType, tokenRequest);
            }
        };
        return tokenGranter;
    }

    /**
     * token enhancer
     *
     * @return TokenEnhancer
     */
    private TokenEnhancer tokenEnhancer() {
        if (accessTokenConverter instanceof JwtAccessTokenConverter) {
            return (TokenEnhancer) accessTokenConverter;
        }
        return null;
    }

    private DefaultTokenServices tokenServices() {
        DefaultTokenServices tokenServices = new DefaultTokenServices();
        tokenServices.setTokenStore(tokenStore);
        tokenServices.setSupportRefreshToken(true);
        tokenServices.setReuseRefreshToken(true);
        tokenServices.setClientDetailsService(clientDetailsService);
        tokenServices.setTokenEnhancer(tokenEnhancer());
        return tokenServices;
    }

    private AuthorizationCodeServices authorizationCodeServices() {
        return new InMemoryAuthorizationCodeServices();
    }

    private OAuth2RequestFactory requestFactory() {
        return new DefaultOAuth2RequestFactory(clientDetailsService);
    }

    /**
     * Return all granters within oauth2
     * Contains custom
     *
     * @return TokenGranter
     */
    private List<TokenGranter> getDefaultTokenGranters() {
        ClientDetailsService clientDetails = clientDetailsService;
        AuthorizationServerTokenServices tokenServices = tokenServices();
        AuthorizationCodeServices authorizationCodeServices = authorizationCodeServices();
        OAuth2RequestFactory requestFactory = requestFactory();

        List<TokenGranter> tokenGranters = new ArrayList<TokenGranter>();
        // code
        tokenGranters.add(new AuthorizationCodeTokenGranter(tokenServices, authorizationCodeServices, clientDetails,
                requestFactory));

        // refresh token
        tokenGranters.add(new RefreshTokenGranter(tokenServices, clientDetails, requestFactory));

        // implicit
        ImplicitTokenGranter implicit = new ImplicitTokenGranter(tokenServices, clientDetails, requestFactory);
        tokenGranters.add(implicit);

        // client
        tokenGranters.add(new ClientCredentialsTokenGranter(tokenServices, clientDetails, requestFactory));

        // password
        if (authenticationManager != null) {
            tokenGranters.add(new ResourceOwnerPasswordTokenGranter(authenticationManager, tokenServices,
                    clientDetails, requestFactory));
        }

        // have custom token granter
        if (!ObjectUtils.isEmpty(apiBootOauthTokenGranters)) {
            apiBootOauthTokenGranters.stream().forEach(apiBootOauthTokenGranter -> tokenGranters.add(new DefaultApiBootOauthTokenGranter(tokenServices, clientDetailsService, requestFactory, apiBootOauthTokenGranter)));
        }

        return tokenGranters;
    }
}
