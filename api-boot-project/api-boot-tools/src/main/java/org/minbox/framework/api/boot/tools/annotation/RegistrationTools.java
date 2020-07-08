package org.minbox.framework.api.boot.tools.annotation;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Registration all tools
 *
 * @author 恒宇少年
 * @since 2.2.8
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(ToolsBeanDefinitionRegistrar.class)
public @interface RegistrationTools {
    //...
}
