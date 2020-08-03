package org.minbox.framework.api.boot.autoconfigure.logging;

import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizer;
import org.springframework.boot.autoconfigure.cache.CacheManagerCustomizers;
import org.springframework.boot.util.LambdaSafe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The {@link LoggingFactoryBeanCustomizer} collection processing class
 *
 * @author 恒宇少年
 */
public class LoggingFactoryBeanCustomizers {
    private List<LoggingFactoryBeanCustomizer> customizers;

    public LoggingFactoryBeanCustomizers(List<LoggingFactoryBeanCustomizer> customizers) {
        this.customizers = (customizers != null) ? new ArrayList<>(customizers) : Collections.emptyList();
    }

    /**
     * Customize the specified {@link LoggingFactoryBean}. Locates all
     * {@link LoggingFactoryBeanCustomizer} beans able to handle the specified instance and
     * invoke {@link LoggingFactoryBeanCustomizer#customize(LoggingFactoryBean)} on them.
     *
     * @param factoryBean the logging factory bean to customize
     * @return the factory bean
     */
    public LoggingFactoryBean customize(LoggingFactoryBean factoryBean) {
        LambdaSafe.callbacks(LoggingFactoryBeanCustomizer.class, this.customizers, factoryBean)
            .withLogger(LoggingFactoryBeanCustomizer.class).invoke((customizer) -> customizer.customize(factoryBean));
        return factoryBean;
    }
}
