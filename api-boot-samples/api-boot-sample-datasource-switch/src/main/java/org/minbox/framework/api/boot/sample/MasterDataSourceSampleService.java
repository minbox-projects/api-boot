package org.minbox.framework.api.boot.sample;

import org.minbox.framework.datasource.annotation.DataSourceSwitch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-02 10:58
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@DataSourceSwitch("master")
public class MasterDataSourceSampleService {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private Slave1DataSourceSampleService slave1DataSourceSampleService;
    @Autowired
    private Slave2DataSourceSampleService slave2DataSourceSampleService;

    public void print() throws Exception {
        Connection connection = dataSource.getConnection();
        System.out.println(this.getClass().getSimpleName() + " ->" + connection.getCatalog());
        connection.close();
        // slave 1
        slave1DataSourceSampleService.print();
        // slave 2
        slave2DataSourceSampleService.print();
    }
}
