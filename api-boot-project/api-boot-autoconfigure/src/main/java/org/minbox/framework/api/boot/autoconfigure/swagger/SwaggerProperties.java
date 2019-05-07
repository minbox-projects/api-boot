package org.minbox.framework.api.boot.autoconfigure.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.swagger.SwaggerProperties.API_BOOT_SWAGGER_PREFIX;

/**
 * swagger属性配置
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-16 23:39
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_SWAGGER_PREFIX)
public class SwaggerProperties {
    /**
     * swagger配置前缀
     */
    public static final String API_BOOT_SWAGGER_PREFIX = "api.boot.swagger";
    /**
     * 是否开启文档，默认开启
     */
    private boolean enable = true;
    /**
     * swagger扫描本项目的base-package
     * 扫描该package下的所有controller来生成文档
     */
    private String basePackage;
    /**
     * 文档标题
     */
    private String title = "ApiBoot快速集成Swagger文档";
    /**
     * 文档描述
     */
    private String description = "ApiBoot通过自动化配置快速集成Swagger2文档，仅需一个注解、一个依赖即可。";
    /**
     * 文档版本号
     */
    private String version = "2.0.7.RELEASE";
    /**
     * 文档版权
     */
    private String license = "ApiBoot";
    /**
     * 文档版权路径
     */
    private String licenseUrl = "https://github.com/hengboy/api-boot";
    /**
     * 文档编写联系人信息
     */
    private Contact contact = new Contact();
    /**
     * 文档接口访问时认证信息
     */
    private Authorization authorization = new Authorization();

    /**
     * 文档编写联系人信息
     */
    @Data
    public static class Contact {
        /**
         * 联系人名称
         */
        private String name = "恒宇少年";
        /**
         * 联系人主页
         */
        private String website = "http://blog.yuqiyu.com";
        /**
         * 联系人邮箱地址
         */
        private String email = "jnyuqy@gmail.com";
    }

    /**
     * 文档接口访问所需认证配置信息
     */
    @Data
    public static class Authorization {
        /**
         * 整合Oauth2后授权名称
         */
        private String name = "ApiBoot Security Oauth 认证头信息";
        /**
         * 整合Oauth2后授权请求头Header内的key-name
         */
        private String keyName = "Authorization";
        /**
         * 整合Oauth2后授权表达式
         */
        private String authRegex = "^.*$";
    }
}
