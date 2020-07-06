package org.minbox.framework.api.boot.autoconfigure.swagger.annotation;

import org.minbox.framework.api.boot.autoconfigure.swagger.ApiBootSwaggerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Use this annotation to open the ApiBoot integration Swagger2 document
 *
 * @author 恒宇少年
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
@Documented
@Import(ApiBootSwaggerAutoConfiguration.class)
public @interface EnableApiBootSwagger {

}
