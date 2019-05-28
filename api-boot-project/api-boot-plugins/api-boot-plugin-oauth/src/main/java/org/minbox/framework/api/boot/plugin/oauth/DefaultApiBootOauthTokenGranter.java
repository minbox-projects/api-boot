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

package org.minbox.framework.api.boot.plugin.oauth;

import org.minbox.framework.api.boot.plugin.oauth.grant.ApiBootOauthTokenGranter;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.ClientDetailsService;
import org.springframework.security.oauth2.provider.OAuth2RequestFactory;
import org.springframework.security.oauth2.provider.TokenRequest;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * default api boot oauth token granter
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-05-27 18:00
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class DefaultApiBootOauthTokenGranter extends AbstractTokenGranter {
    /**
     * Instance of custom authorization provided by ApiBoot
     */
    private ApiBootOauthTokenGranter apiBootOauthTokenGranter;

    /**
     * instance default ApiBoot Oauth Token Granter
     *
     * @param tokenServices            token service
     * @param clientDetailsService     client detail service
     * @param requestFactory           oauth2 request factory
     * @param apiBootOauthTokenGranter Instance of custom authorization provided by ApiBoot
     */
    public DefaultApiBootOauthTokenGranter(AuthorizationServerTokenServices tokenServices, ClientDetailsService clientDetailsService, OAuth2RequestFactory requestFactory, ApiBootOauthTokenGranter apiBootOauthTokenGranter) {
        super(tokenServices, clientDetailsService, requestFactory, apiBootOauthTokenGranter.grantType());
        this.apiBootOauthTokenGranter = apiBootOauthTokenGranter;
    }

    /**
     * grant access token
     *
     * @param grantType    grant type
     * @param tokenRequest create token parameter
     * @return
     */
    @Override
    public OAuth2AccessToken grant(String grantType, TokenRequest tokenRequest) {
        // create token request parameters
        Map<String, String> parameters = new LinkedHashMap(tokenRequest.getRequestParameters());

        // valid
        apiBootOauthTokenGranter.valid(parameters);

        // create token
        OAuth2AccessToken token = super.grant(grantType, tokenRequest);
        if (token != null) {
            token = new DefaultOAuth2AccessToken(token);
        }
        return token;
    }
}
