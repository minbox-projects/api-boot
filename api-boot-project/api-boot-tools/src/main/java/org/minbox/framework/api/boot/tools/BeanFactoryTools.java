package org.minbox.framework.api.boot.tools;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

/**
 * The {@link BeanFactory} tools
 *
 * @author 恒宇少年
 * @since 2.2.8
 */
public class BeanFactoryTools implements BeanFactoryAware {
    /**
     * The bean name of {@link BeanFactoryTools}
     */
    public static final String BEAN_NAME = "ApiBootBeanFactory";
    private static BeanFactory beanFactory;

    public static BeanFactory getBeanFactory() {
        return beanFactory;
    }

    public static Object getBean(String name) throws BeansException {
        return beanFactory.getBean(name);
    }

    public static <T> T getBean(String name, Class<T> requiredType) throws BeansException {
        return beanFactory.getBean(name, requiredType);
    }

    public static <T> T getBean(Class<T> requiredType) throws BeansException {
        return beanFactory.getBean(requiredType);
    }

    public static boolean containsBean(String name) {
        return beanFactory.containsBean(name);
    }

    public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isSingleton(name);
    }

    public static boolean isPrototype(String name) throws NoSuchBeanDefinitionException {
        return beanFactory.isPrototype(name);
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        BeanFactoryTools.beanFactory = beanFactory;
    }
}
