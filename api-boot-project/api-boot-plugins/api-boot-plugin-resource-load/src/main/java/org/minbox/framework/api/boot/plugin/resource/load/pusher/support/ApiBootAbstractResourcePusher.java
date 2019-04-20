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
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.context.ApiBootResourceContext;
import org.minbox.framework.api.boot.plugin.resource.load.expression.ResourceSourceExpression;
import org.minbox.framework.api.boot.plugin.resource.load.loader.ResourceFieldLoader;
import org.minbox.framework.api.boot.plugin.resource.load.model.ResourcePushField;
import org.minbox.framework.api.boot.plugin.resource.load.pusher.ApiBootResourcePusher;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * ApiBoot Resource Pusher Abstract Support
 * Provide access to resource or source field
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-19 09:40
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public abstract class ApiBootAbstractResourcePusher implements ApiBootResourcePusher {

    /**
     * load resource url
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     * @return resource list
     */
    public abstract List<String> loadResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType);

    /**
     * delete resource urls
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     */
    public abstract void deleteResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType);

    /**
     * insert resource urls
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     * @param resourceUrls     resource urls
     */
    public abstract void insertResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType, List<String> resourceUrls);

    /**
     * update resource urls
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     * @param resourceUrls     resource urls
     */
    public abstract void updateResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType, List<String> resourceUrls);

    /**
     * unified pull resource
     *
     * @param declaredMethod declared method
     * @param param          method param array
     * @throws ApiBootException
     */
    @Override
    public void insertResource(Method declaredMethod, Object[] param) throws ApiBootException {
        // get method declared ResourceField annotations
        List<ResourceField> resourceFields = getResourceFields(declaredMethod);
        resourceFields.stream().forEach(resourceField -> {
            // get expression match source filed value
            Object sourceFieldValue = getMatchSourceValue(resourceField.source(), declaredMethod, param);
            // get expression match resource field value
            Object resourceFieldValue = getMatchResourceValue(resourceField.name(), declaredMethod, param);

            // if have value
            if (!ObjectUtils.isEmpty(sourceFieldValue) && !ObjectUtils.isEmpty(resourceFieldValue)) {
                List<String> resourceUrls = convertToList(resourceFieldValue);
                // call implementation class "deleteResourceUrl" method
                this.insertResourceUrl(declaredMethod, String.valueOf(sourceFieldValue), resourceField.type(), resourceUrls);
            }
        });
    }


    /**
     * unified delete resource
     *
     * @param declaredMethod declared method
     * @param param          method param array
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public void deleteResource(Method declaredMethod, Object[] param) throws ApiBootException {
        // get method declared ResourceField annotations
        List<ResourceField> resourceFields = getResourceFields(declaredMethod);
        resourceFields.stream().forEach(resourceField -> {
            // get expression match source filed value
            Object sourceFieldValue = getMatchSourceValue(resourceField.source(), declaredMethod, param);
            // if have value
            if (!ObjectUtils.isEmpty(sourceFieldValue)) {
                // call implementation class "deleteResourceUrl" method
                this.deleteResourceUrl(declaredMethod, String.valueOf(sourceFieldValue), resourceField.type());
            }
        });
    }

    /**
     * unified update resource
     *
     * @param declaredMethod declared method
     * @param param          method param array
     * @throws ApiBootException
     */
    @Override
    public void updateResource(Method declaredMethod, Object[] param) throws ApiBootException {
        // get method declared ResourceField annotations
        List<ResourceField> resourceFields = getResourceFields(declaredMethod);
        resourceFields.stream().forEach(resourceField -> {
            // get expression match source filed value
            Object sourceFieldValue = getMatchSourceValue(resourceField.source(), declaredMethod, param);
            // if have value
            if (!ObjectUtils.isEmpty(sourceFieldValue)) {
                // call implementation class "insertResourceUrl" method
                this.updateResourceUrl(declaredMethod, String.valueOf(sourceFieldValue), resourceField.type(), null);
            }
        });
    }

    /**
     * unified insert or update resource
     *
     * @param declaredMethod declared method
     * @param param          method param array
     * @throws ApiBootException
     */
    @Override
    public void insertOrUpdateResource(Method declaredMethod, Object[] param) throws ApiBootException {
        // TODO
    }

    /**
     * unified push resource
     *
     * @param method method
     * @param result method execute result
     */
    @Override
    public void pushResource(Method method, Object result) {
        // list
        if (result instanceof List) {
            pushToList(method, (List<Object>) result);
        }
        // map
        else if (result instanceof Map) {
            pushToMap(method, (Map) result);
        }
        // single
        else if (result instanceof Object) {
            pushToObject(method, result);
        }
    }

    /**
     * push resource url to list result
     *
     * @param method            method
     * @param executeResultList method execute result list
     * @throws Exception Exception
     */
    private void pushToList(Method method, List<Object> executeResultList) {
        List<ResourceField> resourceFields = getResourceFields(method);
        executeResultList.stream().forEach(o -> push(method, resourceFields, o));
    }

    /**
     * push resource url to map result
     *
     * @param method           method
     * @param executeResultMap method execute result map
     * @throws Exception Exception
     */
    private void pushToMap(Method method, Map executeResultMap) {
        List<ResourceField> resourceFields = getResourceFields(method);
        executeResultMap.keySet().stream().forEach(o -> push(method, resourceFields, executeResultMap.get(o)));
    }

    /**
     * push resource url to result object
     *
     * @param method        method
     * @param executeResult method execute result object
     */
    private void pushToObject(Method method, Object executeResult) {
        List<ResourceField> resourceFields = getResourceFields(method);
        push(method, resourceFields, executeResult);
    }

    /**
     * execute push
     *
     * @param resourceFields ResourceField Annotation List
     * @param object         single object
     */
    private void push(Method method, List<ResourceField> resourceFields, Object object) {
        Class objectClass = object.getClass();
        resourceFields.stream().forEach(resourceField -> {
            try {
                // source
                Field sourceField = getSourceField(method, objectClass, resourceField.source());
                // target
                Field resourceFieldInstance = getResourceField(method, objectClass, resourceField.name());

                // get source filed value
                Object sourceFieldValue = sourceField.get(object);
                // load resource urls
                List<String> resourceUrls = loadResourceUrl(method, String.valueOf(sourceFieldValue), resourceField.type());

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
     * Get expression match value
     *
     * @param matchFieldName match field name
     * @param declaredMethod declared method
     * @param param          method param
     * @return match value
     */
    protected Object getMatchValue(String matchFieldName, Method declaredMethod, Object[] param) {
        // pattern expression match contents
        List<String> matchContents = ResourceSourceExpression.getMatchContent(matchFieldName);

        // source field value
        Object sourceFieldValue = null;

        // don't match expression
        // default parameters using index 0
        if (ObjectUtils.isEmpty(matchContents)) {
            Object paramObject = param[0];
            // source field
            Field field = getSourceField(declaredMethod, paramObject.getClass(), matchFieldName);
            // source field value
            sourceFieldValue = getFieldValue(field, paramObject);
        }
        // match expression
        else {
            // ognl expression
            if (ResourceSourceExpression.getOgnlMatch(matchFieldName).find()) {
                // param object
                Object paramObject = param[Integer.valueOf(matchContents.get(0))];
                // source field
                Field field = getSourceField(declaredMethod, paramObject.getClass(), matchContents.get(1));
                // source field value
                sourceFieldValue = getFieldValue(field, paramObject);
            }
            // basic expression
            else if (ResourceSourceExpression.getBasicMatch(matchFieldName).find()) {
                sourceFieldValue = param[Integer.valueOf(matchContents.get(0))];
            }
        }
        return sourceFieldValue;
    }

    /**
     * Get expression match resource field value
     *
     * @param resourceFieldName ResourceField Annotation name
     * @param declaredMethod    declared method
     * @param param             method param array
     * @return resource field value
     */
    protected Object getMatchResourceValue(String resourceFieldName, Method declaredMethod, Object[] param) {
        return getMatchValue(resourceFieldName, declaredMethod, param);
    }

    /**
     * Get expression match source field value
     *
     * @param sourceFieldName ResourceField Annotation source
     * @param declaredMethod  declared method
     * @param param           method param array
     * @return source field value
     */
    protected Object getMatchSourceValue(String sourceFieldName, Method declaredMethod, Object[] param) {
        return getMatchValue(sourceFieldName, declaredMethod, param);
    }

    /**
     * get Field value
     *
     * @param field  field instance
     * @param object field subordinate object
     * @return
     */
    protected Object getFieldValue(Field field, Object object) {
        try {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            return field.get(object);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get source field
     *
     * @param method          method instance
     * @param sourceFieldName source field name
     * @return Field Instance
     * @throws NoSuchFieldException No Such Field Exception
     */
    protected Field getSourceField(Method method, Class objectClass, String sourceFieldName) {
        // cache from memory
        ResourcePushField resourcePushField = ApiBootResourceContext.getPushFieldFromCache(method, sourceFieldName);
        // if don't have source field from cache
        if (ObjectUtils.isEmpty(resourcePushField) || ObjectUtils.isEmpty(resourcePushField.getSourceField())) {
            Field sourceField = ReflectionUtils.findField(objectClass, sourceFieldName);
            if (!sourceField.isAccessible()) {
                sourceField.setAccessible(true);
            }
            if (ObjectUtils.isEmpty(resourcePushField)) {
                resourcePushField = ResourcePushField.builder().sourceField(sourceField).build();
            } else {
                resourcePushField.setSourceField(sourceField);
            }

            // cache to memory
            ApiBootResourceContext.setPushFieldToCache(method, sourceFieldName, resourcePushField);
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
    protected Field getResourceField(Method method, Class objectClass, String resourceFieldName) throws NoSuchFieldException {
        // cache from memory
        ResourcePushField resourcePushField = ApiBootResourceContext.getPushFieldFromCache(method, resourceFieldName);
        // if don't have source field from cache
        if (ObjectUtils.isEmpty(resourcePushField) || ObjectUtils.isEmpty(resourcePushField.getResourceField())) {
            Field resourceFieldInstance = ReflectionUtils.findField(objectClass, resourceFieldName);
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
    private List<ResourceField> loadMethodResourceFields(Method method) {
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
    protected List<ResourceField> getResourceFields(Method method) {
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

    protected List<String> convertToList(Object value) {
        List<String> resourceUrls = new ArrayList<>();
        if (value instanceof List) {
            resourceUrls.addAll((List) value);
        } else {
            resourceUrls.add(String.valueOf(value));
        }
        return resourceUrls;
    }
}
