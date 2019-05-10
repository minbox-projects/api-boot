package org.minbox.framework.knowledge.library.service;

import org.minbox.framework.api.boot.autoconfigure.swagger.annotation.EnableApiBootSwagger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * 恒宇少年知识库服务接口
 *
 * @author yuqiyu
 */
@SpringBootApplication
@EnableApiBootSwagger
public class KnowledgeLibraryServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(KnowledgeLibraryServiceApplication.class, args);
    }
}
