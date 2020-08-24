package org.minbox.framework.api.boot.sample.message.pipe.server;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 恒宇少年
 */
@SpringBootApplication
public class MessagePipeServerSampleApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(MessagePipeServerSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MessagePipeServerSampleApplication.class, args);
        logger.info("ApiBoot Message Pipe Server服务启动成功.");
    }
}
