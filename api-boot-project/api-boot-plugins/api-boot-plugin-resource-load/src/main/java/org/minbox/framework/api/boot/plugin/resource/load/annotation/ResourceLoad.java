package org.minbox.framework.api.boot.plugin.resource.load.annotation;

import org.minbox.framework.api.boot.plugin.resource.load.enums.ResourceStoreEvent;

import java.lang.annotation.*;

/**
 * 资源加载注解，该注解只能配置在方法上
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-11 21:52
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
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
