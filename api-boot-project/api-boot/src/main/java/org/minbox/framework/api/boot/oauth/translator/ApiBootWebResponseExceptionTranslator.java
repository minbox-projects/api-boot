package org.minbox.framework.api.boot.oauth.translator;

import org.minbox.framework.api.boot.oauth.response.AuthorizationDeniedResponse;
import org.minbox.framework.api.boot.oauth.exception.ApiBootOAuth2Exception;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.common.DefaultThrowableAnalyzer;
import org.springframework.security.oauth2.common.exceptions.InsufficientScopeException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.security.web.util.ThrowableAnalyzer;
import org.springframework.web.HttpRequestMethodNotSupportedException;

import java.io.IOException;

/**
 * Override the default WebResponseExceptionTranslator
 * Customize the error message format returned by the authentication server
 *
 * @author 恒宇少年
 * @see org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator
 */
public class ApiBootWebResponseExceptionTranslator implements WebResponseExceptionTranslator {
    /**
     * Response in case of authentication error
     */
    private AuthorizationDeniedResponse authorizationDeniedResponse;

    public ApiBootWebResponseExceptionTranslator(AuthorizationDeniedResponse authorizationDeniedResponse) {
        this.authorizationDeniedResponse = authorizationDeniedResponse;
    }

    private ThrowableAnalyzer throwableAnalyzer = new DefaultThrowableAnalyzer();

    @Override
    public ResponseEntity<OAuth2Exception> translate(Exception e) throws Exception {
        Throwable[] causeChain = this.throwableAnalyzer.determineCauseChain(e);

        Exception ase = (OAuth2Exception) this.throwableAnalyzer.getFirstThrowableOfType(OAuth2Exception.class, causeChain);
        if (ase != null) {
            return this.handleOAuth2Exception((OAuth2Exception) ase);
        }

        ase = (AuthenticationException) this.throwableAnalyzer.getFirstThrowableOfType(AuthenticationException.class, causeChain);
        if (ase != null) {
            return this.handleOAuth2Exception(new UnauthorizedException(e.getMessage(), e));
        }

        ase = (AccessDeniedException) this.throwableAnalyzer.getFirstThrowableOfType(AccessDeniedException.class, causeChain);
        if (ase instanceof AccessDeniedException) {
            return this.handleOAuth2Exception(new ForbiddenException(ase.getMessage(), ase));
        }

        ase = (HttpRequestMethodNotSupportedException) throwableAnalyzer.getFirstThrowableOfType(
            HttpRequestMethodNotSupportedException.class, causeChain);
        if (ase instanceof HttpRequestMethodNotSupportedException) {
            return handleOAuth2Exception(new MethodNotAllowed(ase.getMessage(), ase));
        }

        return this.handleOAuth2Exception(new ServerErrorException(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase(), e));
    }

    /**
     * Handling Formatted OAuth2Exception Response
     *
     * @param e {@link OAuth2Exception}
     * @return {@link ResponseEntity}
     * @throws IOException
     */
    private ResponseEntity<OAuth2Exception> handleOAuth2Exception(OAuth2Exception e) throws IOException {
        int status = e.getHttpErrorCode();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Cache-Control", "no-store");
        headers.set("Pragma", "no-cache");
        if (status == HttpStatus.UNAUTHORIZED.value() || e instanceof InsufficientScopeException) {
            headers.set("WWW-Authenticate", String.format("%s %s", "Bearer", e.getSummary()));
        }

        // use ApiBootOAuth2Exception as the returned exception type
        ApiBootOAuth2Exception apiBootOAuth2Exception = new ApiBootOAuth2Exception(e.getMessage(), e, authorizationDeniedResponse);
        // get custom authorization definition response HttpStatus
        HttpStatus httpStatus = authorizationDeniedResponse.getHttpStatus();
        ResponseEntity<OAuth2Exception> response = new ResponseEntity(apiBootOAuth2Exception, headers, httpStatus);
        return response;
    }

    private static class MethodNotAllowed extends OAuth2Exception {
        public MethodNotAllowed(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return HttpStatus.METHOD_NOT_ALLOWED.getReasonPhrase();
        }

        @Override
        public int getHttpErrorCode() {
            return HttpStatus.METHOD_NOT_ALLOWED.value();
        }
    }

    private static class UnauthorizedException extends OAuth2Exception {
        public UnauthorizedException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return HttpStatus.UNAUTHORIZED.getReasonPhrase();
        }

        @Override
        public int getHttpErrorCode() {
            return HttpStatus.UNAUTHORIZED.value();
        }
    }

    private static class ServerErrorException extends OAuth2Exception {
        public ServerErrorException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase();
        }

        @Override
        public int getHttpErrorCode() {
            return HttpStatus.INTERNAL_SERVER_ERROR.value();
        }
    }

    private static class ForbiddenException extends OAuth2Exception {
        public ForbiddenException(String msg, Throwable t) {
            super(msg, t);
        }

        @Override
        public String getOAuth2ErrorCode() {
            return HttpStatus.FORBIDDEN.getReasonPhrase();
        }

        @Override
        public int getHttpErrorCode() {
            return HttpStatus.FORBIDDEN.value();
        }
    }
}
