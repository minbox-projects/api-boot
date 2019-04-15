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

package org.minbox.framework.api.boot.plugin.resource.load.pusher;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.resource.load.ApiBootResourceStoreDelegate;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.context.ApiBootResourceContext;
import org.minbox.framework.api.boot.plugin.resource.load.loader.ResourceFieldLoader;
import org.minbox.framework.api.boot.plugin.resource.load.model.ResourcePushField;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;

/**
 * ApiBoot Resource Load Pusher
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-12 16:27
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ResourcePusher {
    /**
     * ApiBoot Resource Load Data Store
     * Use to query resource url
     */
    private static ApiBootResourceStoreDelegate resourceStoreDelegate;

    /**
     * unified push resource
     *
     * @param method method
     * @param result method execute result
     */
    public static void pushResource(ApiBootResourceStoreDelegate resourceStoreDelegate, Method method, Object result) {
        ResourcePusher.resourceStoreDelegate = resourceStoreDelegate;
        // list
        if (result instanceof List) {
            ResourcePusher.pushToList(method, (List<Object>) result);
        }
        // map
        else if (result instanceof Map) {
            ResourcePusher.pushToMap(method, (Map) result);
        }
        // single
        else if (result instanceof Object) {
            ResourcePusher.pushToObject(method, result);
        }
    }

    /**
     * push resource url to list result
     *
     * @param method            method
     * @param executeResultList method execute result list
     * @throws Exception
     */
    private static void pushToList(Method method, List<Object> executeResultList) {
        List<ResourceField> resourceFields = getResourceFields(method);
        executeResultList.stream().forEach(o -> push(method, resourceFields, o));
    }

    /**
     * push resource url to map result
     *
     * @param method           method
     * @param executeResultMap method execute result map
     * @throws Exception
     */
    private static void pushToMap(Method method, Map executeResultMap) {
        List<ResourceField> resourceFields = getResourceFields(method);
        executeResultMap.keySet().stream().forEach(o -> push(method, resourceFields, executeResultMap.get(o)));
    }

    /**
     * push resource url to result object
     *
     * @param method        method
     * @param executeResult method execute result object
     */
    private static void pushToObject(Method method, Object executeResult) {
        List<ResourceField> resourceFields = getResourceFields(method);
        push(method, resourceFields, executeResult);
    }


    /**
     * execute push
     *
     * @param resourceFields ResourceField Annotation List
     * @param object         single object
     */
    private static void push(Method method, List<ResourceField> resourceFields, Object object) {
        Class objectClass = object.getClass();
        resourceFields.stream().forEach(resourceField -> {
            try {
                // source
                Field sourceField = getSourceField(method, objectClass, resourceField.source(), resourceField.name());
                // target
                Field resourceFieldInstance = getResourceField(method, objectClass, resourceField.name());

                // get source filed value
                Object sourceFieldValue = sourceField.get(object);
                // load resource urls
                List<String> resourceUrls = resourceStoreDelegate.loadResourceUrl(String.valueOf(sourceFieldValue), resourceField.type());

                if (!ObjectUtils.isEmpty(resourceUrls)) {
                    // resource field is array
                    if (resourceField.isArray()) {
                        resourceFieldInstance.set(object, resourceUrls.toArray());
                    }
                    // resource field is list
                    else if (resourceField.isList()) {
                        resourceFieldInstance.set(object, resourceUrls);
                    }
                    // resource field is single string
                    else {
                        resourceFieldInstance.set(object, resourceUrls.get(0));
                    }
                }
            } catch (Exception e) {
                throw new ApiBootException("Push resource fail.", e);
            }

        });

    }

    /**
     * get source field
     *
     * @param method          method instance
     * @param sourceFieldName source field name
     * @return
     * @throws NoSuchFieldException
     */
    private static Field getSourceField(Method method, Class objectClass, String sourceFieldName,String resourceFieldName) throws NoSuchFieldException {
        // cache from memory
        ResourcePushField resourcePushField = ApiBootResourceContext.getPushFieldFromCache(method, resourceFieldName);
        // if don't have source field from cache
        if (ObjectUtils.isEmpty(resourcePushField) || ObjectUtils.isEmpty(resourcePushField.getSourceField())) {
            Field sourceField = objectClass.getDeclaredField(sourceFieldName);
            if (!sourceField.isAccessible()) {
                sourceField.setAccessible(true);
            }
            if (ObjectUtils.isEmpty(resourcePushField)) {
                resourcePushField = ResourcePushField.builder().sourceField(sourceField).build();
            } else {
                resourcePushField.setSourceField(sourceField);
            }

            // cache to memory
            ApiBootResourceContext.setPushFieldToCache(method, resourceFieldName, resourcePushField);
        }
        return resourcePushField.getSourceField();
    }

    /**
     * get resource field
     *
     * @param method            method instance
     * @param resourceFieldName resource field name
     * @return Field
     */
    private static Field getResourceField(Method method, Class objectClass, String resourceFieldName) throws NoSuchFieldException {
        // cache from memory
        ResourcePushField resourcePushField = ApiBootResourceContext.getPushFieldFromCache(method, resourceFieldName);
        // if don't have source field from cache
        if (ObjectUtils.isEmpty(resourcePushField) || ObjectUtils.isEmpty(resourcePushField.getResourceField())) {
            Field resourceFieldInstance = objectClass.getDeclaredField(resourceFieldName);
            if (!resourceFieldInstance.isAccessible()) {
                resourceFieldInstance.setAccessible(true);
            }
            if (ObjectUtils.isEmpty(resourcePushField)) {
                resourcePushField = ResourcePushField.builder().resourceField(resourceFieldInstance).build();
            } else {
                resourcePushField.setResourceField(resourceFieldInstance);
            }
            // cache to memory
            ApiBootResourceContext.setPushFieldToCache(method, resourceFieldName, resourcePushField);
        }

        return resourcePushField.getResourceField();
    }

    /**
     * Load method declared ResourceField Annotations
     *
     * @param method method
     * @return ResourceField List
     */
    private static List<ResourceField> loadMethodResourceFields(Method method) {
        // load method declared ResourceField Annotation List
        List<ResourceField> resourceFields = ResourceFieldLoader.getDeclaredResourceField(method);
        return resourceFields;
    }

    /**
     * get method declared resource field annotation
     * 1. get from cache
     * 2. loading from method declared
     *
     * @param method method
     * @return List ResourceField
     */
    private static List<ResourceField> getResourceFields(Method method) {
        // get from cache
        List<ResourceField> resourceFields = ApiBootResourceContext.getResourceFieldFromCache(method);

        // loading from method declared
        if (ObjectUtils.isEmpty(resourceFields)) {
            resourceFields = loadMethodResourceFields(method);
            // set to cache
            ApiBootResourceContext.setResourceFieldToCache(method, resourceFields);
        }
        return resourceFields;
    }
}
