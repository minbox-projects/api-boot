package org.minbox.framework.api.boot.autoconfigure.quartz;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import static org.minbox.framework.api.boot.autoconfigure.quartz.ApiBootQuartzProperties.API_BOOT_QUARTZ_PREFIX;

/**
 * ApiBoot Quartz 配置类
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-27 15:32
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_QUARTZ_PREFIX)
public class ApiBootQuartzProperties {
    /**
     * quartz 配置前缀
     */
    public static final String API_BOOT_QUARTZ_PREFIX = "api.boot.quartz";
    /**
     * Quartz job store type.
     */
    private JobStoreType jobStoreType = JobStoreType.MEMORY;

    /**
     * Name of the scheduler.
     */
    private String schedulerName;

    /**
     * Whether to automatically start the scheduler after initialization.
     */
    private boolean autoStartup = true;

    /**
     * Delay after which the scheduler is started once initialization completes. Setting
     * this property makes sense if no jobs should be run before the entire application
     * has started up.
     */
    private Duration startupDelay = Duration.ofSeconds(0);

    /**
     * Whether to wait for running jobs to complete on shutdown.
     */
    private boolean waitForJobsToCompleteOnShutdown = false;

    /**
     * Whether configured jobs should overwrite existing job definitions.
     */
    private boolean overwriteExistingJobs = false;

    /**
     * Additional Quartz Scheduler properties.
     */
    private final Map<String, String> properties = new HashMap<>();
    /**
     * prop config
     * all param have default value
     */
    private final ApiBootQuartzProperties.Prop prop = new ApiBootQuartzProperties.Prop();

    private final ApiBootQuartzProperties.Jdbc jdbc = new ApiBootQuartzProperties.Jdbc();

    /**
     * org.minbox.framework.api.boot.autoconfigure.quartz.ApiBootQuartzProperties#properties
     * <p>
     * quartz properties config
     */
    @Getter
    @Setter
    public static class Prop {
        @PropKey("org.quartz.scheduler.instanceName")
        private String schedulerInstanceName = "jobScheduler";

        @PropKey("org.quartz.scheduler.instanceId")
        private String schedulerInstanceId = "AUTO";

        @PropKey("org.quartz.jobStore.class")
        private String jobStoreClass = "org.quartz.impl.jdbcjobstore.JobStoreTX";

        @PropKey("org.quartz.jobStore.driverDelegateClass")
        private String jobStoreDriverDelegateClass = "org.quartz.impl.jdbcjobstore.StdJDBCDelegate";

        @PropKey("org.quartz.jobStore.tablePrefix")
        private String jobStoreTablePrefix = "QRTZ_";

        @PropKey("org.quartz.jobStore.isClustered")
        private boolean jobStoreClustered = true;

        @PropKey("org.quartz.jobStore.clusterCheckinInterval")
        private long jobStoreClusterCheckinInterval = 20000;

        @PropKey("org.quartz.threadPool.threadsInheritContextClassLoaderOfInitializingThread")
        private boolean threadPoolThreadsInheritContextClassLoaderOfInitializingThread = true;
    }

    public static class Jdbc {

        private static final String DEFAULT_SCHEMA_LOCATION = "classpath:org/quartz/impl/"
                + "jdbcjobstore/tables_@@platform@@.sql";

        /**
         * Path to the SQL file to use to initialize the database schema.
         */
        private String schema = DEFAULT_SCHEMA_LOCATION;

        /**
         * Prefix for single-line comments in SQL initialization scripts.
         */
        private String commentPrefix = "--";

        public String getSchema() {
            return this.schema;
        }

        public void setSchema(String schema) {
            this.schema = schema;
        }

        public String getCommentPrefix() {
            return this.commentPrefix;
        }

        public void setCommentPrefix(String commentPrefix) {
            this.commentPrefix = commentPrefix;
        }

    }
}
