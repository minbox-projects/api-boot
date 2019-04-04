package org.minbox.framework.api.boot.plugin.datasource;

import org.minbox.framework.api.boot.plugin.datasource.config.DataSourceConfig;
import org.minbox.framework.api.boot.plugin.datasource.config.DataSourceDruidConfig;
import org.minbox.framework.api.boot.plugin.datasource.config.DataSourceHikariConfig;
import org.minbox.framework.api.boot.plugin.datasource.support.ApiBootBasicDataSource;
import org.minbox.framework.api.boot.plugin.datasource.support.ApiBootDruidDataSource;
import org.minbox.framework.api.boot.plugin.datasource.support.ApiBootHikariDataSource;

import javax.sql.DataSource;

/**
 * ApiBoot DataSource Switch
 * data source factory
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-01 11:32
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootDataSourceFactoryBean {
    /**
     * create new dataSource instance
     *
     * @param config dataSource config
     * @return dataSource Instance
     */
    public DataSource newDataSource(DataSourceConfig config) {
        DataSource dataSource = null;
        // not setting data source type class name
        if (config.getDataSourceType() == null) {
            // use druid data source
            if (checkUseAppointDataSource(DataSourceTypeNames.DRUID)) {
                dataSource = new ApiBootDruidDataSource((DataSourceDruidConfig) config);
            }
            // use Hikari data source
            else if (checkUseAppointDataSource(DataSourceTypeNames.HIKARI)) {
                dataSource = new ApiBootHikariDataSource((DataSourceHikariConfig) config);
            }
        }
        // setting data source type class name
        else {
            // druid data source
            if (DataSourceTypeNames.DRUID.equals(config.getDataSourceType().getName())) {
                dataSource = new ApiBootDruidDataSource((DataSourceDruidConfig) config);
            }
            // Hikari data source
            else if (DataSourceTypeNames.HIKARI.equals(config.getDataSourceType().getName())) {
                dataSource = new ApiBootHikariDataSource((DataSourceHikariConfig) config);
            }
        }
        // use default basic data source
        if (dataSource == null) {
            dataSource = new ApiBootBasicDataSource(config);
        }
        return dataSource;
    }

    /**
     * check project is use appoint data source
     *
     * @return true/false
     */
    private boolean checkUseAppointDataSource(String dataSourceTypeName) {
        boolean isUseCheck = true;
        try {
            Class.forName(dataSourceTypeName);
        } catch (ClassNotFoundException e) {
            isUseCheck = false;
        }
        return isUseCheck;
    }
}
