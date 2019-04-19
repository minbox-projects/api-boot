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

package org.minbox.framework.api.boot.plugin.resource.load.context;

import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.model.ResourcePushField;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ApiBoot Resource Context
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-15 09:57
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootResourceContext {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootResourceContext.class);

    /**
     * key -> method name
     * value -> declared resource field annotation list
     */
    private static ConcurrentMap<String, List<ResourceField>> RESOURCE_FIELD_ANNOTATIONS = new ConcurrentHashMap();
    /**
     * key -> method name
     * child map key -> resource field name
     * child map value -> resource field instance
     */
    private static ConcurrentMap<String, Map<String, ResourcePushField>> RESOURCE_FIELDS = new ConcurrentHashMap();

    /**
     * get push field from cache
     *
     * @param method    method instance
     * @param fieldName 字段名称
     * @return ResourceField list
     */
    public static ResourcePushField getPushFieldFromCache(Method method, String fieldName) {
        String methodName = formatterMethodName(method);
        logger.debug("Cache method [{}] push field from memory", methodName);
        Map<String, ResourcePushField> resourcePushFieldMap = ApiBootResourceContext.RESOURCE_FIELDS.get(methodName);
        return ObjectUtils.isEmpty(resourcePushFieldMap) ? null : resourcePushFieldMap.get(fieldName);
    }

    /**
     * set push field to cache
     *
     * @param method            method instance
     * @param resourcePushField push fields
     */
    public static void setPushFieldToCache(Method method, String fieldName, ResourcePushField resourcePushField) {
        String methodName = formatterMethodName(method);
        logger.debug("Cache method [{}] push field to memory", methodName);
        Map<String, ResourcePushField> resourcePushFieldMap = ApiBootResourceContext.RESOURCE_FIELDS.get(methodName);
        // new map
        if (ObjectUtils.isEmpty(resourcePushFieldMap)) {
            resourcePushFieldMap = new HashMap(1) {
                {
                    put(fieldName, resourcePushField);
                }
            };
        }
        //already have map
        else {
            resourcePushFieldMap.put(fieldName, resourcePushField);
        }
        ApiBootResourceContext.RESOURCE_FIELDS.put(methodName, resourcePushFieldMap);
    }

    /**
     * get method resource field from cache
     *
     * @param method method instance
     * @return ResourceField list
     */
    public static List<ResourceField> getResourceFieldFromCache(Method method) {
        String methodName = formatterMethodName(method);
        logger.debug("Cache method [{}] resource field annotation from memory", methodName);
        return ApiBootResourceContext.RESOURCE_FIELD_ANNOTATIONS.get(methodName);
    }

    /**
     * set method resource field to cache
     *
     * @param method         method instance
     * @param resourceFields method resource field annotation
     */
    public static void setResourceFieldToCache(Method method, List<ResourceField> resourceFields) {
        String methodName = formatterMethodName(method);
        logger.debug("Cache method [{}] resource field annotation to memory", methodName);
        ApiBootResourceContext.RESOURCE_FIELD_ANNOTATIONS.put(methodName, resourceFields);
    }

    /**
     * formatter method name
     *
     * @param method method instance
     * @return method name
     */
    public static String formatterMethodName(Method method) {
        String expression = "%s.%s";
        return String.format(expression, method.getDeclaringClass().getName(), method.getName());
    }

    /**
     * formatter redis key
     *
     * @param method           method instance
     * @param sourceFieldValue source field value
     * @param resourceType     resource type
     * @return redis key
     */
    public static String formatterRedisCacheKey(Method method, String sourceFieldValue, String resourceType) {
        String methodName = formatterMethodName(method);
        String expression = "%s.%s.%s";
        return String.format(expression, methodName, resourceType, sourceFieldValue);
    }
}

