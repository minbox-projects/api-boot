package org.minbox.framework.api.boot.plugin.datasource;

import org.minbox.framework.api.boot.common.exception.ApiBootException;

import javax.sql.DataSource;

/**
 * Api Boot DataSource 接口定义
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-01 14:55
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface ApiBootDataSource extends DataSource {
    /**
     * Create new data source Instance
     *
     * @return DataSource
     * @throws ApiBootException 异常信息
     */
    DataSource build() throws ApiBootException;
}
