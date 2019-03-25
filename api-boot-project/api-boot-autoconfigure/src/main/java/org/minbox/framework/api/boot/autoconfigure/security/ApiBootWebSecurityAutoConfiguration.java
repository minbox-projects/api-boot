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

    public ApiBootWebSecurityAutoConfiguration(ApiBootSecurityProperties apiBootSecurityProperties) {
        this.apiBootSecurityProperties = apiBootSecurityProperties;
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
}
