package org.minbox.framework.api.boot.plugin.datasource.config;

import lombok.Data;

/**
 * druid data source config parameter
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-01 11:41
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
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
