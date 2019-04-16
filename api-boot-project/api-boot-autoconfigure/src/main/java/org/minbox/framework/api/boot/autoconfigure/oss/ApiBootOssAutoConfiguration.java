package org.minbox.framework.api.boot.autoconfigure.oss;

import com.aliyun.oss.OSSClient;
import org.minbox.framework.api.boot.plugin.oss.ApiBootOssService;
import org.minbox.framework.api.boot.plugin.oss.progress.ApiBootObjectStorageProgress;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.oss.ApiBootOssProperties.API_BOOT_OSS_PREFIX;

/**
 * ApiBoot 整合阿里云Oss对象存储自动化配置
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-21 11:12
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@EnableConfigurationProperties(ApiBootOssProperties.class)
@ConditionalOnClass(OSSClient.class)
@ConditionalOnProperty(prefix = API_BOOT_OSS_PREFIX, name = {"region", "access-key-id", "access-key-secret", "bucket-name"})
public class ApiBootOssAutoConfiguration {
    /**
     * ApiBoot Oss 属性配置
     */
    private ApiBootOssProperties apiBootOssProperties;
    /**
     * ApiBoot Progress Provider
     */
    private ApiBootObjectStorageProgress apiBootObjectStorageProgress;

    public ApiBootOssAutoConfiguration(ApiBootOssProperties apiBootOssProperties, ObjectProvider<ApiBootObjectStorageProgress> apiBootProgressProvider) {
        this.apiBootOssProperties = apiBootOssProperties;
        this.apiBootObjectStorageProgress = apiBootProgressProvider.getIfAvailable();
    }

    /**
     * 实例化ApiBootOssService
     *
     * @return ApiBootOssService 实例
     */
    @Bean
    @ConditionalOnMissingBean
    ApiBootOssService apiBootOssService() {
        ApiBootOssService apiBootOssService = new ApiBootOssService(apiBootOssProperties.getRegion().getEndpoint(), apiBootOssProperties.getBucketName(), apiBootOssProperties.getAccessKeyId(), apiBootOssProperties.getAccessKeySecret(), apiBootOssProperties.getDomain());
        apiBootOssService.setApiBootObjectStorageProgress(apiBootObjectStorageProgress);
        return apiBootOssService;
    }
}
