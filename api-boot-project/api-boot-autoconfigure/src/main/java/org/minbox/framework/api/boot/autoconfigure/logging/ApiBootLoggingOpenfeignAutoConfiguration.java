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

package org.minbox.framework.api.boot.autoconfigure.logging;

import feign.RequestInterceptor;
import org.minbox.framework.logging.client.http.openfeign.LoggingOpenFeignInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * ApiBoot Logging Openfeign Http Away Configuration
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-16 16:05
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass({RequestInterceptor.class, LoggingOpenFeignInterceptor.class})
@EnableConfigurationProperties(ApiBootLoggingProperties.class)
public class ApiBootLoggingOpenfeignAutoConfiguration {

    /**
     * ApiBoot Logging Openfeign Interceptor
     *
     * @return ApiBootLogOpenFeignInterceptor
     */
    @Bean
    @ConditionalOnMissingBean
    public RequestInterceptor apiBootLogOpenFeignInterceptor() {
        return new LoggingOpenFeignInterceptor();
    }
}
