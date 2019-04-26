package org.minbox.framework.api.boot.plugin.resource.load.annotation;

import java.lang.annotation.*;

/**
 * 资源业务字段
 * 配置方法返回值资源字段的基本信息
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
public @interface ResourceField {
    /**
     * 资源加载后目标Field
     *
     * @return name
     */
    String name();

    /**
     * 资源编号Field
     *
     * @return source
     */
    String source();

    /**
     * 资源类型
     * 如：头像、封面图等
     * 由使用者定义
     *
     * @return type
     */
    String type();

    /**
     * 是否为数组
     *
     * @return true：接收资源的字段为数组类型，false：非数组类型
     */
    boolean isArray() default false;

    /**
     * 是否为list
     *
     * @return true：接收资源的字段为list类型，false：非list类型
     */
    boolean isList() default false;
}
