package org.minbox.framework.api.boot.autoconfigure.datasource;

import lombok.Data;
import org.minbox.framework.datasource.config.DataSourceDruidConfig;
import org.minbox.framework.datasource.config.DataSourceHikariConfig;

import java.util.HashMap;
import java.util.Map;

/**
 * 数据源切换多环境属性
 *
 * @author 恒宇少年
 */
@Data
public class ApiBootDataSourceSwitchEnvironmentProperties {
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
