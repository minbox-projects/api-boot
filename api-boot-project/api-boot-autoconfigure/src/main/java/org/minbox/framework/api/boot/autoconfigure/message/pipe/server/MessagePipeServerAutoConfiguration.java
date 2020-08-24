package org.minbox.framework.api.boot.autoconfigure.message.pipe.server;

import org.minbox.framework.message.pipe.server.config.MessagePipeConfiguration;
import org.minbox.framework.message.pipe.server.config.ServerConfiguration;
import org.minbox.framework.message.pipe.spring.annotation.server.EnableMessagePipeServer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * The Message Pipe Server configuration
 *
 * @author 恒宇少年
 */
@ConditionalOnClass(ServerConfiguration.class)
@EnableConfigurationProperties(MessagePipeServerProperties.class)
@EnableMessagePipeServer
public class MessagePipeServerAutoConfiguration {
    private MessagePipeServerProperties messagePipeServerProperties;

    public MessagePipeServerAutoConfiguration(MessagePipeServerProperties messagePipeServerProperties) {
        this.messagePipeServerProperties = messagePipeServerProperties;
    }

    /**
     * Create {@link ServerConfiguration} instance
     *
     * @return The {@link ServerConfiguration} instance
     * @see MessagePipeServerProperties#getConfiguration
     */
    @Bean
    public ServerConfiguration serverConfiguration() {
        return messagePipeServerProperties.getConfiguration();
    }

    /**
     * Create {@link MessagePipeConfiguration}
     * <p>
     * The common configuration instance for each channel
     *
     * @return The {@link MessagePipeConfiguration} instance
     */
    @Bean
    public MessagePipeConfiguration messagePipeConfiguration() {
        MessagePipeConfiguration configuration = MessagePipeConfiguration.defaultConfiguration();
        MessagePipeConfiguration.LockTime lockTime =
            new MessagePipeConfiguration.LockTime()
                .setLeaseTime(messagePipeServerProperties.getLockLeaseTime())
                .setTimeUnit(messagePipeServerProperties.getLockLeaseTimeUnit());
        configuration.setLockTime(lockTime);
        configuration.setDistributionMessagePoolSize(messagePipeServerProperties.getDistributionMessagePoolSize());
        return configuration;
    }
}
