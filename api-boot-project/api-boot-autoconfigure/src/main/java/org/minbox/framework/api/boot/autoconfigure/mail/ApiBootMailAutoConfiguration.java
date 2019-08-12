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

package org.minbox.framework.api.boot.autoconfigure.mail;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.minbox.framework.api.boot.plugin.mail.ApiBootAliYunMailService;
import org.minbox.framework.api.boot.plugin.mail.ApiBootMailService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.mail.ApiBootMailProperties.API_BOOT_MAIL_PREFIX;

/**
 * ApiBoot Mail Auto Configuration
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-06-19 18:41
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass(ApiBootAliYunMailService.class)
@ConditionalOnProperty(prefix = API_BOOT_MAIL_PREFIX, name = {"access-key", "access-secret", "account-name", "region"})
@EnableConfigurationProperties(ApiBootMailProperties.class)
public class ApiBootMailAutoConfiguration {
    /**
     * ApiBoot Mail Properties
     */
    private ApiBootMailProperties apiBootMailProperties;

    public ApiBootMailAutoConfiguration(ApiBootMailProperties apiBootMailProperties) {
        this.apiBootMailProperties = apiBootMailProperties;
    }

    /**
     * ApiBoot Mail Service AliYun Support
     *
     * @param acsClient AcsClient
     * @return ApiBootMailService Support Instance
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiBootMailService apiBootMailService(IAcsClient acsClient) {
        return new ApiBootAliYunMailService(acsClient, apiBootMailProperties.getAccountName(), apiBootMailProperties.isReplyToAddress(), apiBootMailProperties.getAddressType(), apiBootMailProperties.getFromAlias(), apiBootMailProperties.getTagName());
    }

    /**
     * Instance AliYun Acs Client
     *
     * @return IAcsClient Support Instance
     * @see DefaultProfile
     * @see DefaultAcsClient
     */
    @Bean
    @ConditionalOnMissingBean
    public IAcsClient acsClient() {
        IClientProfile profile = DefaultProfile.getProfile(apiBootMailProperties.getRegion().getValue(), apiBootMailProperties.getAccessKey(), apiBootMailProperties.getAccessSecret());
        return new DefaultAcsClient(profile);
    }
}
