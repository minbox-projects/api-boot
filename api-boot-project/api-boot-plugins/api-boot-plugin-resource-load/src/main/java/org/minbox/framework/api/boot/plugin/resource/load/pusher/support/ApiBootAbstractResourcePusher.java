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
import org.minbox.framework.api.boot.common.tools.ListTools;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.pusher.ApiBootResourcePusher;
import org.minbox.framework.api.boot.plugin.resource.load.tools.ResourceFieldTools;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
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
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public void insertResource(Method declaredMethod, Object[] param) throws ApiBootException {
        // get method declared ResourceField annotations
        List<ResourceField> resourceFields = ResourceFieldTools.getResourceFields(declaredMethod);
        resourceFields.stream().forEach(resourceField -> {
            // get expression match source filed value
            Object sourceFieldValue = ResourceFieldTools.getMatchSourceValue(resourceField.source(), param);
            // get expression match resource field value
            Object resourceFieldValue = ResourceFieldTools.getMatchResourceValue(resourceField.name(), param);

            // if have value
            if (!ObjectUtils.isEmpty(sourceFieldValue) && !ObjectUtils.isEmpty(resourceFieldValue)) {
                List<String> resourceUrls = ListTools.convertToList(resourceFieldValue);
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
        List<ResourceField> resourceFields = ResourceFieldTools.getResourceFields(declaredMethod);
        resourceFields.stream().forEach(resourceField -> {
            // get expression match source filed value
            Object sourceFieldValue = ResourceFieldTools.getMatchSourceValue(resourceField.source(), param);
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
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public void updateResource(Method declaredMethod, Object[] param) throws ApiBootException {
        // get method declared ResourceField annotations
        List<ResourceField> resourceFields = ResourceFieldTools.getResourceFields(declaredMethod);
        resourceFields.stream().forEach(resourceField -> {
            // get expression match source filed value
            Object sourceFieldValue = ResourceFieldTools.getMatchSourceValue(resourceField.source(), param);
            // get expression match resource field value
            Object resourceFieldValue = ResourceFieldTools.getMatchResourceValue(resourceField.name(), param);
            // if have value
            if (!ObjectUtils.isEmpty(sourceFieldValue) && !ObjectUtils.isEmpty(resourceFieldValue)) {
                List<String> resourceUrls = ListTools.convertToList(resourceFieldValue);
                // call implementation class "insertResourceUrl" method
                this.updateResourceUrl(declaredMethod, String.valueOf(sourceFieldValue), resourceField.type(), resourceUrls);
            }
        });
    }

    /**
     * unified push resource
     *
     * @param method method
     * @param result method execute result
     */
    @Override
    public void loadResource(Method method, Object result) {
        // list
        if (result instanceof List) {
            pushToList(method, (List<Object>) result);
        }
        // map
        else if (result instanceof Map) {
            pushToMap(method, (Map) result);
        }
        // result is mybatis pageable
        /*else if (result instanceof Page) {
            Page page = (Page) result;
            pushToList(method, page.getData());
        }*/
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
        List<ResourceField> resourceFields = ResourceFieldTools.getResourceFields(method);
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
        List<ResourceField> resourceFields = ResourceFieldTools.getResourceFields(method);
        executeResultMap.keySet().stream().forEach(o -> push(method, resourceFields, executeResultMap.get(o)));
    }

    /**
     * push resource url to result object
     *
     * @param method        method
     * @param executeResult method execute result object
     */
    private void pushToObject(Method method, Object executeResult) {
        List<ResourceField> resourceFields = ResourceFieldTools.getResourceFields(method);
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
                Field sourceField = ResourceFieldTools.getField(objectClass, resourceField.source());
                // target
                Field resourceFieldInstance = ResourceFieldTools.getField(objectClass, resourceField.name());

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
}
