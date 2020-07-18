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

import org.minbox.framework.api.boot.secuirty.ApiBootWebSecurityConfiguration;
import org.minbox.framework.api.boot.secuirty.handler.ApiBootDefaultAccessDeniedHandler;
import org.minbox.framework.api.boot.secuirty.point.ApiBootDefaultAuthenticationEntryPoint;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * ApiBoot integrates SpringSecurity's default automation configuration
 *
 * @author 恒宇少年
 * @see ApiBootWebSecurityMemoryAutoConfiguration
 * @see ApiBootWebSecurityJdbcAutoConfiguration
 */
public class ApiBootWebSecurityAutoConfiguration extends ApiBootWebSecurityConfiguration {

    protected ApiBootSecurityProperties apiBootSecurityProperties;
    private AccessDeniedHandler accessDeniedHandler;
    private AuthenticationEntryPoint authenticationEntryPoint;

    public ApiBootWebSecurityAutoConfiguration(ApiBootSecurityProperties apiBootSecurityProperties, AccessDeniedHandler accessDeniedHandler, AuthenticationEntryPoint authenticationEntryPoint) {
        this.apiBootSecurityProperties = apiBootSecurityProperties;
        this.accessDeniedHandler = accessDeniedHandler;
        this.authenticationEntryPoint = authenticationEntryPoint;
    }

    /**
     * Configure exclude permissions blocked path
     * <p>
     * By default, use {@link ApiBootSecurityProperties#DEFAULT_IGNORE_URLS}
     *
     * @return Path to be excluded
     */
    @Override
    protected List<String> configureIgnoreUrls() {
        List<String> ignoringUrls = Arrays.asList(ApiBootSecurityProperties.DEFAULT_IGNORE_URLS);
        if (!ObjectUtils.isEmpty(apiBootSecurityProperties.getIgnoringUrls())) {
            ignoringUrls.addAll(Arrays.asList(apiBootSecurityProperties.getIgnoringUrls()));
        }
        return ignoringUrls;
    }

    @Override
    protected AccessDeniedHandler getAccessDeniedHandler() {
        return ObjectUtils.isEmpty(this.accessDeniedHandler) ? new ApiBootDefaultAccessDeniedHandler() : this.accessDeniedHandler;
    }

    @Override
    protected AuthenticationEntryPoint getAuthenticationEntryPoint() {
        return ObjectUtils.isEmpty(this.authenticationEntryPoint) ? new ApiBootDefaultAuthenticationEntryPoint() : this.authenticationEntryPoint;
    }

    @Override
    protected boolean disableHttpBasic() {
        return apiBootSecurityProperties.isDisableHttpBasic();
    }

    @Override
    protected boolean disableCsrf() {
        return apiBootSecurityProperties.isDisableCsrf();
    }
}
