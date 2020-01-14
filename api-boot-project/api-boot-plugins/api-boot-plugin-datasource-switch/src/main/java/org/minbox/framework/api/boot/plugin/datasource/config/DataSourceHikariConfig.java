package org.minbox.framework.api.boot.plugin.datasource.config;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Hikari data source configuration class
 *
 * @author 恒宇少年
 */
@Data
public class DataSourceHikariConfig extends DataSourceConfig {
    /**
     * Hikari dataSource Property Map
     * like：cachePrepStmts、prepStmtCacheSize、prepStmtCacheSqlLimit..
     */
    private Map<String, String> property = new HashMap<>();
}
