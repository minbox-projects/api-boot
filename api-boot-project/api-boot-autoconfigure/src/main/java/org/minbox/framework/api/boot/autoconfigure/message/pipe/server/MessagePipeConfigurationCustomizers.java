package org.minbox.framework.api.boot.autoconfigure.message.pipe.server;

import org.minbox.framework.message.pipe.server.config.MessagePipeConfiguration;
import org.springframework.boot.util.LambdaSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The wrapper class of {@link MessagePipeConfigurationCustomizer}
 * <p>
 * Execute {@link MessagePipeConfigurationCustomizer#customize} according to the order configured by {@link org.springframework.core.annotation.Order}
 *
 * @author 恒宇少年
 */
public class MessagePipeConfigurationCustomizers {
    private List<MessagePipeConfigurationCustomizer> customizers;

    public MessagePipeConfigurationCustomizers(List<MessagePipeConfigurationCustomizer> customizers) {
        this.customizers = (customizers != null) ? new ArrayList<>(customizers) : Collections.emptyList();
    }

    public MessagePipeConfiguration customizer(MessagePipeConfiguration configuration) {
        LambdaSafe.callbacks(MessagePipeConfigurationCustomizer.class, this.customizers, configuration)
            .withLogger(MessagePipeConfigurationCustomizer.class).invoke((customizer) -> customizer.customize(configuration));
        return configuration;
    }
}
