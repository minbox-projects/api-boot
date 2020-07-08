package org.minbox.framework.api.boot.tools.annotation;

import org.minbox.framework.api.boot.tools.ApplicationContextTools;
import org.minbox.framework.api.boot.tools.BeanFactoryTools;
import org.minbox.framework.util.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * Register tool classes through {@link BeanDefinitionRegistry}
 *
 * @author 恒宇少年
 * @since 2.2.8
 */
public class ToolsBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ToolsBeanDefinitionRegistrar.class);

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        this.registerApplicationContextTools(registry);
        this.registerBeanFactoryTools(registry);
    }

    /**
     * Register {@link ApplicationContextTools}
     *
     * @param registry The {@link BeanDefinitionRegistry} instance
     */
    private void registerApplicationContextTools(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, ApplicationContextTools.BEAN_NAME, ApplicationContextTools.class);
        logger.info("Register ApplicationContextTools successfully.");
    }

    /**
     * Register {@link BeanFactoryTools}
     *
     * @param registry The {@link BeanDefinitionRegistry} instance
     */
    private void registerBeanFactoryTools(BeanDefinitionRegistry registry) {
        BeanUtils.registerInfrastructureBeanIfAbsent(registry, BeanFactoryTools.BEAN_NAME, BeanFactoryTools.class);
        logger.info("Register BeanFactoryTools successfully.");
    }
}
