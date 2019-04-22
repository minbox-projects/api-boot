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

package org.minbox.framework.api.boot.autoconfigure.push;

import lombok.Data;
import org.minbox.framework.api.boot.plugin.message.push.model.PushClientConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

import static org.minbox.framework.api.boot.autoconfigure.push.ApiBootMessagePushProperties.API_BOOT_PUSH_PREFIX;

/**
 * ApiBoot Message Push Properties
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-20 14:52
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_PUSH_PREFIX)
public class ApiBootMessagePushProperties {
    /**
     * push 配置前缀
     */
    public static final String API_BOOT_PUSH_PREFIX = "api.boot.push";
    /**
     * 极光推送客户端配置
     */
    private PushClientConfig client;
    /**
     * 多个推送客户端配置集合
     */
    private Map<String, PushClientConfig> multiple;
    /**
     * 配置是否生产环境推送，适用IOS平台
     */
    private boolean production = false;
}
