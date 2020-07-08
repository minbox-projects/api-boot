package org.minbox.framework.api.boot.sample;

import org.minbox.framework.api.boot.tools.ApplicationContextTools;
import org.minbox.framework.api.boot.tools.BeanFactoryTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

/**
 * ApiBoot Tools sample
 *
 * @author 恒宇少年
 */
@SpringBootApplication
public class ApiBootToolsSampleApplication implements CommandLineRunner {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootToolsSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootToolsSampleApplication.class, args);
        logger.info("ApiBoot Tools sample bootstrap successfully.");
    }

    @Override
    public void run(String... args) throws Exception {
        // Just use ApplicationContextTools get applicationContext
        ApplicationContext applicationContext = ApplicationContextTools.getContext();
        logger.info("ApplicationContext Object：{}", applicationContext);
        // Just use BeanFactoryTools get bean instance
        ApplicationContextTools tools = BeanFactoryTools.getBean(ApplicationContextTools.class);
        logger.info("ApplicationContextTools Object：{}", tools);
    }
}
