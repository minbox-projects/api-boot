package org.minbox.framework.api.boot.autoconfigure.datasource;

import lombok.Data;
import org.minbox.framework.api.boot.plugin.datasource.config.DataSourceDruidConfig;
import org.minbox.framework.api.boot.plugin.datasource.config.DataSourceHikariConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

import static org.minbox.framework.api.boot.autoconfigure.datasource.ApiBootDataSourceSwitchProperties.API_BOOT_DATASOURCE_SWITCH_PREFIX;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-02 09:48
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
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
     * primary datasource pool name
     * default is master
     */
    private String primary = "master";
    /**
     * config druid type datasource list
     */
    public Map<String, DataSourceDruidConfig> druid = new HashMap();
    /**
     * config hikari type datasource list
     */
    public Map<String, DataSourceHikariConfig> hikari = new HashMap();
}
