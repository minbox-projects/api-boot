package org.minbox.framework.api.boot.sample.quartz;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-28 17:25
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
public class ApiBootQuartzSampleApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootQuartzSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootQuartzSampleApplication.class);
        logger.info("「「「「「ApiBoot Quartz 启动成功.」」」」」");
    }
}
