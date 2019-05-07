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

package org.minbox.framework.api.boot.autoconfigure.ratelimiter;

import com.alibaba.boot.nacos.config.autoconfigure.NacosConfigAutoConfiguration;
import com.alibaba.boot.nacos.config.properties.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.exception.NacosException;
import org.minbox.framework.api.boot.plugin.rate.limiter.centre.RateLimiterConfigCentre;
import org.minbox.framework.api.boot.plugin.rate.limiter.centre.support.NacosRateLimiterConfigCentre;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Objects;
import java.util.Properties;

import static com.alibaba.nacos.api.PropertyKeyConst.*;

/**
 * Nacos Implementation of Distributed Configuration Center Used by ApiBoot
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-05-07 09:30
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(name = "com.alibaba.boot.nacos.config.properties.NacosConfigProperties")
@EnableConfigurationProperties({NacosConfigProperties.class})
@AutoConfigureAfter(NacosConfigAutoConfiguration.class)
public class ApiBootRateLimiterNacosConfigConfiguration {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootRateLimiterNacosConfigConfiguration.class);
    /**
     * nacos config service name
     */
    static final String NACOS_CONFIG_SERVICE_NAME = "nacosConfigService";
    /**
     * Nacos Config Properties
     */
    private NacosConfigProperties nacosConfigProperties;

    public ApiBootRateLimiterNacosConfigConfiguration(NacosConfigProperties nacosConfigProperties) {
        this.nacosConfigProperties = nacosConfigProperties;
        logger.info("ApiBoot RateLimiter nacos config centre is instantiated.");
    }

    /**
     * Initialize Nacos Configuration Center
     *
     * @param configService Nacos Config Service
     * @return RateLimiterConfigCentre
     * @see NacosRateLimiterConfigCentre
     */
    @Bean
    @ConditionalOnMissingBean
    public RateLimiterConfigCentre nacosRateLimiterConfigCentre(@Qualifier(NACOS_CONFIG_SERVICE_NAME) ConfigService configService) {
        return new NacosRateLimiterConfigCentre(configService);
    }

    /**
     * init ConfigService Instance
     *
     * @return NacosConfigService
     * @throws NacosException Nacos Exception
     */
    @Bean
    @ConditionalOnMissingBean(name = NACOS_CONFIG_SERVICE_NAME)
    public ConfigService nacosConfigService() throws NacosException {
        Properties properties = new Properties();
        properties.put(SERVER_ADDR, Objects.toString(nacosConfigProperties.getServerAddr(), ""));
        properties.put(ENCODE, Objects.toString(nacosConfigProperties.getEncode(), ""));
        properties.put(NAMESPACE, Objects.toString(nacosConfigProperties.getNamespace(), ""));
        properties.put(ACCESS_KEY, Objects.toString(nacosConfigProperties.getAccessKey(), ""));
        properties.put(SECRET_KEY, Objects.toString(nacosConfigProperties.getSecretKey(), ""));
        properties.put(CONTEXT_PATH, Objects.toString(nacosConfigProperties.getContextPath(), ""));
        properties.put(ENDPOINT, Objects.toString(nacosConfigProperties.getEndpoint(), ""));
        return NacosFactory.createConfigService(properties);
    }
}
