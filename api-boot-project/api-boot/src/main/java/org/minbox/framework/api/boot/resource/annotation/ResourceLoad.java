package org.minbox.framework.api.boot.resource.annotation;

import org.minbox.framework.api.boot.resource.enums.ResourceStoreEvent;

import java.lang.annotation.*;

/**
 * 资源加载注解，该注解只能配置在方法上
 *
 * @author 恒宇少年
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ResourceLoad {
    /**
     * 资源存储事件
     * 默认查询资源
     *
     * @return ResourceStoreEvent
     */
    ResourceStoreEvent event() default ResourceStoreEvent.SELECT;
}
