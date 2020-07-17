package org.minbox.framework.api.boot.datasource;

import org.minbox.framework.api.boot.datasource.config.DataSourceConfig;
import org.minbox.framework.api.boot.datasource.config.DataSourceDruidConfig;
import org.minbox.framework.api.boot.datasource.config.DataSourceHikariConfig;
import org.minbox.framework.api.boot.datasource.support.ApiBootBasicDataSource;
import org.minbox.framework.api.boot.datasource.support.ApiBootDruidDataSource;
import org.minbox.framework.api.boot.datasource.support.ApiBootHikariDataSource;

import javax.sql.DataSource;

/**
 * {@link DataSource} Factory Class
 *
 * @author 恒宇少年
 */
public class ApiBootDataSourceFactoryBean {
    /**
     * create new dataSource instance
     *
     * @param config {@link DataSourceConfig}
     * @return {@link DataSource} the new instance
     */
    public DataSource newDataSource(DataSourceConfig config) {
        DataSource dataSource = null;
        // if not setting data source type class name
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
        // if setting data source type class name
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
