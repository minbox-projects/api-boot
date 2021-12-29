package org.minbox.framework.api.boot.autoconfigure.grace;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.grace.ApiBootGraceLogProperties.API_BOOT_GRACE_PREFIX;

/**
 * Grace框架自动化配置属性类
 *
 * @author 恒宇少年
 */
@Configuration
@ConfigurationProperties(prefix = API_BOOT_GRACE_PREFIX)
@Data
public class ApiBootGraceLogProperties {
    public static final String API_BOOT_GRACE_PREFIX = "api.boot.grace";
    /**
     * 配置表达式函数扫描的基础包列表
     *
     * @see org.minbox.framework.grace.expression.annotation.GraceFunctionDefiner
     * @see org.minbox.framework.grace.expression.annotation.GraceFunction
     */
    private List<String> functionScanBasePackages;
}
