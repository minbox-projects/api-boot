package org.minbox.framework.api.boot.autoconfigure.message.pipe.server;

import lombok.Data;
import org.minbox.framework.message.pipe.server.config.ServerConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;

import java.util.concurrent.TimeUnit;

import static org.minbox.framework.api.boot.autoconfigure.message.pipe.server.MessagePipeServerProperties.MESSAGE_PIPE_PREFIX;

/**
 * The Message Pipe server config properties
 *
 * @author 恒宇少年
 */
@ConfigurationProperties(prefix = MESSAGE_PIPE_PREFIX)
@Data
public class MessagePipeServerProperties {
    /**
     * The config prefix for "message-pipe-server"
     */
    public static final String MESSAGE_PIPE_PREFIX = "api.boot.message.pipe.server";
    /**
     * The {@link ServerConfiguration} server configuration
     */
    @NestedConfigurationProperty
    private ServerConfiguration configuration = new ServerConfiguration();
    /**
     * The redisson lock lease time
     */
    private long lockLeaseTime = 10;
    /**
     * The redisson lock lean {@link TimeUnit}
     */
    private TimeUnit lockLeaseTimeUnit = TimeUnit.SECONDS;
    /**
     * The number of threads in the message thread pool
     */
    private int distributionMessagePoolSize = 10;
}
