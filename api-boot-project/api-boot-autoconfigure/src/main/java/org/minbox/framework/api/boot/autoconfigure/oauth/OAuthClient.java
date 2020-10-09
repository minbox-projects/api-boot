package org.minbox.framework.api.boot.autoconfigure.oauth;

import lombok.Data;

/**
 * oauth client configuration class
 *
 * @author 恒宇少年
 */
@Data
public class OAuthClient {
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
