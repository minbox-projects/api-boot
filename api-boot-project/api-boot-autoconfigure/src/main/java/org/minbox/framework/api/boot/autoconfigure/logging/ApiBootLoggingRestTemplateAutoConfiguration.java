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

import org.minbox.framework.logging.client.http.rest.LoggingRestTemplateInterceptor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;

/**
 * ApiBoot Logging ResTemplate Config
 * {@link RestTemplate}
 * Setting Interceptor Transmit Link Information
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass(RestTemplate.class)
@ConditionalOnBean(RestTemplate.class)
public class ApiBootLoggingRestTemplateAutoConfiguration {

    public ApiBootLoggingRestTemplateAutoConfiguration(RestTemplate restTemplate) {

        List<ClientHttpRequestInterceptor> interceptors = restTemplate.getInterceptors();

        LoggingRestTemplateInterceptor interceptor = new LoggingRestTemplateInterceptor();

        if (ObjectUtils.isEmpty(interceptors)) {
            restTemplate.setInterceptors(Arrays.asList(interceptor));
        } else {
            interceptors.add(interceptor);
        }
    }
}
