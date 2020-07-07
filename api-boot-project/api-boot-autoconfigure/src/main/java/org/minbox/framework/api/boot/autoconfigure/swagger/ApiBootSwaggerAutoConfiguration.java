package org.minbox.framework.api.boot.autoconfigure.swagger;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.util.StringUtils;
import springfox.bean.validators.configuration.BeanValidatorPluginsConfiguration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.configuration.SwaggerCommonConfiguration;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger2.configuration.Swagger2DocumentationConfiguration;
import springfox.documentation.swagger2.web.Swagger2Controller;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.swagger.SwaggerProperties.API_BOOT_SWAGGER_PREFIX;

/**
 * Swagger2 automation configuration
 * Only when setting "api.boot.swagger.enable=true" or when no configuration in the configuration file (because the default is true) will automatically configure this class
 * Import the original configuration class of Swagger through @Import
 *
 * @author 恒宇少年
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnClass({SwaggerCommonConfiguration.class, Swagger2DocumentationConfiguration.class, Swagger2Controller.class})
@ConditionalOnProperty(prefix = API_BOOT_SWAGGER_PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
@Import({Swagger2DocumentationConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class ApiBootSwaggerAutoConfiguration {
    private static final String AUTHORIZATION_SCOPE = "global";
    private static final String AUTHORIZATION_SCOPE_DESCRIPTION = "accessEverything";
    private SwaggerProperties swaggerProperties;
    private BeanFactory beanFactory;

    public ApiBootSwaggerAutoConfiguration(SwaggerProperties swaggerProperties, BeanFactory beanFactory) {
        this.swaggerProperties = swaggerProperties;
        this.beanFactory = beanFactory;
    }

    /**
     * Configure {@link Docket}
     * <p>
     * The base package value:
     * default value is {@link AutoConfigurationPackages#get} is used
     *
     * @return The {@link Docket} instance
     */
    @Bean
    public Docket docket() {
        String basePackage = swaggerProperties.getBasePackage();
        if (StringUtils.isEmpty(basePackage)) {
            basePackage = AutoConfigurationPackages.get(beanFactory).get(0);
        }
        return new Docket(DocumentationType.SWAGGER_2)
            .apiInfo(apiInfo())
            .securitySchemes(Collections.singletonList(apiKey()))
            .securityContexts(Collections.singletonList(securityContext()))
            .select()
            .apis(RequestHandlerSelectors.basePackage(basePackage))
            .paths(PathSelectors.any())
            .build();
    }

    /**
     * Configure {@link ApiInfo}
     *
     * @return The {@link ApiInfo} instance
     */
    public ApiInfo apiInfo() {
        Contact contact = this.convertContact(swaggerProperties.getContact());
        return new ApiInfoBuilder()
            .title(swaggerProperties.getTitle())
            .description(swaggerProperties.getDescription())
            .version(swaggerProperties.getVersion())
            .license(swaggerProperties.getLicense())
            .licenseUrl(swaggerProperties.getLicenseUrl())
            .contact(contact)
            .build();
    }

    /**
     * Request key information when configuring Swagger to integrate Oauth2
     * <p>
     * Use header transfer {@link ApiKeyVehicle#HEADER} method "Authorization: Bearer TokenValue"
     *
     * @return The {@link ApiKey} instance
     */
    private ApiKey apiKey() {
        return new ApiKey(swaggerProperties.getAuthorization().getName(),
            swaggerProperties.getAuthorization().getKeyName(),
            ApiKeyVehicle.HEADER.getValue());
    }

    /**
     * Configure {@link SecurityContext}
     *
     * @return The {@link SecurityContext} instance
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
            .securityReferences(this.defaultAuth())
            .forPaths(PathSelectors.regex(swaggerProperties.getAuthorization().getAuthRegex()))
            .build();
    }

    /**
     * Configure {@link SecurityReference}
     * <p>
     * The configured reference will be displayed on the dialog
     *
     * @return The {@link SecurityReference} list
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope(AUTHORIZATION_SCOPE, AUTHORIZATION_SCOPE_DESCRIPTION);
        AuthorizationScope[] authorizationScopes = Arrays.asList(authorizationScope).stream().toArray(AuthorizationScope[]::new);
        SecurityReference securityReference =
            SecurityReference.builder()
                .reference(swaggerProperties.getAuthorization().getName())
                .scopes(authorizationScopes)
                .build();
        return Collections.singletonList(securityReference);
    }

    /**
     * Convert {@link SwaggerProperties.Contact} to {@link Contact}
     *
     * @param contact The {@link SwaggerProperties.Contact} instance
     * @return {@link Contact} instance
     */
    private Contact convertContact(SwaggerProperties.Contact contact) {
        return new Contact(contact.getName(), contact.getWebsite(), contact.getEmail());
    }
}
