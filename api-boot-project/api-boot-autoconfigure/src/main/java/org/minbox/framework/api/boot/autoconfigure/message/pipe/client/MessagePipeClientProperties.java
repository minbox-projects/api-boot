package org.minbox.framework.api.boot.autoconfigure.message.pipe.client;

import lombok.Data;
import org.minbox.framework.message.pipe.client.config.ClientConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import static org.minbox.framework.api.boot.autoconfigure.message.pipe.client.MessagePipeClientProperties.MESSAGE_PIPE_PREFIX;

/**
 * The Message Pipe client config properties
 *
 * @author 恒宇少年
 */
@ConfigurationProperties(prefix = MESSAGE_PIPE_PREFIX)
@Data
public class MessagePipeClientProperties {
    /**
     * The config prefix for "message-pipe-client"
     */
    public static final String MESSAGE_PIPE_PREFIX = "api.boot.message.pipe.client";
    /**
     * The {@link ClientConfiguration} client configuration
     */
    @NestedConfigurationProperty
    private ClientConfiguration configuration = new ClientConfiguration();
}
