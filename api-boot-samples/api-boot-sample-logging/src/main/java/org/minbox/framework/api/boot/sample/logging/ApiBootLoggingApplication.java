/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.minbox.framework.api.boot.sample.logging;

import org.minbox.framework.logging.spring.context.annotation.client.EnableLoggingClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

/**
 * 日志示例
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-15 22:41
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
@EnableLoggingClient
public class ApiBootLoggingApplication {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootLoggingApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(ApiBootLoggingApplication.class, args);
        logger.info("{}服务启动成功.", "ApiBoot Logging Sample");
    }

    /**
     * 测试使用RestTemplate来透传链路信息
     *
     * @return
     */
    @Bean
    @ConditionalOnMissingBean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
