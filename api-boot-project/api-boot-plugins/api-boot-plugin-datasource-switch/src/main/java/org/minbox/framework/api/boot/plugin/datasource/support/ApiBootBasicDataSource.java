package org.minbox.framework.api.boot.plugin.datasource.support;

import com.zaxxer.hikari.HikariDataSource;
import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.datasource.ApiBootDataSource;
import org.minbox.framework.api.boot.plugin.datasource.config.DataSourceConfig;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.jdbc.datasource.DelegatingDataSource;

import javax.sql.DataSource;

/**
 * The Basic {@link DataSource} config
 *
 * @author 恒宇少年
 */
public class ApiBootBasicDataSource extends DelegatingDataSource implements ApiBootDataSource {
    private DataSourceConfig config;

    public ApiBootBasicDataSource(DataSourceConfig config) {
        this.config = config;
        this.setTargetDataSource(build());
    }

    /**
     * create default basic data source
     *
     * @return {@link DataSource} instance
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public DataSource build() throws ApiBootException {
        try {
            DataSource dataSource = DataSourceBuilder.create().url(config.getUrl()).username(config.getUsername()).password(config.getPassword()).driverClassName(config.getDriverClassName())
                // springboot 2.x default is HikariDataSource
                .type(HikariDataSource.class)
                .build();
            return dataSource;
        } catch (Exception e) {
            throw new ApiBootException("Create a default data source exception", e);
        }

    }
}
