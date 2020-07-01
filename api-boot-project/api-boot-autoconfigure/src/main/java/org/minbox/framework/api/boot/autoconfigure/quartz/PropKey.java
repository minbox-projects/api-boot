package org.minbox.framework.api.boot.autoconfigure.quartz;

import java.lang.annotation.*;

/**
 * Properties configuration field conversion Properties required by Quartz
 *
 * @author 恒宇少年
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface PropKey {
    String value();
}
