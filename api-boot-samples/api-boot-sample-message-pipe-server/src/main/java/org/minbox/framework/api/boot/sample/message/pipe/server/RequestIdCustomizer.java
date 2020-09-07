package org.minbox.framework.api.boot.sample.message.pipe.server;

import org.minbox.framework.api.boot.autoconfigure.message.pipe.server.MessagePipeConfigurationCustomizer;
import org.minbox.framework.message.pipe.core.transport.RequestIdGenerator;
import org.minbox.framework.message.pipe.server.config.MessagePipeConfiguration;
import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * 自定义配置{@link org.minbox.framework.message.pipe.core.transport.RequestIdGenerator}
 *
 * @author 恒宇少年
 */
@Component
public class RequestIdCustomizer implements MessagePipeConfigurationCustomizer {
    @Override
    public void customize(MessagePipeConfiguration configuration) {
        RequestIdGenerator generator = new UuidRequestIdGenerator();
        configuration.setRequestIdGenerator(generator);
    }

    /**
     * 使用Uuid随机数生成请求ID
     */
    class UuidRequestIdGenerator implements RequestIdGenerator {
        @Override
        public String generate() {
            return UUID.randomUUID().toString();
        }
    }
}
