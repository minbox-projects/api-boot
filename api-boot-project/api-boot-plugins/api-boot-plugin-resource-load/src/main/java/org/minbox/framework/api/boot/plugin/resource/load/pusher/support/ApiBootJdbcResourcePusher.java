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

package org.minbox.framework.api.boot.plugin.resource.load.pusher.support;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.resource.load.ApiBootResourceStoreDelegate;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.List;

/**
 * ApiBoot Jdbc Resource Pusher
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-19 09:33
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootJdbcResourcePusher extends ApiBootAbstractResourcePusher {
    /**
     * ApiBoot Resource Store Delegate
     * Use load resource url from jdbc
     */
    private ApiBootResourceStoreDelegate apiBootResourceStoreDelegate;

    public ApiBootJdbcResourcePusher(ApiBootResourceStoreDelegate apiBootResourceStoreDelegate) {
        this.apiBootResourceStoreDelegate = apiBootResourceStoreDelegate;
        if (ObjectUtils.isEmpty(this.apiBootResourceStoreDelegate)) {
            throw new ApiBootException("Unable to load [ApiBootResourceStoreDelegate] implementation class instance");
        }
    }

    /**
     * load resource from ApiBootResourceStoreDelegate#loadResourceUrl method
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     * @return resource List
     * @see ApiBootResourceStoreDelegate
     */
    @Override
    public List<String> loadResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType) {
        return apiBootResourceStoreDelegate.loadResourceUrl(sourceFieldValue, resourceType);
    }

    /**
     * delete resource urls
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     */
    @Override
    public void deleteResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType) {
        apiBootResourceStoreDelegate.deleteResource(sourceFieldValue, resourceType);
    }

    /**
     * insert resource urls
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     * @param resourceUrls     resource urls
     */
    @Override
    public void insertResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType, List<String> resourceUrls) {
        apiBootResourceStoreDelegate.addResource(sourceFieldValue, resourceType, resourceUrls);
    }

    /**
     * update resource urls
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     * @param resourceUrls     resource urls
     */
    @Override
    public void updateResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType, List<String> resourceUrls) {
        apiBootResourceStoreDelegate.updateResource(sourceFieldValue, resourceType, resourceUrls);
    }
}
