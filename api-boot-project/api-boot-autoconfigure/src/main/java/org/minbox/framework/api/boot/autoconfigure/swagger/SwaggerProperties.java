package org.minbox.framework.api.boot.autoconfigure.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.swagger.SwaggerProperties.API_BOOT_SWAGGER_PREFIX;

/**
 * Swagger config properties
 *
 * @author 恒宇少年
 * @see ApiBootSwaggerAutoConfiguration
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_SWAGGER_PREFIX)
public class SwaggerProperties {
    /**
     * Swagger config properties prefix
     */
    public static final String API_BOOT_SWAGGER_PREFIX = "api.boot.swagger";
    /**
     * Whether to open the document
     * open by default
     */
    private boolean enable = true;
    /**
     * Scan all controllers under this package to generate documents
     */
    private String basePackage;
    /**
     * Document title
     */
    private String title = "ApiBoot快速集成Swagger文档";
    /**
     * Document description
     */
    private String description = "ApiBoot通过自动化配置快速集成Swagger2文档，仅需一个注解、一个依赖即可。";
    /**
     * Document version
     * <p>
     * Same as ApiBoot version
     */
    private String version = "2.2.7.RELEASE";
    /**
     * Document copyright owner
     */
    private String license = "ApiBoot";
    /**
     * Document copyright path
     */
    private String licenseUrl = "https://github.com/hengboy/api-boot";
    /**
     * Document writing contact
     */
    private Contact contact = new Contact();
    /**
     * Authentication information when accessing the document interface
     */
    private Authorization authorization = new Authorization();

    /**
     * Document contact
     */
    @Data
    public static class Contact {
        /**
         * Contact name
         */
        private String name = "恒宇少年";
        /**
         * Contact website
         */
        private String website = "http://blog.yuqiyu.com";
        /**
         * Contact mail address
         */
        private String email = "jnyuqy@gmail.com";
    }

    /**
     * Document authorization
     */
    @Data
    public static class Authorization {
        /**
         * Authorization description
         */
        private String name = "ApiBoot Security Oauth 认证头信息";
        /**
         * Authorization head name
         */
        private String keyName = "Authorization";
        /**
         * Authorization expression
         */
        private String authRegex = "^.*$";
    }
}
