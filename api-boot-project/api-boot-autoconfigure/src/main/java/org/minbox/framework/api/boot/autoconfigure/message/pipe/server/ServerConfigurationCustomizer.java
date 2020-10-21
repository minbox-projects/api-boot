package org.minbox.framework.api.boot.autoconfigure.message.pipe.server;

import org.minbox.framework.message.pipe.server.config.ServerConfiguration;

/**
 * @author 恒宇少年
 */
@FunctionalInterface
public interface ServerConfigurationCustomizer {
    void customize(ServerConfiguration serverConfiguration);
}
