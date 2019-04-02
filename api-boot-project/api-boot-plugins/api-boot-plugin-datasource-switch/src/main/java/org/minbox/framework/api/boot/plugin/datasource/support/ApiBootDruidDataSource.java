package org.minbox.framework.api.boot.plugin.datasource.support;

import com.alibaba.druid.pool.DruidDataSource;
import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.datasource.ApiBootDataSource;
import org.minbox.framework.api.boot.plugin.datasource.config.DataSourceDruidConfig;

import javax.sql.DataSource;

/**
 * Api Boot Druid Data Source
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-01 15:00
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
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
     * @return DataSource
     * @throws ApiBootException
     */
    @Override
    public DataSource build() throws ApiBootException {
        return this;
    }
}
