package org.minbox.framework.api.boot.sample;

import org.minbox.framework.api.boot.autoconfigure.oss.ApiBootOssProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;

/**
 * ApiBoot Sample Oss
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-21 17:06
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@SpringBootApplication
public class ApiBootOssSampleApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootOssSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootOssSampleApplication.class, args);
        logger.info("「「「「「ApiBoot Oss Sample 启动成功.」」」」」");
    }

    /**
     * 将自定义Oss Service 放入Spring Ioc
     *
     * @param apiBootOssProperties oss属性配置类
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    ApiBootOssOverrideService apiBootOssOverrideService(ApiBootOssProperties apiBootOssProperties) {
        return new ApiBootOssOverrideService(apiBootOssProperties.getRegion().getEndpoint(), apiBootOssProperties.getBucketName(), apiBootOssProperties.getAccessKeyId(), apiBootOssProperties.getAccessKeySecret(), apiBootOssProperties.getDomain());
    }
}
