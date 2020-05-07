package org.minbox.framework.api.boot.sample;

import org.minbox.framework.api.boot.autoconfigure.sequence.ApiBootSequenceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author 恒宇少年
 */
@SpringBootApplication
public class ApiBootSequenceSampleApplication implements CommandLineRunner {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootSequenceSampleApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootSequenceSampleApplication.class, args);
        logger.info("服务启动成功.");
    }

    /**
     * 注入ApiBoot提供的分布式ID生成类
     * <p>
     * 调用{@link ApiBootSequenceContext#nextId()}、{@link ApiBootSequenceContext#nextStringId()}方法可以直接获取ID
     */
    @Autowired
    private ApiBootSequenceContext apiBootSequenceContext;

    @Override
    public void run(String... args) throws Exception {
        // 获取下一个String类型的ID
        String nextId = apiBootSequenceContext.nextStringId();
        // 获取下一个Long类型的ID
        Long nextLongId = apiBootSequenceContext.nextId();
        System.out.println(nextId + "," + nextLongId);
    }
}
