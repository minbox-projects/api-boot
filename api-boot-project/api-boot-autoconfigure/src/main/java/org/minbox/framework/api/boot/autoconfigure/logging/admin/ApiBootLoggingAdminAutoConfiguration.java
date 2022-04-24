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

import org.minbox.framework.api.boot.autoconfigure.logging.admin.ui.ApiBootLoggingAdminUiAutoConfiguration;
import org.minbox.framework.logging.admin.LoggingAdminFactoryBean;
import org.minbox.framework.logging.admin.storage.LoggingStorage;
import org.minbox.framework.logging.spring.context.annotation.admin.EnableLoggingAdmin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * ApiBoot Logging Admin Configuration
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass(LoggingAdminFactoryBean.class)
@EnableConfigurationProperties(ApiBootLoggingAdminProperties.class)
@Import({
    ApiBootLoggingAdminUiAutoConfiguration.class
})
@EnableAsync
@EnableLoggingAdmin
@AutoConfigureAfter({ApiBootLoggingAdminDataBaseAutoConfiguration.class, ApiBootLoggingAdminMongoAutoConfiguration.class})
public class ApiBootLoggingAdminAutoConfiguration {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootLoggingAdminAutoConfiguration.class);
    /**
     * {@link ApiBootLoggingAdminProperties}
     */
    private ApiBootLoggingAdminProperties apiBootLoggingAdminProperties;

    public ApiBootLoggingAdminAutoConfiguration(ApiBootLoggingAdminProperties apiBootLoggingAdminProperties) {
        this.apiBootLoggingAdminProperties = apiBootLoggingAdminProperties;
        logger.info("LoggingAdmin storage away uses {}.", apiBootLoggingAdminProperties.getStorageAway());
    }

    /**
     * instantiation {@link LoggingAdminFactoryBean}
     *
     * @param loggingStorage The logging storage object instance
     * @return LoggingAdminFactoryBean
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingAdminFactoryBean loggingAdminFactoryBean(LoggingStorage loggingStorage) {
        LoggingAdminFactoryBean factoryBean = new LoggingAdminFactoryBean();
        factoryBean.setLoggingStorage(loggingStorage);
        factoryBean.setShowConsoleReportLog(apiBootLoggingAdminProperties.isShowConsoleReportLog());
        factoryBean.setFormatConsoleLogJson(apiBootLoggingAdminProperties.isFormatConsoleLogJson());
        factoryBean.setCleanerSetting(apiBootLoggingAdminProperties.getCleanerSetting());
        logger.info("LoggingAdminFactoryBean init successfully.");
        return factoryBean;
    }
}
