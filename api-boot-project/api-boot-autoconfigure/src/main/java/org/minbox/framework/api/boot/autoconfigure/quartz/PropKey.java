package org.minbox.framework.api.boot.autoconfigure.quartz;

import java.lang.annotation.*;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-30 14:46
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropKey {
    /**
     * 对应
     * org.minbox.framework.api.boot.autoconfigure.quartz.ApiBootQuartzProperties#properties
     * 配置key值
     *
     * @return
     */
    String value();
}
