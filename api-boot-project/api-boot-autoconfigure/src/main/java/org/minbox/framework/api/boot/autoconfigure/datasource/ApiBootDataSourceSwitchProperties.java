package org.minbox.framework.api.boot.autoconfigure.datasource;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.minbox.framework.api.boot.autoconfigure.datasource.ApiBootDataSourceSwitchProperties.API_BOOT_DATASOURCE_SWITCH_PREFIX;

/**
 * ApiBoot DataSource Switch Properties
 * <p>
 * The property configuration class required by the data source switching component
 *
 * @author 恒宇少年
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_DATASOURCE_SWITCH_PREFIX)
public class ApiBootDataSourceSwitchProperties {
    /**
     * ApiBoot DataSource Switch Config Prefix
     */
    public static final String API_BOOT_DATASOURCE_SWITCH_PREFIX = "api.boot.datasource";
    /**
     * active environment
     */
    private String activeEnvironment = "default";

    /**
     * Multi-environment data source configuration
     */
    private Map<String, ApiBootDataSourceSwitchEnvironmentProperties> environments = new HashMap<>();
}
