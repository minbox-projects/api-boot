package org.minbox.framework.api.boot.autoconfigure.quartz;

import lombok.Data;
import org.springframework.boot.autoconfigure.quartz.JobStoreType;
import org.springframework.boot.autoconfigure.quartz.QuartzProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
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

    private final QuartzProperties.Jdbc jdbc = new QuartzProperties.Jdbc();

    public static class Jdbc {

        private static final String DEFAULT_SCHEMA_LOCATION = "classpath:org/quartz/impl/"
                + "jdbcjobstore/tables_@@platform@@.sql";

        /**
         * Path to the SQL file to use to initialize the database schema.
         */
        private String schema = DEFAULT_SCHEMA_LOCATION;

        /**
         * Database schema initialization mode.
         */
        private DataSourceInitializationMode initializeSchema = DataSourceInitializationMode.EMBEDDED;

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

        public DataSourceInitializationMode getInitializeSchema() {
            return this.initializeSchema;
        }

        public void setInitializeSchema(DataSourceInitializationMode initializeSchema) {
            this.initializeSchema = initializeSchema;
        }

        public String getCommentPrefix() {
            return this.commentPrefix;
        }

        public void setCommentPrefix(String commentPrefix) {
            this.commentPrefix = commentPrefix;
        }

    }
}
