package org.minbox.framework.api.boot.autoconfigure.logging.admin;

import org.minbox.framework.logging.admin.LoggingAdminFactoryBean;
import org.minbox.framework.logging.admin.storage.LoggingDataSourceStorage;
import org.minbox.framework.logging.admin.storage.LoggingStorage;
import org.minbox.framework.logging.admin.storage.mongo.LoggingMongoStorage;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.minbox.framework.api.boot.autoconfigure.logging.admin.ApiBootLoggingAdminProperties.API_BOOT_LOGGING_ADMIN_PREFIX;

/**
 * ApiBoot logging admin mongo away configuration
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass({MongoTemplate.class, LoggingAdminFactoryBean.class})
@ConditionalOnBean(MongoTemplate.class)
@ConditionalOnProperty(prefix = API_BOOT_LOGGING_ADMIN_PREFIX, name = "storage-away", havingValue = "mongo")
@AutoConfigureAfter(MongoDataAutoConfiguration.class)
public class ApiBootLoggingAdminMongoAutoConfiguration {
    /**
     * {@link org.minbox.framework.logging.admin.storage.LoggingStorage} database
     *
     * @param mongoTemplate {@link MongoTemplate}
     * @return {@link LoggingDataSourceStorage}
     */
    @Bean
    @ConditionalOnMissingBean(LoggingStorage.class)
    public LoggingStorage loggingMongoStorage(MongoTemplate mongoTemplate) {
        return new LoggingMongoStorage(mongoTemplate);
    }
}
