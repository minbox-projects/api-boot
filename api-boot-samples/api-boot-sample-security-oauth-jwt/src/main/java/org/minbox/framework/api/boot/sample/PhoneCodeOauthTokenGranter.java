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

package org.minbox.framework.api.boot.sample;

import org.minbox.framework.api.boot.plugin.oauth.exception.ApiBootTokenException;
import org.minbox.framework.api.boot.plugin.oauth.grant.ApiBootOauthTokenGranter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Map;

/**
 * 短信验证码登录示例
 *
 * @author 恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-06-06 09:15
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Component
public class PhoneCodeOauthTokenGranter implements ApiBootOauthTokenGranter {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(PhoneCodeOauthTokenGranter.class);

    /**
     * 获取Token时使用grant_type=phone_code授权方式
     */
    private static final String GRANT_TYPE = "phone_code";

    /**
     * 参数：手机号
     */
    private static final String PARAM_PHONE = "phone";
    /**
     * 参数：验证码
     */
    private static final String PARAM_CODE = "code";

    @Override
    public String grantType() {
        return GRANT_TYPE;
    }

    /**
     * 该方法参数集合是获取Token时携带的参数
     * phone=171xxxxx
     * code=196523
     *
     * @param parameters parameter map
     * @return UserDetails
     * @throws ApiBootTokenException ApiBoot Exception
     */
    @Override
    public UserDetails loadByParameter(Map<String, String> parameters) throws ApiBootTokenException {
        // 获取Token路径：/oauth/token?grant_type=phone_code&phone=171xxxxx&code=196523
        String phone = parameters.get(PARAM_PHONE);
        String code = parameters.get(PARAM_CODE);

        logger.debug("手机号：{}", phone);
        logger.debug("验证码：{}", code);

        // 自定义数据逻辑校验验证码是否正确、是否与该手机号匹配等
        // 校验通过后返回实现SpringSecurity提供的UserDetails接口的数据实体即可
        return new UserDetails() {
            @Override
            public Collection<? extends GrantedAuthority> getAuthorities() {
                return null;
            }

            @Override
            public String getPassword() {
                return null;
            }

            @Override
            public String getUsername() {
                return phone;
            }

            @Override
            public boolean isAccountNonExpired() {
                return true;
            }

            @Override
            public boolean isAccountNonLocked() {
                return true;
            }

            @Override
            public boolean isCredentialsNonExpired() {
                return true;
            }

            @Override
            public boolean isEnabled() {
                return true;
            }
        };
    }
}
