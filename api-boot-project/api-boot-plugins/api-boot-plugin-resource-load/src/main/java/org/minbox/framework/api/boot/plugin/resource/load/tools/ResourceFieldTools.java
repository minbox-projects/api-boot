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

package org.minbox.framework.api.boot.plugin.resource.load.tools;

import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.context.ApiBootResourceContext;
import org.minbox.framework.api.boot.plugin.resource.load.expression.ResourceSourceExpression;
import org.minbox.framework.api.boot.plugin.resource.load.loader.ResourceFieldLoader;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

/**
 * Resource Tools
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-20 11:31
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ResourceFieldTools {
    /**
     * Get expression match value
     *
     * @param matchFieldName match field name
     * @param param          method param
     * @return match value
     */
    public static Object getMatchValue(String matchFieldName, Object[] param) {
        // pattern expression match contents
        List<String> matchContents = ResourceSourceExpression.getMatchContent(matchFieldName);

        // source field value
        Object sourceFieldValue = null;

        // don't match expression
        // default parameters using index 0
        if (ObjectUtils.isEmpty(matchContents)) {
            Object paramObject = param[0];
            // source field
            Field field = getField(paramObject.getClass(), matchFieldName);
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
                Field field = getField(paramObject.getClass(), matchContents.get(1));
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
     * @param param             method param array
     * @return resource field value
     */
    public static Object getMatchResourceValue(String resourceFieldName, Object[] param) {
        return getMatchValue(resourceFieldName, param);
    }

    /**
     * Get expression match source field value
     *
     * @param sourceFieldName ResourceField Annotation source
     * @param param           method param array
     * @return source field value
     */
    public static Object getMatchSourceValue(String sourceFieldName, Object[] param) {
        return getMatchValue(sourceFieldName, param);
    }

    /**
     * get Field value
     *
     * @param field  field instance
     * @param object field subordinate object
     * @return Field value
     */
    public static Object getFieldValue(Field field, Object object) {
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
     * get field instance
     *
     * @param objectClass method result object class
     * @param fieldName   field name
     * @return Field Instance
     */
    public static Field getField(Class objectClass, String fieldName) {
        // cache from memory
        Field sourceField = ApiBootResourceContext.getPushFieldFromCache(objectClass.getName(), fieldName);
        // if don't have source field from cache
        if (ObjectUtils.isEmpty(sourceField)) {
            sourceField = ReflectionUtils.findField(objectClass, fieldName);
            if (!sourceField.isAccessible()) {
                sourceField.setAccessible(true);
            }
            // cache to memory
            ApiBootResourceContext.setPushFieldToCache(objectClass.getName(), fieldName, sourceField);
        }
        return sourceField;
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
    public static List<ResourceField> getResourceFields(Method method) {
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
