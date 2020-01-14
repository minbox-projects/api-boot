package org.minbox.framework.api.boot.plugin.datasource.support;

import com.zaxxer.hikari.HikariDataSource;
import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.datasource.ApiBootDataSource;
import org.minbox.framework.api.boot.plugin.datasource.config.DataSourceHikariConfig;

import javax.sql.DataSource;

/**
 * The Hikari {@link DataSource} config
 *
 * @author 恒宇少年
 */
public class ApiBootHikariDataSource extends HikariDataSource implements ApiBootDataSource {

    public ApiBootHikariDataSource(DataSourceHikariConfig config) {
        try {
            this.setJdbcUrl(config.getUrl());
            this.setUsername(config.getUsername());
            this.setPassword(config.getPassword());
            config.getProperty().keySet().stream().forEach(param -> this.addDataSourceProperty(param, config.getProperty().get(param)));
        } catch (Exception e) {
            throw new ApiBootException("Create new Hikari dataSource fail.", e);
        }
    }

    /**
     * create new Hikari dataSource instance
     *
     * @return {@link DataSource} this class instance
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public DataSource build() throws ApiBootException {
        return this;
    }
}
