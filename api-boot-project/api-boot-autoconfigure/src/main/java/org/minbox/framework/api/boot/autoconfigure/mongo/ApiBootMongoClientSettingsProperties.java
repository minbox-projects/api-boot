package org.minbox.framework.api.boot.autoconfigure.mongo;

import lombok.Data;
import org.minbox.framework.mongo.client.setting.MongoClientSettingsBean;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.NestedConfigurationProperty;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.mongo.ApiBootMongoClientSettingsProperties.API_BOOT_MONGO_CLIENT_SETTINGS;

/**
 * {@link com.mongodb.MongoClientSettings} config properties
 *
 * @author 恒宇少年
 */
@Configuration
@ConfigurationProperties(prefix = API_BOOT_MONGO_CLIENT_SETTINGS)
@Data
public class ApiBootMongoClientSettingsProperties {
    /**
     * The mongo client settings configure properties prefix
     */
    public static final String API_BOOT_MONGO_CLIENT_SETTINGS = "api.boot.mongo";
    /**
     * The mongo client settings bean
     * <p>
     * Provides {@link com.mongodb.MongoClientSettings} related expansion configuration
     */
    @NestedConfigurationProperty
    private MongoClientSettingsBean settings;
}
