package org.minbox.framework.api.boot.autoconfigure.sms;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import org.minbox.framework.api.boot.sms.ApiBootAliYunSmsService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.sms.ApiBootSmsProperties.API_BOOT_SMS_PREFIX;

/**
 * ApiBoot 短信服务自动化配置
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass(SendSmsRequest.class)
@ConditionalOnProperty(prefix = API_BOOT_SMS_PREFIX, name = {"access-key-id", "access-key-secret", "sign-name"})
@EnableConfigurationProperties(ApiBootSmsProperties.class)
public class ApiBootSmsAutoConfiguration {
    /**
     * ApiBoot Sms 属性配置类
     */
    private ApiBootSmsProperties apiBootSmsProperties;

    public ApiBootSmsAutoConfiguration(ApiBootSmsProperties apiBootSmsProperties) {
        this.apiBootSmsProperties = apiBootSmsProperties;
    }

    /**
     * 实例化ApiBoot Sms Service
     *
     * @return ApiBoot Sms Service
     */
    @Bean
    @ConditionalOnMissingBean
    ApiBootAliYunSmsService apiBootSmsService() {
        return new ApiBootAliYunSmsService(apiBootSmsProperties.getAccessKeyId(), apiBootSmsProperties.getAccessKeySecret(), apiBootSmsProperties.getSignName(), apiBootSmsProperties.getProfile(), apiBootSmsProperties.getConnectionTimeout(), apiBootSmsProperties.getReadTimeout());
    }
}
