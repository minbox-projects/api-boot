package org.minbox.framework.api.boot.sample;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-02 16:38
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
public class ApiBootDataSourceSwitchSampleApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootDataSourceSwitchSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootDataSourceSwitchSampleApplication.class, args);
        logger.info("「「「「「ApiBoot DataSource Switch 启动完成.」」」」」");
    }
}
