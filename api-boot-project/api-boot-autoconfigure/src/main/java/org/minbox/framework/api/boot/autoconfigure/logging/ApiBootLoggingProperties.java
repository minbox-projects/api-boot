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

package org.minbox.framework.api.boot.autoconfigure.logging;

import lombok.Data;
import org.minbox.framework.logging.core.ReportAway;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.logging.ApiBootLoggingProperties.API_BOOT_LOGGING_PREFIX;

/**
 * ApiBoot Logging Properties
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-15 22:29
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConfigurationProperties(prefix = API_BOOT_LOGGING_PREFIX)
@Data
public class ApiBootLoggingProperties {
    /**
     * ApiBoot logging properties config prefix
     */
    public static final String API_BOOT_LOGGING_PREFIX = "api.boot.logging";
    /**
     * Interception log path prefix
     */
    private String[] loggingPathPrefix = new String[]{"/**"};
    /**
     * Ignore path array
     */
    private String[] ignorePaths;
    /**
     * Format console log JSON
     */
    private boolean formatConsoleLogJson = false;
    /**
     * show console log
     */
    private boolean showConsoleLog = false;
    /**
     * Report Request Log To Admin Away
     */
    private ReportAway reportAway = ReportAway.just;
    /**
     * Number of request logs reported once
     */
    private int reportNumberOfRequestLog = 10;
    /**
     * report to admin initial delay second
     */
    private int reportInitialDelaySecond = 5;
    /**
     * report to admin interval second
     */
    private int reportIntervalSecond = 5;
    /**
     * logging cache away
     */
    private LoggingCacheAway loggingCacheAway = LoggingCacheAway.memory;
    /**
     * ApiBoot Logging Admin Instance
     */
    private AdminInstance admin = new AdminInstance();
    /**
     * ApiBoot Logging Discovery Instance
     * support eureka
     */
    private DiscoveryInstance discovery;
    /**
     * Choose load balancing strategy for admin report log
     * {@link org.minbox.framework.logging.client.admin.discovery.lb.LoadBalanceStrategy}
     *
     * @see org.minbox.framework.logging.client.admin.discovery.lb.support.RandomWeightedStrategy
     * @see org.minbox.framework.logging.client.admin.discovery.lb.support.SmoothWeightedRoundRobinStrategy
     */
    private LoadBalanceStrategyAway loadBalanceStrategy = LoadBalanceStrategyAway.RANDOM_WEIGHT;

    /**
     * Config ApiBoot Logging Admin Server
     * report every request log to api-boot-logging-admin
     */
    @Data
    public static class AdminInstance {
        /**
         * ApiBoot Logging Admin Server Address
         */
        private String serverAddress;

    }

    /**
     * Config ApiBoot Logging Discovery Instance
     * Draw the list of ApiBoot Logging Admin addresses from the registry
     * and report the request log through load balancing
     */
    @Data
    public static class DiscoveryInstance {
        /**
         * ApiBoot Logging Admin Spring Security Username
         */
        private String username;
        /**
         * ApiBoot Logging Admin Spring Security User Password
         */
        private String password;
        /**
         * ApiBoot Logging Admin Service ID
         */
        private String serviceId;
    }
}
