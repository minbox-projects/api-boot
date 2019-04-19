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

import org.minbox.framework.api.boot.plugin.resource.load.ApiBootResourceStoreDelegate;
import org.minbox.framework.api.boot.plugin.resource.load.context.ApiBootResourceContext;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ApiBoot Resource Memory Pusher
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-19 11:16
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootMemoryResourcePusher extends ApiBootJdbcResourcePusher {
    /**
     * memory resource urls
     */
    private Map<String, List<String>> RESOURCE_URLS = new HashMap();

    public ApiBootMemoryResourcePusher(ApiBootResourceStoreDelegate apiBootResourceStoreDelegate) {
        super(apiBootResourceStoreDelegate);
    }

    /**
     * Check whether the data is cached in memory
     * If not cached, query from the database
     * If cached , query from the memory
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     * @return
     */
    @Override
    public List<String> loadResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType) {
        // formatter key
        String resourceKey = ApiBootResourceContext.formatterCacheKey(declaredMethod, sourceFieldValue, resourceType);
        // get resource urls
        List<String> resourceUrls = RESOURCE_URLS.get(resourceKey);
        if (ObjectUtils.isEmpty(resourceUrls)) {
            // get resource urls from jdbc
            resourceUrls = super.loadResourceUrl(declaredMethod, sourceFieldValue, resourceType);
            // put resource
            RESOURCE_URLS.put(resourceKey, resourceUrls);
        }
        return resourceUrls;
    }

    /**
     * remove from memory
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     */
    @Override
    public void deleteResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType) {
        // remove from jdbc
        super.deleteResourceUrl(declaredMethod, sourceFieldValue, resourceType);
        // formatter key
        String resourceKey = ApiBootResourceContext.formatterCacheKey(declaredMethod, sourceFieldValue, resourceType);
        // remove memory resource urls
        RESOURCE_URLS.remove(resourceKey);
    }
}
