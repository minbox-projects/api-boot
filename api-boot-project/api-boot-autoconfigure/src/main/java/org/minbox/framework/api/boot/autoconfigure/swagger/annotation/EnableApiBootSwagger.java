package org.minbox.framework.api.boot.autoconfigure.swagger.annotation;

import org.minbox.framework.api.boot.autoconfigure.swagger.ApiBootSwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 使用该注解开启ApiBoot整合Swagger2文档
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-17 08:58
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(ApiBootSwaggerAutoConfiguration.class)
public @interface EnableApiBootSwagger {

}
