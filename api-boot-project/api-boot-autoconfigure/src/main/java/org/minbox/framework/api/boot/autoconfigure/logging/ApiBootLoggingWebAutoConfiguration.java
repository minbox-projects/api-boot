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

import org.minbox.framework.logging.client.LoggingFactoryBean;
import org.minbox.framework.logging.client.interceptor.LoggingInterceptor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * ApiBoot logging web auto configuration
 * registry MinBox logging request interceptor
 * {@link LoggingInterceptor}
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass(LoggingFactoryBean.class)
@ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
@EnableConfigurationProperties(ApiBootLoggingProperties.class)
@AutoConfigureAfter(ApiBootLoggingAutoConfiguration.class)
public class ApiBootLoggingWebAutoConfiguration implements WebMvcConfigurer {
    private LoggingInterceptor loggingInterceptor;
    private ApiBootLoggingProperties loggingProperties;

    public ApiBootLoggingWebAutoConfiguration(LoggingInterceptor loggingInterceptor, ApiBootLoggingProperties loggingProperties) {
        this.loggingInterceptor = loggingInterceptor;
        this.loggingProperties = loggingProperties;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loggingInterceptor).addPathPatterns(loggingProperties.getLoggingPathPrefix());
    }
}
