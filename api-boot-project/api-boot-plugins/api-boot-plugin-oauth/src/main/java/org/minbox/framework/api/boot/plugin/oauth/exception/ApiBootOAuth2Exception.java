package org.minbox.framework.api.boot.plugin.oauth.exception;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import lombok.Getter;
import org.minbox.framework.api.boot.plugin.oauth.response.AuthorizationDeniedResponse;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;

/**
 * Custom implementation of OAuth2Exception
 *
 * @author 恒宇少年
 * @see OAuth2Exception
 */
@JsonSerialize(using = ApiBootOAuth2ExceptionSerializer.class)
@Getter
public class ApiBootOAuth2Exception extends OAuth2Exception {

    private AuthorizationDeniedResponse response;

    public ApiBootOAuth2Exception(String message, Throwable t, AuthorizationDeniedResponse response) {
        super(message, t);
        this.response = response;
    }
}
