package org.minbox.framework.api.boot.datasource;

import org.minbox.framework.api.boot.common.exception.ApiBootException;

import javax.sql.DataSource;

/**
 * ApiBoot Extends {@link DataSource}
 *
 * @author 恒宇少年
 */
public interface ApiBootDataSource extends DataSource {
    /**
     * Create new data source Instance
     *
     * @return {@link DataSource}
     * @throws ApiBootException ApiBoot Exception
     */
    DataSource build() throws ApiBootException;
}
