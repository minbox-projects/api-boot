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
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.oauth.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;

/**
 * Configure Oauth2 properties class
 *
 * @author 恒宇少年
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_OAUTH_PREFIX)
public class ApiBootOauthProperties {
    /**
     * config prefix
     */
    public static final String API_BOOT_OAUTH_PREFIX = "api.boot.oauth";
    /**
     * Configure oauth authentication information storage mode
     * <p>
     * The default use {@link OAuthAway#memory}
     *
     * @see OAuthAway
     */
    private OAuthAway away = OAuthAway.memory;
    /**
     * Whether to generate a new token every time the "/oauth/token" interface is called
     * <p>
     * The previous one is used by default
     */
    private boolean alwaysCreateToken = false;
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
     * Configure simple client grant types
     * 2.1.1. After the RELEASE version, the attribute is discarded and replaced by clients.
     */
    @Deprecated
    private String[] grantTypes = new String[]{"password", "refresh_token"};
    /**
     * Configure simple client scopes
     * 2.1.1. After the RELEASE version, the attribute is discarded and replaced by clients.
     */
    @Deprecated
    private String[] scopes = new String[]{"api"};
    /**
     * Configure simple client resource id
     * 2.1.1. After the RELEASE version, the attribute is discarded and replaced by clients.
     */
    @Deprecated
    private String resourceId = "api";

    /**
     * Configure to use jwt to format oauth token
     */
    private Jwt jwt = new Jwt();
    /**
     * configure multiple clients
     * <p>
     * Add a client information by default and use the default configuration
     */
    private List<OAuthClient> clients = new ArrayList() {
        {
            add(new OAuthClient());
        }
    };
}
