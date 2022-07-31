package org.minbox.framework.api.boot.autoconfigure.logging.admin;

import org.minbox.framework.logging.admin.LoggingAdminFactoryBean;
import org.minbox.framework.logging.admin.storage.LoggingDataSourceStorage;
import org.minbox.framework.logging.admin.storage.LoggingStorage;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

import static org.minbox.framework.api.boot.autoconfigure.logging.admin.ApiBootLoggingAdminProperties.API_BOOT_LOGGING_ADMIN_PREFIX;

/**
 * ApiBoot logging admin database away configuration
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass({DataSource.class, LoggingAdminFactoryBean.class})
@ConditionalOnBean(DataSource.class)
@ConditionalOnProperty(prefix = API_BOOT_LOGGING_ADMIN_PREFIX, name = "storage-away", havingValue = "jdbc")
@AutoConfigureAfter(DataSourceAutoConfiguration.class)
public class ApiBootLoggingAdminDataBaseAutoConfiguration {

    /**
     * {@link org.minbox.framework.logging.admin.storage.LoggingStorage} database
     *
     * @param dataSource {@link DataSource}
     * @return {@link LoggingDataSourceStorage}
     */
    @Bean
    @ConditionalOnMissingBean(LoggingStorage.class)
    public LoggingStorage loggingDataSourceStorage(DataSource dataSource) {
        return new LoggingDataSourceStorage(dataSource);
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
