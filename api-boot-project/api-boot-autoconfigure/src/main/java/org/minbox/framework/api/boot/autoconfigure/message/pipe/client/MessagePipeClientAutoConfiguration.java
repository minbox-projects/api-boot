package org.minbox.framework.api.boot.autoconfigure.message.pipe.client;

import org.minbox.framework.message.pipe.client.config.ClientConfiguration;
import org.minbox.framework.message.pipe.spring.annotation.client.EnableMessagePipeClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * The Message Pipe configuration
 *
 * @author 恒宇少年
 */
@ConditionalOnClass(ClientConfiguration.class)
@EnableConfigurationProperties(MessagePipeClientProperties.class)
@EnableMessagePipeClient
public class MessagePipeClientAutoConfiguration {
    private MessagePipeClientProperties messagePipeClientProperties;

    public MessagePipeClientAutoConfiguration(MessagePipeClientProperties messagePipeClientProperties) {
        this.messagePipeClientProperties = messagePipeClientProperties;
    }

    /**
     * Create {@link ClientConfiguration} instance
     *
     * @return The {@link ClientConfiguration} instance
     * @see MessagePipeClientProperties
     */
    @Bean
    public ClientConfiguration clientConfiguration() {
        return messagePipeClientProperties.getConfiguration();
    }
}
