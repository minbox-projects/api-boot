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

package org.minbox.framework.api.boot.autoconfigure.pageable;

import lombok.Data;
import org.minbox.framework.mybatis.pageable.common.DataBaseDialect;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

import static org.minbox.framework.api.boot.autoconfigure.pageable.ApiBootMyBatisPageableProperties.API_BOOT_PAGEABLE_PREFIX;


/**
 * Mybatis pageable properties
 *
 * @author 恒宇少年
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_PAGEABLE_PREFIX)
public class ApiBootMyBatisPageableProperties {
    /**
     * mybatis pageable prefix
     */
    public static final String API_BOOT_PAGEABLE_PREFIX = "api.boot.pageable";
    /**
     * The database dialect
     * <p>
     * default use {@link DataBaseDialect#MYSQL}
     */
    private DataBaseDialect dialect = DataBaseDialect.MYSQL;

    /**
     * Convert this instance to {@link Properties}
     *
     * @return The {@link Properties} config instance
     */
    public Properties convertProperties() {
        Properties properties = new Properties();
        properties.setProperty(PropertiesNames.DATABASE_DIALECT, dialect.getValue().getName());
        return properties;
    }
}
