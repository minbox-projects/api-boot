package org.minbox.framework.api.boot.autoconfigure.datasource;

import com.alibaba.druid.pool.DruidDataSource;
import org.minbox.framework.datasource.DataSourceFactoryBean;
import org.minbox.framework.datasource.MinBoxDataSource;
import org.minbox.framework.datasource.aop.advistor.DataSourceSwitchAdvisor;
import org.minbox.framework.datasource.aop.interceptor.DataSourceSwitchAnnotationInterceptor;
import org.minbox.framework.datasource.config.DataSourceConfig;
import org.minbox.framework.datasource.config.DataSourceDruidConfig;
import org.minbox.framework.datasource.routing.MinBoxSwitchRoutingDataSource;
import org.minbox.framework.datasource.routing.customizer.DataSourceSelectionCustomizer;
import org.minbox.framework.datasource.support.MinBoxDruidDataSource;
import org.minbox.framework.datasource.support.MinBoxHikariDataSource;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * ApiBoot DataSource Switch AutoConfiguration
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass({MinBoxDataSource.class, AbstractRoutingDataSource.class})
@EnableConfigurationProperties(ApiBootDataSourceSwitchProperties.class)
@AutoConfigureBefore(DataSourceAutoConfiguration.class)
public class ApiBootDataSourceSwitchAutoConfiguration {
    /**
     * ApiBoot DataSource Switch Properties
     */
    private ApiBootDataSourceSwitchProperties dataSourceSwitchProperties;
    private DataSourceSelectionCustomizer selectionCustomizer;

    public ApiBootDataSourceSwitchAutoConfiguration(ApiBootDataSourceSwitchProperties dataSourceSwitchProperties,
                                                    ObjectProvider<DataSourceSelectionCustomizer> customizerObjectProvider) {
        this.dataSourceSwitchProperties = dataSourceSwitchProperties;
        this.selectionCustomizer = customizerObjectProvider.getIfAvailable();
    }

    /**
     * ApiBoot DataSource FactoryBean
     * Used to create datasource
     *
     * @return ApiBootDataSourceFactoryBean
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSourceFactoryBean dataSourceFactoryBean() {
        return new DataSourceFactoryBean();
    }

    /**
     * ApiBoot Routing DataSource
     * switch use datasource
     * {@link DataSource}
     *
     * @param dataSourceFactoryBean ApiBoot DataSource FactoryBean
     * @return DataSource
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSource dataSource(DataSourceFactoryBean dataSourceFactoryBean) {
        List<DataSourceConfig> dataSourceConfigList = new LinkedList();
        Map<String, DataSourceConfig> dataSourceConfigMap = new HashMap(1);

        // put druid datasource config to map
        dataSourceConfigMap.putAll(dataSourceSwitchProperties.getDruid());
        // put hikari datasource config to map
        dataSourceConfigMap.putAll(dataSourceSwitchProperties.getHikari());

        // convert all datasource config
        dataSourceConfigMap.keySet().stream().forEach(poolName -> {
            DataSourceConfig dataSourceConfig = dataSourceConfigMap.get(poolName);
            // set data source pool name
            dataSourceConfig.setPoolName(poolName);
            // datasource type
            dataSourceConfig.setDataSourceType(dataSourceConfig instanceof DataSourceDruidConfig ? MinBoxDruidDataSource.class : MinBoxHikariDataSource.class);

            // after convert add to data source list
            dataSourceConfigList.add(dataSourceConfig);
        });

        return new MinBoxSwitchRoutingDataSource(dataSourceFactoryBean, dataSourceSwitchProperties.getPrimary(), dataSourceConfigList, selectionCustomizer);
    }

    /**
     * ApiBoot DataSource Switch Advice Interceptor
     *
     * @return ApiBootDataSourceSwitchAnnotationInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSourceSwitchAnnotationInterceptor dataSourceSwitchAnnotationInterceptor() {
        return new DataSourceSwitchAnnotationInterceptor();
    }

    /**
     * ApiBoot DataSource Switch Advisor
     * Used to get @DataSourceSwitch annotation define
     *
     * @param dataSourceSwitchAnnotationInterceptor ApiBoot DataSource Annotation Interceptor
     * @return ApiBootDataSourceSwitchAdvisor
     */
    @Bean
    @ConditionalOnMissingBean
    public DataSourceSwitchAdvisor apiBootDataSourceSwitchAdvisor(DataSourceSwitchAnnotationInterceptor dataSourceSwitchAnnotationInterceptor) {
        return new DataSourceSwitchAdvisor(dataSourceSwitchAnnotationInterceptor);
    }

}
