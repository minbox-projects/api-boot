package org.minbox.framework.api.boot.autoconfigure.datasource;

import org.minbox.framework.datasource.DataSourceFactoryBean;
import org.minbox.framework.datasource.MinBoxDataSource;
import org.minbox.framework.datasource.aop.advistor.DataSourceSwitchAdvisor;
import org.minbox.framework.datasource.aop.interceptor.DataSourceSwitchAnnotationInterceptor;
import org.minbox.framework.datasource.config.DataSourceConfig;
import org.minbox.framework.datasource.config.DataSourceDruidConfig;
import org.minbox.framework.datasource.environment.DataSourceSwitchEnvironment;
import org.minbox.framework.datasource.environment.customizer.DataSourceEnvironmentSelectionCustomizer;
import org.minbox.framework.datasource.routing.MinBoxSwitchRoutingDataSource;
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
import java.util.*;
import java.util.stream.Collectors;

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
    private DataSourceEnvironmentSelectionCustomizer environmentSelectionCustomizer;

    public ApiBootDataSourceSwitchAutoConfiguration(ApiBootDataSourceSwitchProperties dataSourceSwitchProperties,
                                                    ObjectProvider<DataSourceEnvironmentSelectionCustomizer> customizerObjectProvider) {
        this.dataSourceSwitchProperties = dataSourceSwitchProperties;
        this.environmentSelectionCustomizer = customizerObjectProvider.getIfAvailable();
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
        List<DataSourceSwitchEnvironment> switchEnvironments = this.dataSourceSwitchProperties.getEnvironments().keySet()
            .stream()
            .map(environment -> {
                ApiBootDataSourceSwitchEnvironmentProperties environmentProperties = this.dataSourceSwitchProperties.getEnvironments().get(environment);

                Map<String, DataSourceConfig> dataSourceConfigMap = new HashMap<>();
                dataSourceConfigMap.putAll(environmentProperties.getDruid());
                dataSourceConfigMap.putAll(environmentProperties.getHikari());

                // @formatter:off
                List<DataSourceConfig> configs = dataSourceConfigMap.keySet()
                    .stream()
                    .map(poolName -> {
                        DataSourceConfig dataSourceConfig = dataSourceConfigMap.get(poolName);
                        dataSourceConfig.setPoolName(poolName);
                        dataSourceConfig.setDataSourceType(dataSourceConfig instanceof DataSourceDruidConfig ? MinBoxDruidDataSource.class : MinBoxHikariDataSource.class);
                        return dataSourceConfig;
                    })
                    .collect(Collectors.toList());
                return DataSourceSwitchEnvironment.initialization()
                    .environment(environment)
                    .primaryPoolName(environmentProperties.getPrimary())
                    .configs(configs)
                    .build();
                // @formatter:on
            })
            .collect(Collectors.toList());
        return new MinBoxSwitchRoutingDataSource(dataSourceFactoryBean, environmentSelectionCustomizer, dataSourceSwitchProperties.getActiveEnvironment(), switchEnvironments);
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
