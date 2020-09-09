package org.minbox.framework.api.boot.autoconfigure.message.pipe.server;

import org.minbox.framework.message.pipe.server.config.MessagePipeConfiguration;
import org.minbox.framework.message.pipe.server.config.ServerConfiguration;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.List;
import java.util.stream.Collectors;

/**
 * The Message Pipe Server configuration
 *
 * @author 恒宇少年
 */
@ConditionalOnClass({ServerConfiguration.class, RedisMessageListenerContainer.class})
@EnableConfigurationProperties(MessagePipeServerProperties.class)
public class MessagePipeServerAutoConfiguration {
    private MessagePipeServerProperties messagePipeServerProperties;

    public MessagePipeServerAutoConfiguration(MessagePipeServerProperties messagePipeServerProperties) {
        this.messagePipeServerProperties = messagePipeServerProperties;
    }

    /**
     * Create {@link ServerConfiguration} instance
     *
     * @return The {@link ServerConfiguration} instance
     * @see MessagePipeServerProperties
     */
    @Bean
    public ServerConfiguration serverConfiguration() {
        return messagePipeServerProperties.getConfiguration();
    }

    /**
     * Instantiate {@link RedisMessageListenerContainer}
     * <p>
     * This instance is required by the message pipeline, but here is just the default configuration
     *
     * @param redisConnectionFactory The {@link RedisConnectionFactory} redis connectory factory instance
     * @return The {@link RedisMessageListenerContainer} instance
     */
    @Bean
    @ConditionalOnMissingBean
    public RedisMessageListenerContainer redisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        return container;
    }

    /**
     * Instantiate the wrapper class of {@link MessagePipeConfigurationCustomizer}
     *
     * @param customizers The {@link MessagePipeConfigurationCustomizer} object provider
     * @return The {@link MessagePipeConfigurationCustomizers} instance
     */
    @Bean
    @ConditionalOnMissingBean
    public MessagePipeConfigurationCustomizers messagePipeConfigurationCustomizers(
        ObjectProvider<MessagePipeConfigurationCustomizer> customizers) {
        List<MessagePipeConfigurationCustomizer> sortedCustomizers =
            customizers.orderedStream().collect(Collectors.toList());
        return new MessagePipeConfigurationCustomizers(sortedCustomizers);
    }

    /**
     * Create {@link MessagePipeConfiguration}
     * <p>
     * The common configuration instance for each channel
     *
     * @return The {@link MessagePipeConfiguration} instance
     */
    @Bean
    public MessagePipeConfiguration messagePipeConfiguration(MessagePipeConfigurationCustomizers customizers) {
        MessagePipeConfiguration configuration = MessagePipeConfiguration.defaultConfiguration();
        return customizers.customizer(configuration);
    }

    /**
     * Configuration {@link MessagePipeConfiguration#setLockTime}
     *
     * @return The {@link MessagePipeConfigurationCustomizer} instance of {@link MessagePipeConfiguration.LockTime}
     */
    @Bean
    public MessagePipeConfigurationCustomizer customizerLockTime() {
        return configuration -> {
            MessagePipeConfiguration.LockTime lockTime =
                new MessagePipeConfiguration.LockTime()
                    .setLeaseTime(messagePipeServerProperties.getLockLeaseTime())
                    .setTimeUnit(messagePipeServerProperties.getLockLeaseTimeUnit());
            configuration.setLockTime(lockTime);
        };
    }

    /**
     * Configuration {@link MessagePipeConfiguration#setMessagePipeMonitorMillis}
     *
     * @return The {@link MessagePipeConfigurationCustomizer} instance of monitor millis
     */
    @Bean
    public MessagePipeConfigurationCustomizer customizerMonitorTime() {
        return configuration ->
            configuration.setMessagePipeMonitorMillis(messagePipeServerProperties.getMessagePipeMonitorMillis());

    }
}
