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

import lombok.Data;
import org.minbox.framework.api.boot.autoconfigure.security.SecurityAway;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.oauth.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;

/**
 * 整合Oauth2 相关属性配置
 *
 * @author 恒宇少年
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_OAUTH_PREFIX)
public class ApiBootOauthProperties {
    /**
     * 安全配置前缀
     */
    public static final String API_BOOT_OAUTH_PREFIX = "api.boot.oauth";
    /**
     * Oauth2认证存储方式，默认内存方式
     *
     * @see OAuthAway
     */
    private OAuthAway away = OAuthAway.memory;

    /**
     * Oauth2 clientId
     * 2.1.1. After the RELEASE version, the attribute is discarded and replaced by clients.
     */
    @Deprecated
    private String clientId = "ApiBoot";
    /**
     * Oauth2 clientSecret
     * 2.1.1. After the RELEASE version, the attribute is discarded and replaced by clients.
     */
    @Deprecated
    private String clientSecret = "ApiBootSecret";
    /**
     * 客户端授权类型集合
     * 2.1.1. After the RELEASE version, the attribute is discarded and replaced by clients.
     */
    @Deprecated
    private String[] grantTypes = new String[]{"password", "refresh_token"};
    /**
     * 客户端作用域集合
     * 2.1.1. After the RELEASE version, the attribute is discarded and replaced by clients.
     */
    @Deprecated
    private String[] scopes = new String[]{"api"};
    /**
     * 资源编号
     * 2.1.1. After the RELEASE version, the attribute is discarded and replaced by clients.
     */
    @Deprecated
    private String resourceId = "api";

    /**
     * 配置JWT格式化Oauth2返回的token
     */
    private Jwt jwt = new Jwt();
    /**
     * configure multiple clients
     */
    private List<Client> clients = new ArrayList() {{
        add(new Client());
    }};

    /**
     * Oauth2 Client
     * Used to configure multiple clients
     */
    @Data
    public static class Client {
        /**
         * oauth2 client id
         */
        private String clientId = "ApiBoot";
        /**
         * oauth2 client secret
         */
        private String clientSecret = "ApiBootSecret";
        /**
         * oauth2 client grant types
         * default value is "password,refresh_token"
         */
        private String[] grantTypes = new String[]{"password", "refresh_token"};
        /**
         * oauth2 client scope
         * default value is "api"
         */
        private String[] scopes = new String[]{"api"};
        /**
         * oauth2 application resource id
         * default value is "api"
         */
        private String[] resourceId = new String[]{"api"};
        /**
         * oauth2 access token validity seconds
         * default value is 2 hours (7200 second)
         */
        private int accessTokenValiditySeconds = 60 * 60 * 2;
        /**
         * oauth2 refresh token validity seconds
         * <p>
         * The default value is 30 days（2592000 seconds）
         */
        private int refreshTokenValiditySeconds = 60 * 60 * 24 * 30;
    }

    /**
     * 自定义Jwt相关的配置
     */
    @Data
    public static class Jwt {
        /**
         * 开启Jwt转换AccessToken
         */
        private boolean enable = false;
        /**
         * Jwt转换时使用的秘钥key
         */
        private String signKey = "ApiBoot";
    }
}
