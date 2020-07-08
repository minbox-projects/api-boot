package org.minbox.framework.api.boot.tools;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationEvent;

/**
 * The {@link ApplicationContext} tools
 *
 * @author 恒宇少年
 * @since 2.2.8
 */
public class ApplicationContextTools implements ApplicationContextAware {
    /**
     * The bean name of {@link ApplicationContextTools}
     *
     * @see org.minbox.framework.api.boot.tools.annotation.ToolsBeanDefinitionRegistrar
     */
    public static final String BEAN_NAME = "ApiBootApplicationContext";
    private static ApplicationContext context;

    public static ApplicationContext getContext() {
        return context;
    }

    /**
     * Publish a {@link ApplicationEvent}
     *
     * @param event The {@link ApplicationEvent} instance
     */
    public static void publishEvent(ApplicationEvent event) {
        context.publishEvent(event);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }
}
