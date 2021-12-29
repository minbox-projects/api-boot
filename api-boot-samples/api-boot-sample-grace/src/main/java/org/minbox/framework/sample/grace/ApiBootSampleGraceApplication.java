package org.minbox.framework.sample.grace;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 恒宇少年
 */
@SpringBootApplication
public class ApiBootSampleGraceApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootSampleGraceApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootSampleGraceApplication.class, args);
        logger.info("Grace测试服务启动成功.");
    }
}
