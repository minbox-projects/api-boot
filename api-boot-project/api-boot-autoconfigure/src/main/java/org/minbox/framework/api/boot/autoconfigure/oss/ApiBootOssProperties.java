package org.minbox.framework.api.boot.autoconfigure.oss;

import lombok.Data;
import org.minbox.framework.oss.OssRegion;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.oss.ApiBootOssProperties.API_BOOT_OSS_PREFIX;

/**
 * ApiBoot Oss 属性配置
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-21 11:13
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConfigurationProperties(prefix = API_BOOT_OSS_PREFIX)
@Data
public class ApiBootOssProperties {
    /**
     * oss 配置前缀
     */
    public static final String API_BOOT_OSS_PREFIX = "api.boot.oss";
    /**
     * 存储地域
     */
    private OssRegion region;
    /**
     * 授权秘钥Id
     */
    private String accessKeyId;
    /**
     * 授权秘钥Secret
     */
    private String accessKeySecret;
    /**
     * 存储空间名称
     */
    private String bucketName;
    /**
     * Oss存储空间自定义域名
     */
    private String domain;

}
