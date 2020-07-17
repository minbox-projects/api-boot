package org.minbox.framework.api.boot.datasource.config;

import lombok.Data;

/**
 * Druid data source configuration class
 *
 * @author 恒宇少年
 */
@Data
public class DataSourceDruidConfig extends DataSourceConfig {
    private String filters = "stat,wall,slf4j";
    private int maxActive = 20;
    private int initialSize = 1;
    private long maxWait = 60000;
    private String validationQuery = "select 1 from dual";
    private boolean testWhileIdle = true;
    private boolean testOnBorrow = false;
    private boolean testOnReturn = false;

}
