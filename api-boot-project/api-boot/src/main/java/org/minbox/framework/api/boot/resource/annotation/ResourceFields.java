package org.minbox.framework.api.boot.resource.annotation;

import java.lang.annotation.*;

/**
 * 资源业务字段集合
 *
 * @author 恒宇少年
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResourceFields {
    /**
     * 资源业务字段的集合
     *
     * @return
     */
    ResourceField[] value();
}
