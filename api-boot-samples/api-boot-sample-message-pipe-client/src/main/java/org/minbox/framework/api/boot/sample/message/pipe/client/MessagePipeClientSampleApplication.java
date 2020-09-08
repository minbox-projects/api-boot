package org.minbox.framework.api.boot.sample.message.pipe.client;

import org.minbox.framework.message.pipe.spring.annotation.ServerServiceType;
import org.minbox.framework.message.pipe.spring.annotation.client.EnableMessagePipeClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 恒宇少年
 */
@SpringBootApplication
public class MessagePipeClientSampleApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(MessagePipeClientSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MessagePipeClientSampleApplication.class, args);
        logger.info("ApiBoot Message Pipe Client 服务启动成功.");
    }
}
