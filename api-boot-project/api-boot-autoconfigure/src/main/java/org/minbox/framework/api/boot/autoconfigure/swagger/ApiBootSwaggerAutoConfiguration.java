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

import java.util.Collections;
import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.swagger.SwaggerProperties.API_BOOT_SWAGGER_PREFIX;

/**
 * Swagger2自动化配置
 * 只有在设置api.boot.swagger.enable=true时或者在配置文件内不进行配置时（因为默认为true）才会自动化配置该类
 * 通过@Import导入Swagger原本配置类
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-16 23:41
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnClass({SwaggerCommonConfiguration.class, Swagger2DocumentationConfiguration.class, Swagger2Controller.class})
@ConditionalOnProperty(prefix = API_BOOT_SWAGGER_PREFIX, name = "enable", havingValue = "true", matchIfMissing = true)
@Import({Swagger2DocumentationConfiguration.class, BeanValidatorPluginsConfiguration.class})
public class ApiBootSwaggerAutoConfiguration {
    /**
     * swagger相关属性配置
     */
    private SwaggerProperties swaggerProperties;
    /**
     * spring bean factory
     */
    private BeanFactory beanFactory;

    public ApiBootSwaggerAutoConfiguration(SwaggerProperties swaggerProperties, BeanFactory beanFactory) {
        this.swaggerProperties = swaggerProperties;
        this.beanFactory = beanFactory;
    }

    /**
     * 配置swagger基本信息
     * - BasePackage
     * 默认使用SpringBoot项目扫描bean根目录
     * 如果存在配置时则使用SwaggerProperties.basePackage作为扫描根目录
     *
     * @return Docket实例
     */
    @Bean
    public Docket docket() {
        String basePackage = swaggerProperties.getBasePackage();
        if (StringUtils.isEmpty(basePackage)) {
            basePackage = AutoConfigurationPackages.get(beanFactory).get(0);
        }
        return new Docket(DocumentationType.SWAGGER_2).apiInfo(apiInfo()).securitySchemes(Collections.singletonList(apiKey())).securityContexts(Collections.singletonList(securityContext())).select().apis(RequestHandlerSelectors.basePackage(basePackage)).paths(PathSelectors.any()).build();
    }

    /**
     * 配置文档基本信息
     * 如：文档标题、版本、描述、联系人基本信息等
     *
     * @return ApiInfo实例
     */
    public ApiInfo apiInfo() {
        return new ApiInfoBuilder().title(swaggerProperties.getTitle()).description(swaggerProperties.getDescription()).version(swaggerProperties.getVersion()).license(swaggerProperties.getLicense()).licenseUrl(swaggerProperties.getLicenseUrl()).contact(new Contact(swaggerProperties.getContact().getName(), swaggerProperties.getContact().getWebsite(), swaggerProperties.getContact().getEmail())).build();
    }

    /**
     * 配置Swagger整合Oauth2时的请求Key信息
     * 使用头部传递方式 Authorization: Beare TokenValue
     *
     * @return apiKey
     */
    private ApiKey apiKey() {
        return new ApiKey(swaggerProperties.getAuthorization().getName(), swaggerProperties.getAuthorization().getKeyName(), ApiKeyVehicle.HEADER.getValue());
    }

    /**
     * 配置安全上下文
     *
     * @return securityContext
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(this.defaultAuth()).forPaths(PathSelectors.regex(swaggerProperties.getAuthorization().getAuthRegex())).build();
    }

    /**
     * 配置默认权限
     *
     * @return SecurityReference arrays
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[]{authorizationScope};
        return Collections.singletonList(SecurityReference.builder().reference(swaggerProperties.getAuthorization().getName()).scopes(authorizationScopes).build());
    }
}
