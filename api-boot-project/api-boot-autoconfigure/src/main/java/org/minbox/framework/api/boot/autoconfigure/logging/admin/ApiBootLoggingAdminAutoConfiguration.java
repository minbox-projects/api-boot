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

import org.minbox.framework.api.boot.plugin.logging.admin.discovery.LoggingAdminDiscovery;
import org.minbox.framework.api.boot.plugin.logging.admin.endpoint.LoggingEndpoint;
import org.minbox.framework.api.boot.plugin.logging.admin.endpoint.LoggingRequestMappingHandlerMapping;
import org.minbox.framework.api.boot.plugin.logging.admin.listener.ReportLogJsonFormatListener;
import org.minbox.framework.api.boot.plugin.logging.admin.listener.ReportLogStorageListener;
import org.minbox.framework.api.boot.plugin.logging.admin.storage.LoggingDataSourceStorage;
import org.minbox.framework.api.boot.plugin.logging.admin.storage.LoggingStorage;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.*;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerMapping;

import javax.sql.DataSource;

/**
 * ApiBoot Logging Admin Configuration
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-19 10:38
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(LoggingEndpoint.class)
@ConditionalOnWebApplication
@EnableConfigurationProperties(ApiBootLoggingAdminProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class ApiBootLoggingAdminAutoConfiguration {
    /**
     * ApiBoot Logging Admin Properties
     */
    private ApiBootLoggingAdminProperties apiBootLoggingAdminProperties;

    public ApiBootLoggingAdminAutoConfiguration(ApiBootLoggingAdminProperties apiBootLoggingAdminProperties) {
        this.apiBootLoggingAdminProperties = apiBootLoggingAdminProperties;
    }

    /**
     * ApiBoot Logging Endpoint
     * Receive Log report
     *
     * @return LoggingEndpoint
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingEndpoint loggingEndpoint() {
        return new LoggingEndpoint();
    }

    /**
     * ApiBoot Logging Request Mapping
     *
     * @return HandlerMapping
     */
    @Bean
    public HandlerMapping loggingRequestMappingHandlerMapping() {
        return new LoggingRequestMappingHandlerMapping(loggingEndpoint());
    }

    /**
     * Report Log Json Format Listener
     *
     * @return ReportLogJsonFormatListener
     */
    @Bean
    @ConditionalOnMissingBean
    public ReportLogJsonFormatListener reportLogJsonFormatListener() {
        return new ReportLogJsonFormatListener(apiBootLoggingAdminProperties.isShowConsoleReportLog(), apiBootLoggingAdminProperties.isFormatConsoleLogJson());
    }

    /**
     * Storage Request Log Configuration
     */
    @Configuration
    @ConditionalOnBean(DataSource.class)
    public static class StorageLogAutoConfiguration {
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
}
