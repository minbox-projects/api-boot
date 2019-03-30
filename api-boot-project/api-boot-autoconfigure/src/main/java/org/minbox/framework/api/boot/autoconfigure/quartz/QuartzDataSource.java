package org.minbox.framework.api.boot.autoconfigure.quartz;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.*;

/**
 * Qualifier annotation for a DataSource to be injected into Quartz auto-configuration.
 * Can be used on a secondary data source, if there is another one marked as
 * {@code @Primary}.
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-30 13:15
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Target({ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.TYPE,
        ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Qualifier
public @interface QuartzDataSource {

}
