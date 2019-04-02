package org.minbox.framework.api.boot.plugin.datasource.config;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * Hikari data source config parameter
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-01 11:42
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
public class DataSourceHikariConfig extends DataSourceConfig {
    /**
     * Hikari dataSource Property Map
     * like：cachePrepStmts、prepStmtCacheSize、prepStmtCacheSqlLimit..
     */
    private Map<String, String> property = new HashMap<>();
}
