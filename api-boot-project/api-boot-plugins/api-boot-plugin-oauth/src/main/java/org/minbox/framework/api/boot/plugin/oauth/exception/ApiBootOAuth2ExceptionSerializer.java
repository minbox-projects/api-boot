package org.minbox.framework.api.boot.plugin.oauth.exception;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import java.io.IOException;

/**
 * The ApiBootOAuth2Exception Serializer
 * The specific implementation is handed over to "org.minbox.framework.api.boot.plugin.oauth.response.AuthorizationDeniedResponse#serializeResponse"
 *
 * @author 恒宇少年
 */
public class ApiBootOAuth2ExceptionSerializer extends StdSerializer<ApiBootOAuth2Exception> {
    protected ApiBootOAuth2ExceptionSerializer() {
        super(ApiBootOAuth2Exception.class);
    }

    @Override
    public void serialize(ApiBootOAuth2Exception e, JsonGenerator generator, SerializerProvider serializerProvider) throws IOException {
        generator.writeStartObject();
        e.getResponse().serializeResponse(e, generator);
        generator.writeEndObject();
    }
}
