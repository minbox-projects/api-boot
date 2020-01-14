package org.minbox.framework.api.boot.plugin.datasource;

/**
 * {@link javax.sql.DataSource} Types name definition
 *
 * @author 恒宇少年
 */
public interface DataSourceTypeNames {
    /**
     * The Druid Class Name
     *
     * @see com.alibaba.druid.pool.DruidDataSource
     */
    String DRUID = "com.alibaba.druid.pool.DruidDataSource";
    /**
     * The Hikari Class Name
     *
     * @see com.zaxxer.hikari.HikariDataSource
     */
    String HIKARI = "com.zaxxer.hikari.HikariDataSource";
}
