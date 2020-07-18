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

package org.minbox.framework.api.boot.oauth.grant;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.provider.*;
import org.springframework.security.oauth2.provider.token.AbstractTokenGranter;
import org.springframework.security.oauth2.provider.token.AuthorizationServerTokenServices;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * default api boot oauth token granter
 *
 * @author 恒宇少年
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
     * get oauth2 authentication
     *
     * @param client       client detail
     * @param tokenRequest token request
     * @return oauth2 authentication
     */
    @Override
    protected OAuth2Authentication getOAuth2Authentication(ClientDetails client, TokenRequest tokenRequest) {
        Map<String, String> parameters = new LinkedHashMap<String, String>(tokenRequest.getRequestParameters());

        UserDetails userDetails = apiBootOauthTokenGranter.loadByParameter(parameters);

        Authentication userAuth = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
        ((AbstractAuthenticationToken) userAuth).setDetails(parameters);

        OAuth2Request storedOAuth2Request = getRequestFactory().createOAuth2Request(client, tokenRequest);
        return new OAuth2Authentication(storedOAuth2Request, userAuth);
    }
}
