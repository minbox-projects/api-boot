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

package org.minbox.framework.api.boot.autoconfigure.security;

import org.minbox.framework.api.boot.plugin.security.ApiBootWebSecurityConfiguration;
import org.minbox.framework.api.boot.plugin.security.point.ApiBootDefaultAuthenticationEntryPoint;
import org.minbox.framework.api.boot.plugin.security.handler.ApiBootDefaultAccessDeniedHandler;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * ApiBoot SpringSecurity自动化封装配置
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 15:24
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootWebSecurityAutoConfiguration extends ApiBootWebSecurityConfiguration {
    /**
     * 注入ApiBoot安全属性
     */
    protected ApiBootSecurityProperties apiBootSecurityProperties;
    /**
     * 异常处理
     */
    private AccessDeniedHandler accessDeniedHandler;
    /**
     * 端点处理
     */
    private AuthenticationEntryPoint authenticationEntryPoint;

    public ApiBootWebSecurityAutoConfiguration(ApiBootSecurityProperties apiBootSecurityProperties, AccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint authenticationEntryPoint) {
        this.apiBootSecurityProperties = apiBootSecurityProperties;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * 配置排除的路径列表
     *
     * @return 将要排除的路径
     */
    @Override
    protected List<String> configureIgnoreUrls() {
        List<String> ignoringUrls = new ArrayList<>();
        // 默认排除路径
        ignoringUrls.addAll(Arrays.asList(ApiBootSecurityProperties.DEFAULT_IGNORE_URLS));
        // 自定义排除的路径
        if (!ObjectUtils.isEmpty(apiBootSecurityProperties.getIgnoringUrls())) {
            ignoringUrls.addAll(Arrays.asList(apiBootSecurityProperties.getIgnoringUrls()));
        }
        return ignoringUrls;
    }

    /**
     * 返回项目内定义的AccessDeniedHandler实现类实例
     * 给ApiBoot Security进行配置
     *
     * @return
     */
    @Override
    protected AccessDeniedHandler getAccessDeniedHandler() {
        return ObjectUtils.isEmpty(this.accessDeniedHandler) ? new ApiBootDefaultAccessDeniedHandler() : this.accessDeniedHandler;
    }

    @Override
    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return ObjectUtils.isEmpty(this.authenticationEntryPoint) ? new ApiBootDefaultAuthenticationEntryPoint() : this.authenticationEntryPoint;
    }

    /**
     * 配置禁用http basic
     *
     * @return true：禁用，false：不禁用
     */
    @Override
    protected boolean disableHttpBasic() {
        return apiBootSecurityProperties.isDisableHttpBasic();
    }

    /**
     * 配置csrf
     *
     * @return true：禁用，false：不禁用
     */
    @Override
    protected boolean disableCsrf() {
        return apiBootSecurityProperties.isDisableCsrf();
    }
}
