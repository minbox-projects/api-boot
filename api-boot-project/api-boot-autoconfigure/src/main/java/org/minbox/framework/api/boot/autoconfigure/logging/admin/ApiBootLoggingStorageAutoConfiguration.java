/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.minbox.framework.api.boot.autoconfigure.logging.admin;

import org.minbox.framework.api.boot.plugin.logging.admin.listener.ReportLogStorageListener;
import org.minbox.framework.api.boot.plugin.logging.admin.storage.LoggingDataSourceStorage;
import org.minbox.framework.api.boot.plugin.logging.admin.storage.LoggingStorage;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

/**
 * ApiBoot Logging Storage Configuration
 * Store request logs in a database to provide data for analysis
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-26 15:09
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnBean(DataSource.class)
public class ApiBootLoggingStorageAutoConfiguration {
    /**
     * Logging DataSource Storage
     *
     * @return LoggingDataSourceStorage
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingStorage loggingStorage(DataSource dataSource) {
        return new LoggingDataSourceStorage(dataSource);
    }

    /**
     * Report Log Storage Listener
     *
     * @return ReportLogStorageListener
     */
    @Bean
    @ConditionalOnMissingBean
    public ReportLogStorageListener reportLogStorageListener(LoggingStorage loggingStorage) {
        return new ReportLogStorageListener(loggingStorage);
    }
}
