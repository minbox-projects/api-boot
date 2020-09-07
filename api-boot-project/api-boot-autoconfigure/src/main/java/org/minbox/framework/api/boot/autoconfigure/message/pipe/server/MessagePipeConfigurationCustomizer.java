package org.minbox.framework.api.boot.autoconfigure.message.pipe.server;

import org.minbox.framework.message.pipe.server.config.MessagePipeConfiguration;

/**
 * The {@link MessagePipeConfiguration} Custom configuration interface definition
 * <p>
 * If there is a sequence, you can use the {@link org.springframework.core.annotation.Order} annotation to configure
 *
 * @author 恒宇少年
 */
@FunctionalInterface
public interface MessagePipeConfigurationCustomizer {
    /**
     * To implement this method, it can be modified according to the parameter object
     *
     * @param configuration The {@link MessagePipeConfiguration} instance
     */
    void customize(MessagePipeConfiguration configuration);
}
