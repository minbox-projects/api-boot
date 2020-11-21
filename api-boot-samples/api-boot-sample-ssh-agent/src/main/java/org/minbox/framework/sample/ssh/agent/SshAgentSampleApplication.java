package org.minbox.framework.sample.ssh.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * ssh agent 示例入口
 *
 * @author 恒宇少年
 */
@SpringBootApplication
public class SshAgentSampleApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(SshAgentSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(SshAgentSampleApplication.class, args);
        logger.info("ssh agent服务启动成功.");
    }
}
