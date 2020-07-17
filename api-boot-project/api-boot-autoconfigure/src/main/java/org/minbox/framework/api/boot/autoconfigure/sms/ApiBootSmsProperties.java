package org.minbox.framework.api.boot.autoconfigure.sms;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.sms.ApiBootSmsProperties.API_BOOT_SMS_PREFIX;

/**
 * ApiBoot 集成 阿里云国际短信服务配置类
 *
 * @author 恒宇少年
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_SMS_PREFIX)
public class ApiBootSmsProperties {
    /**
     * sms 配置前缀
     */
    public static final String API_BOOT_SMS_PREFIX = "api.boot.sms";
    /**
     * RAM账号的AccessKey ID
     */
    private String accessKeyId;
    /**
     * RAM账号Access Key Secret
     */
    private String accessKeySecret;
    /**
     * 短信签名
     */
    private String signName;
    /**
     * 短信发送连接超时时长
     */
    private long connectionTimeout = 10000;
    /**
     * 短信接收消息连接超时时长
     */
    private long readTimeout = 10000;
    /**
     * 短信区域环境
     */
    private String profile = "default";
}
