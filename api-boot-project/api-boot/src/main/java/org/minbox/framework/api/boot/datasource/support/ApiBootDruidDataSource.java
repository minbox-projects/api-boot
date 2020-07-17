package org.minbox.framework.api.boot.datasource.support;

import com.alibaba.druid.pool.DruidDataSource;
import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.datasource.ApiBootDataSource;
import org.minbox.framework.api.boot.datasource.config.DataSourceDruidConfig;

import javax.sql.DataSource;

/**
 * The Druid {@link DataSource} config
 *
 * @author 恒宇少年
 */
public class ApiBootDruidDataSource extends DruidDataSource implements ApiBootDataSource {

    public ApiBootDruidDataSource(DataSourceDruidConfig config) {
        try {
            this.setUrl(config.getUrl());
            this.setUsername(config.getUsername());
            this.setPassword(config.getPassword());
            this.setDriverClassName(config.getDriverClassName());
            this.setFilters(config.getFilters());
            this.setMaxActive(config.getMaxActive());
            this.setInitialSize(config.getInitialSize());
            this.setMaxWait(config.getMaxWait());
            this.setValidationQuery(config.getValidationQuery());
            this.setTestWhileIdle(config.isTestWhileIdle());
            this.setTestOnBorrow(config.isTestOnBorrow());
            this.setTestOnReturn(config.isTestOnReturn());
        } catch (Exception e) {
            throw new ApiBootException("Create new druid dataSource fail.", e);
        }
    }

    /**
     * create new druid dataSource instance
     *
     * @return {@link DataSource} this class instance
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public DataSource build() throws ApiBootException {
        return this;
    }
}
