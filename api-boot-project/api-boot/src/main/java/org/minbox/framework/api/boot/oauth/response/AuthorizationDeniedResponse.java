package org.minbox.framework.api.boot.oauth.response;

import com.fasterxml.jackson.core.JsonGenerator;
import org.minbox.framework.api.boot.oauth.exception.ApiBootOAuth2Exception;
import org.springframework.http.HttpStatus;

/**
 * Interface definition to respond to authorization exception
 *
 * @author 恒宇少年
 */
public interface AuthorizationDeniedResponse {
    /**
     * Provide the response HttpStatus, default is 401
     *
     * @return {@link HttpStatus}
     */
    default HttpStatus getHttpStatus() {
        return HttpStatus.UNAUTHORIZED;
    }

    /**
     * Serialize the response content
     *
     * @param e         {@link ApiBootOAuth2Exception}
     * @param generator {@link JsonGenerator}
     */
    default void serializeResponse(ApiBootOAuth2Exception e, JsonGenerator generator) {
        // default nothing to do
    }
}
