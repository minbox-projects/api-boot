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

package org.minbox.framework.api.boot.plugin.logging.admin.discovery.support;

import org.apache.tomcat.util.codec.binary.Base64;
import org.minbox.framework.api.boot.plugin.logging.admin.discovery.LoggingAdminDiscovery;

/**
 * ApiBoot Logging Abstract Admin Discovery
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-19 17:17
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public abstract class LoggingAbstractAdminDiscovery implements LoggingAdminDiscovery {
    /**
     * basic auth
     */
    private static final String BASIC_AUTH = "Basic %s";

    /**
     * get basic auth base64 string
     *
     * @param basicInfo basic info
     * @return basic auth base64 string
     */
    protected String getBasicBase64(String basicInfo) {
        String basicBase64 = Base64.encodeBase64String(basicInfo.getBytes());
        return String.format(BASIC_AUTH, basicBase64);
    }
}
