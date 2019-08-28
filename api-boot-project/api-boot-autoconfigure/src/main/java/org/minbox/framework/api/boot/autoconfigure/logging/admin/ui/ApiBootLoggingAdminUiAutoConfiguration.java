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

package org.minbox.framework.api.boot.autoconfigure.logging.admin.ui;

import org.minbox.framework.logging.admin.storage.LoggingStorage;
import org.minbox.framework.logging.admin.ui.HomepageForwardingFilter;
import org.minbox.framework.logging.admin.ui.LoggingAdminUiFactoryBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templatemode.TemplateMode;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static java.util.Arrays.asList;

/**
 * ApiBoot Logging Admin Ui Configuration
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-26 15:08
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnBean(LoggingStorage.class)
@EnableConfigurationProperties(ApiBootLoggingAdminUiProperties.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
public class ApiBootLoggingAdminUiAutoConfiguration implements WebMvcConfigurer {
    /**
     * ApiBoot Logging Admin Ui Resource Handler Prefix
     */
    private static final String RESOURCE_PREFIX = "/**";
    /**
     * ApiBoot Logging Admin Template Html Suffix
     */
    private static final String TEMPLATE_SUFFIX = ".html";
    /**
     * Default Ui Router List
     */
    private static final List<String> DEFAULT_UI_ROUTES = asList(
        "/about/**",
        "/services/**",
        "/logs/**",
        "/wallboard/**"
    );
    /**
     * Application Context
     */
    private ApplicationContext applicationContext;
    /**
     * ApiBoot Logging Admin Ui Properties
     */
    private ApiBootLoggingAdminUiProperties adminUiProperties;

    public ApiBootLoggingAdminUiAutoConfiguration(ApplicationContext applicationContext, ApiBootLoggingAdminUiProperties adminUiProperties) {
        this.applicationContext = applicationContext;
        this.adminUiProperties = adminUiProperties;
    }

    /**
     * Configuration Resource Handler
     * add "api-boot-logging-admin-ui" resource path handler
     *
     * @param registry ResourceHandlerRegistry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(RESOURCE_PREFIX).addResourceLocations(this.adminUiProperties.getResourceLocations());
    }

    /**
     * Config thymeleaf Template Resolver
     *
     * @return SpringResourceTemplateResolver
     */
    @Bean
    public SpringResourceTemplateResolver adminTemplateResolver() {
        SpringResourceTemplateResolver resolver = new SpringResourceTemplateResolver();
        resolver.setApplicationContext(this.applicationContext);
        resolver.setPrefix(this.adminUiProperties.getTemplateLocation());
        resolver.setSuffix(TEMPLATE_SUFFIX);
        resolver.setTemplateMode(TemplateMode.HTML);
        resolver.setCharacterEncoding(StandardCharsets.UTF_8.name());
        resolver.setCacheable(this.adminUiProperties.isCacheTemplates());
        resolver.setOrder(10);
        resolver.setCheckExistence(true);
        return resolver;
    }

    /**
     * Home Page Forwarding Filter
     *
     * @return HomepageForwardingFilter
     * @throws IOException Io Exception
     */
    @Bean
    @ConditionalOnMissingBean
    public HomepageForwardingFilter homepageForwardFilter() throws IOException {
        return new HomepageForwardingFilter("/", DEFAULT_UI_ROUTES);
    }

    /**
     * Logging Admin Ui FactoryBean {@link org.minbox.framework.logging.admin.LoggingAdminFactoryBean}
     *
     * @return LoggingAdminUiFactoryBean
     */
    @Bean
    @ConditionalOnMissingBean
    public LoggingAdminUiFactoryBean loggingAdminUiFactoryBean() {
        LoggingAdminUiFactoryBean factoryBean = new LoggingAdminUiFactoryBean();
        factoryBean.setBrand(adminUiProperties.getBrand());
        factoryBean.setTitle(adminUiProperties.getTitle());
        factoryBean.setRoutes(DEFAULT_UI_ROUTES);
        return factoryBean;
    }
}
