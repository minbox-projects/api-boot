package org.minbox.framework.api.boot.autoconfigure.message.pipe.server;

import org.minbox.framework.message.pipe.server.config.ServerConfiguration;
import org.springframework.boot.util.LambdaSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author 恒宇少年
 */
public class ServerConfigurationCustomizers {
    private List<ServerConfigurationCustomizer> customizers;

    public ServerConfigurationCustomizers(List<ServerConfigurationCustomizer> customizers) {
        this.customizers = (customizers != null) ? new ArrayList<>(customizers) : Collections.emptyList();
    }

    public ServerConfiguration customizer(ServerConfiguration configuration) {
        LambdaSafe.callbacks(ServerConfigurationCustomizer.class, this.customizers, configuration)
            .withLogger(ServerConfigurationCustomizer.class).invoke((customizer) -> customizer.customize(configuration));
        return configuration;
    }
}
