/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.minbox.framework.api.boot.autoconfigure.oauth;

import lombok.Data;
import org.minbox.framework.api.boot.autoconfigure.security.SecurityAway;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.oauth.ApiBootOauthProperties.API_BOOT_OAUTH_PREFIX;

/**
 * 整合Oauth2 相关属性配置
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 16:52
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_OAUTH_PREFIX)
public class ApiBootOauthProperties {
    /**
     * 安全配置前缀
     */
    public static final String API_BOOT_OAUTH_PREFIX = "api.boot.oauth";
    /**
     * Oauth2认证存储方式，默认内存方式
     *
     * @see SecurityAway
     */
    private SecurityAway away = SecurityAway.memory;
    /**
     * Oauth2 clientId
     */
    private String clientId = "ApiBoot";
    /**
     * Oauth2 clientSecret
     */
    private String clientSecret = "ApiBootSecret";
    /**
     * 客户端授权类型集合
     */
    private String[] grantTypes = new String[]{"password", "refresh_token"};
    /**
     * 客户端作用域集合
     */
    private String[] scopes = new String[]{"api"};
    /**
     * 资源编号
     */
    private String resourceId = "api";
    /**
     * 配置JWT格式化Oauth2返回的token
     */
    private Jwt jwt = new Jwt();

    /**
     * 自定义Jwt相关的配置
     */
    @Data
    public static class Jwt {
        /**
         * 开启Jwt转换AccessToken
         */
        private boolean enable = false;
        /**
         * Jwt转换时使用的秘钥key
         */
        private String signKey = "ApiBoot";
    }
}
