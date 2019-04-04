package org.minbox.framework.api.boot.plugin.datasource.config;

import lombok.Data;

import javax.sql.DataSource;

/**
 * data source basic config parameter
 * for example：
 * username、url、password
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-01 11:41
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
public class DataSourceConfig {
    /**
     * data source pool name
     */
    private String poolName = "master";
    /**
     * data source type
     *
     * @see org.minbox.framework.api.boot.plugin.datasource.DataSourceTypeNames
     */
    private Class<? extends DataSource> dataSourceType;
    /**
     * driver class name
     * if don't config will use default values
     * MySQL8.0+ -> com.mysql.cj.jdbc.Driver（SpringBoot2.x recommended use）
     * MySQL8.0- -> com.mysql.jdbc.Driver
     */
    private String driverClassName = "com.mysql.cj.jdbc.Driver";
    /**
     * database connection url
     */
    private String url;
    /**
     * database connection username
     */
    private String username;
    /**
     * database connection password
     */
    private String password;
}
