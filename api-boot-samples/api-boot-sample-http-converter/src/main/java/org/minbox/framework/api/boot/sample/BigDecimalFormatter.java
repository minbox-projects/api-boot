package org.minbox.framework.api.boot.sample;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 自定义ValueFilter所需注解
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-19 14:13
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BigDecimalFormatter {
    /**
     * 小数位数，默认保留两位
     *
     * @return
     */
    int scale();
}
