package org.minbox.framework.api.boot.sample.mongo.setting;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * "api-boot-starter-mongo-client-settings" 依赖测试启动入口
 *
 * @author 恒宇少年
 */
@SpringBootApplication
public class ApiBootMongoClientSettingsApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootMongoClientSettingsApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootMongoClientSettingsApplication.class, args);
        logger.info("Mongo扩展参数示例服务启动成功.");
    }
}
