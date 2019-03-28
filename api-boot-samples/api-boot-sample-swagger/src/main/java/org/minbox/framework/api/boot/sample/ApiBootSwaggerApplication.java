package org.minbox.framework.api.boot.sample;

import org.minbox.framework.api.boot.autoconfigure.swagger.annotation.EnableApiBootSwagger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ApiBoot Swagger 示例
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-18 13:40
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
@EnableApiBootSwagger
public class ApiBootSwaggerApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootSwaggerApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootSwaggerApplication.class, args);
        logger.info("「「「「「ApiBoot Swagger Sample 启动完成.」」」」」");
    }
}
