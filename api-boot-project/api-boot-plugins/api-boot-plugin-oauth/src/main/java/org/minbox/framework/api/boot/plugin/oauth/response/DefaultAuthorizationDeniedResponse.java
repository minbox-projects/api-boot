package org.minbox.framework.api.boot.plugin.oauth.response;

import com.fasterxml.jackson.core.JsonGenerator;
import org.minbox.framework.api.boot.plugin.oauth.exception.ApiBootOAuth2Exception;
import org.springframework.http.HttpStatus;
import org.springframework.web.util.HtmlUtils;

/**
 * The default {@link AuthorizationDeniedResponse} support
 * Provide default OAuth2Exception exception response content
 *
 * @author 恒宇少年
 * @see org.minbox.framework.api.boot.plugin.oauth.translator.ApiBootWebResponseExceptionTranslator
 * @see ApiBootOAuth2Exception
 */
public class DefaultAuthorizationDeniedResponse implements AuthorizationDeniedResponse {
    @Override
    public void serializeResponse(ApiBootOAuth2Exception e, JsonGenerator generator) {
        try {
            String message = e.getMessage();
            if (message != null) {
                message = HtmlUtils.htmlEscape(message);
            }
            generator.writeObjectField("errorMessage", message);
            generator.writeObjectField("errorCode", HttpStatus.UNAUTHORIZED.getReasonPhrase());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
