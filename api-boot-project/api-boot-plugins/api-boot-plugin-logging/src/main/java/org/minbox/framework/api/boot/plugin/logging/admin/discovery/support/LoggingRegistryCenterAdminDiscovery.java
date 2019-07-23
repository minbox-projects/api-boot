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
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

/**
 * ApiBoot Logging Registry Center Admin Discovery
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-19 15:45
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class LoggingRegistryCenterAdminDiscovery extends LoggingAbstractAdminDiscovery {
    /**
     * admin service id
     */
    private String serviceId;
    /**
     * admin spring security username
     */
    private String username;
    /**
     * admin spring security user password
     */
    private String password;
    /**
     * Ribbon Load Balance Client
     */
    private LoadBalancerClient loadBalancerClient;

    public LoggingRegistryCenterAdminDiscovery(String serviceId, String username, String password, LoadBalancerClient loadBalancerClient) {
        this.serviceId = serviceId;
        this.username = username;
        this.password = password;
        this.loadBalancerClient = loadBalancerClient;
    }

    /**
     * @return
     * @throws ApiBootException
     */
    @Override
    public String lookup() throws ApiBootException {
        Assert.notNull(serviceId, "ApiBoot Logging Admin ServiceID is null.");
        ServiceInstance serviceInstance = loadBalancerClient.choose(serviceId);
        if (ObjectUtils.isEmpty(serviceInstance)) {
            throw new ApiBootException("There Is No Online ApiBoot Logging Admin Service.");
        }
        return serviceInstance.getUri().toString();
    }

    /**
     * get basic auth base64 string
     *
     * @return basic auth base64 string
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public String getBasicAuth() throws ApiBootException {
        if (ObjectUtils.isEmpty(username) || ObjectUtils.isEmpty(password)) {
            return null;
        }
        return getBasicBase64(String.format("%s:%s", username, password));
    }
}
