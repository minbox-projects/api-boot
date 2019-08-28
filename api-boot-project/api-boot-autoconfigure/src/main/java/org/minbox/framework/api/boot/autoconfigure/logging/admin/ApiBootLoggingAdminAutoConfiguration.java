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
import org.minbox.framework.logging.admin.endpoint.LoggingEndpoint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.scheduling.annotation.EnableAsync;

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
@ConditionalOnBean(DataSource.class)
@EnableConfigurationProperties(ApiBootLoggingAdminProperties.class)
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
@Import({
    ApiBootLoggingAdminUiAutoConfiguration.class
})
@EnableAsync
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
    }

    /**
     * instantiation {@link LoggingAdminFactoryBean}
     *
     * @param dataSource {@link DataSource}
     * @return LoggingAdminFactoryBean
     */
    @Bean
    public LoggingAdminFactoryBean loggingAdminFactoryBean(DataSource dataSource) {
        LoggingAdminFactoryBean factoryBean = new LoggingAdminFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setShowConsoleReportLog(apiBootLoggingAdminProperties.isShowConsoleReportLog());
        factoryBean.setFormatConsoleLogJson(apiBootLoggingAdminProperties.isFormatConsoleLogJson());
        return factoryBean;
    }

    /**
     * Verify that the {@link DataSource} exists and perform an exception alert when it does not exist
     *
     * @see org.minbox.framework.api.boot.autoconfigure.enhance.ApiBootMyBatisEnhanceAutoConfiguration
     */
    @Configuration
    @ConditionalOnMissingBean(DataSource.class)
    public static class DataSourceNotFoundConfiguration implements InitializingBean {
        @Override
        public void afterPropertiesSet() throws Exception {
            throw new BeanCreationException("No " + DataSource.class.getName() + " Found.");
        }
    }
}
