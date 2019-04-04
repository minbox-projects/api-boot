package org.minbox.framework.api.boot.plugin.datasource;

/**
 * ApiBoot DataSource Switch 所支持的数据源类型限定名
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-01 11:33
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface DataSourceTypeNames {
    /**
     * Druid 数据源类全限定路径
     */
    String DRUID = "com.alibaba.druid.pool.DruidDataSource";
    /**
     * Hikari 数据源全限定路径
     */
    String HIKARI = "com.zaxxer.hikari.HikariDataSource";
}
