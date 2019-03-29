package org.minbox.framework.api.boot.sample.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-22 13:48
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
public class ApiBootSmsSampleApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootSmsSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootSmsSampleApplication.class, args);
        logger.info("「「「「「ApiBoot Sms 示例启动成功.」」」」」");
    }
}
