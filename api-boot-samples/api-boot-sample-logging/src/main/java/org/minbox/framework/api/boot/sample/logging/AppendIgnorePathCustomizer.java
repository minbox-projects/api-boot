package org.minbox.framework.api.boot.sample.logging;

import org.minbox.framework.api.boot.autoconfigure.logging.LoggingFactoryBeanCustomizer;
import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * {@link LoggingFactoryBeanCustomizer}实现类
 * 新增排除日志拦截输出的路径
 *
 * @author 恒宇少年
 */
@Component
@Order(2)
public class AppendIgnorePathCustomizer implements LoggingFactoryBeanCustomizer {
    @Override
    public void customize(LoggingFactoryBean factoryBean) {
        factoryBean.getIgnorePaths().add("/test");
    }
}
