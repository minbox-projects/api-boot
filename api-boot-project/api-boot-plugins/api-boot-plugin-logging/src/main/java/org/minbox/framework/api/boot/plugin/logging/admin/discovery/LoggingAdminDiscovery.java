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

package org.minbox.framework.api.boot.plugin.logging.admin.discovery;

import org.minbox.framework.api.boot.common.exception.ApiBootException;

/**
 * Look Up ApiBoot Logging Admin Service Urls
 * 1. single api-boot-logging-admin service
 * 2. pull api-boot-logging-admin services from registry center
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-19 15:29
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface LoggingAdminDiscovery {
    /**
     * lookup a api-boot-logging-admin service url
     *
     * @return service url
     * @throws ApiBootException ApiBoot Exception
     */
    String lookup() throws ApiBootException;

    /**
     * get basic auth info
     * if config spring security user
     *
     * @return spring security user info
     * @throws ApiBootException ApiBoot Exception
     */
    String getBasicAuth() throws ApiBootException;
}
