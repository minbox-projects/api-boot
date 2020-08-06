package org.minbox.framework.api.boot.autoconfigure.oss;

import com.aliyun.oss.OSSClient;
import org.minbox.framework.oss.ObjectStorageProgress;
import org.minbox.framework.oss.ObjectStorageService;
import org.minbox.framework.oss.support.aliyun.AliyunObjectStorageService;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.oss.ApiBootOssProperties.API_BOOT_OSS_PREFIX;

/**
 * Object storage automation configuration
 *
 * @author 恒宇少年
 * @see AliyunObjectStorageService
 */
@Configuration
@EnableConfigurationProperties(ApiBootOssProperties.class)
@ConditionalOnClass(OSSClient.class)
@ConditionalOnProperty(prefix = API_BOOT_OSS_PREFIX, name = {"region", "access-key-id", "access-key-secret", "bucket-name"})
public class ApiBootOssAutoConfiguration {

    private ApiBootOssProperties apiBootOssProperties;
    private ObjectStorageProgress apiBootObjectStorageProgress;

    public ApiBootOssAutoConfiguration(ApiBootOssProperties apiBootOssProperties, ObjectProvider<ObjectStorageProgress> apiBootProgressProvider) {
        this.apiBootOssProperties = apiBootOssProperties;
        this.apiBootObjectStorageProgress = apiBootProgressProvider.getIfAvailable();
    }

    /**
     * Register {@link ObjectStorageService}
     *
     * @return The {@link ObjectStorageService} implement class instance
     */
    @Bean
    @ConditionalOnMissingBean
    ObjectStorageService apiBootOssService() {
        AliyunObjectStorageService objectStorageService = new AliyunObjectStorageService(apiBootOssProperties.getRegion().getEndpoint(), apiBootOssProperties.getBucketName(), apiBootOssProperties.getAccessKeyId(), apiBootOssProperties.getAccessKeySecret(), apiBootOssProperties.getDomain());
        objectStorageService.setObjectStorageProgress(apiBootObjectStorageProgress);
        return objectStorageService;
    }
}
