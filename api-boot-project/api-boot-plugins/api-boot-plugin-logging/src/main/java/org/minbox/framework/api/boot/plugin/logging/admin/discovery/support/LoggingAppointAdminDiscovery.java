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

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * ApiBoot Logging Appoint Admin Discovery
 * Support multiple admins
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-19 15:44
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class LoggingAppointAdminDiscovery extends LoggingAbstractAdminDiscovery {
    /**
     * basic user split
     */
    private static final String BASIC_SPLIT = "@";
    /**
     * http prefix
     */
    private static final String HTTP_PREFIX = "http://";
    /**
     * ApiBoot Logging Admin Server Address
     */
    private String[] adminServerAddress;
    /**
     * ApiBoot Logging Admin Server Address
     * To Current Thread
     */
    private final ThreadLocal<String> CURRENT_SERVER_ADDRESS = new ThreadLocal();

    public LoggingAppointAdminDiscovery(String[] adminServerAddress) {
        this.adminServerAddress = adminServerAddress;
    }

    /**
     * load balance lookup admin server address
     *
     * @return admin server address
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public String lookup() throws ApiBootException {
        Assert.notNull(adminServerAddress, "ApiBoot Logging Admin Server Address Is Null.");
        String serverAddress = adminServerAddress[0];
        CURRENT_SERVER_ADDRESS.set(serverAddress);
        // have basic auth
        if (serverAddress.indexOf(BASIC_SPLIT) > 0) {
            serverAddress = serverAddress.substring(serverAddress.indexOf(BASIC_SPLIT) + 1);
        }
        // append http prefix
        return HTTP_PREFIX + serverAddress;
    }

    /**
     * get basic auth
     *
     * @return basic auth base64
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public String getBasicAuth() throws ApiBootException {
        String serverAddress = CURRENT_SERVER_ADDRESS.get();
        if (serverAddress.indexOf(BASIC_SPLIT) > 0) {
            String basicInfo = serverAddress.substring(0, serverAddress.indexOf(BASIC_SPLIT));
            CURRENT_SERVER_ADDRESS.remove();
            if (!ObjectUtils.isEmpty(basicInfo)) {
                return getBasicBase64(basicInfo);
            }
        }
        return null;
    }
}
