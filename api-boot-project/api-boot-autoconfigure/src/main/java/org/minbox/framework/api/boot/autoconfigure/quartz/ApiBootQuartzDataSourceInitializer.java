package org.minbox.framework.api.boot.autoconfigure.quartz;

import org.springframework.boot.jdbc.AbstractDataSourceInitializer;
import org.springframework.boot.jdbc.DataSourceInitializationMode;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.datasource.init.ResourceDatabasePopulator;
import org.springframework.util.Assert;

import javax.sql.DataSource;

/**
 * {@link AbstractDataSourceInitializer} when using quartz in jdbc mode
 *
 * @author 恒宇少年
 */
public class ApiBootQuartzDataSourceInitializer extends AbstractDataSourceInitializer {
    private final ApiBootQuartzProperties properties;

    public ApiBootQuartzDataSourceInitializer(DataSource dataSource,
                                              ResourceLoader resourceLoader, ApiBootQuartzProperties properties) {
        super(dataSource, resourceLoader);
        Assert.notNull(properties, "QuartzProperties must not be null");
        this.properties = properties;
    }

    @Override
    protected void customize(ResourceDatabasePopulator populator) {
        populator.setCommentPrefix(this.properties.getJdbc().getCommentPrefix());
    }

    @Override
    protected DataSourceInitializationMode getMode() {
        return this.properties.getJdbc().getInitializeSchema();
    }

    @Override
    protected String getSchemaLocation() {
        return this.properties.getJdbc().getSchema();
    }

    @Override
    protected String getDatabaseName() {
        String databaseName = super.getDatabaseName();
        if ("db2".equals(databaseName)) {
            return "db2_v95";
        }
        if ("mysql".equals(databaseName)) {
            return "mysql_innodb";
        }
        if ("postgresql".equals(databaseName)) {
            return "postgres";
        }
        if ("sqlserver".equals(databaseName)) {
            return "sqlServer";
        }
        return databaseName;
    }
}
