package org.minbox.framework.api.boot.plugin.datasource.annotation;

import java.lang.annotation.*;

/**
 * data source switch annotation
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-01 16:23
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface DataSourceSwitch {
    /**
     * data source pool name
     *
     * @return pool name
     */
    String value();
}
