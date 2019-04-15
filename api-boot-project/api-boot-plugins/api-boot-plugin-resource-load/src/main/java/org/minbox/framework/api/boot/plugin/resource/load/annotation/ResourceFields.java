package org.minbox.framework.api.boot.plugin.resource.load.annotation;

import java.lang.annotation.*;

/**
 * 资源业务字段集合
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-11 22:12
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
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
