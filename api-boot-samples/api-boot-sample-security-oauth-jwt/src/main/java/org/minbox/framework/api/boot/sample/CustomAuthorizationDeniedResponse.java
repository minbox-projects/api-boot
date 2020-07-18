package org.minbox.framework.api.boot.sample;

import com.fasterxml.jackson.core.JsonGenerator;
import org.minbox.framework.api.boot.oauth.exception.ApiBootOAuth2Exception;
import org.minbox.framework.api.boot.oauth.response.AuthorizationDeniedResponse;
import org.springframework.stereotype.Component;

/**
 * 自定义认证错误格式化响应 {@link AuthorizationDeniedResponse}使用示例
 *
 * @author 恒宇少年
 */
@Component
public class CustomAuthorizationDeniedResponse implements AuthorizationDeniedResponse {
    @Override
    public void serializeResponse(ApiBootOAuth2Exception e, JsonGenerator generator) {
        try {
            generator.writeObjectField("code", e.getHttpErrorCode());
            generator.writeObjectField("message", e.getMessage());
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
