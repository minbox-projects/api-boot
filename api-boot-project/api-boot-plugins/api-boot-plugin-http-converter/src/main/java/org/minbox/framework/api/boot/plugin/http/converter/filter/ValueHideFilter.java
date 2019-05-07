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

package org.minbox.framework.api.boot.plugin.http.converter.filter;

import com.alibaba.fastjson.serializer.ValueFilter;
import org.minbox.framework.api.boot.plugin.http.converter.filter.annotation.ApiBootValueHide;

import java.lang.reflect.Field;

/**
 * Value Hide Filter
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-15 17:44
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ValueHideFilter implements ValueFilter {
    /**
     * Execute hide value
     *
     * @param object object instance
     * @param name   field name
     * @param value  filed value
     * @return hidden values
     */
    @Override
    public Object process(Object object, String name, Object value) {
        if (object != null) {
            try {
                // get declared field
                Field field = object.getClass().getDeclaredField(name);
                if (field.isAnnotationPresent(ApiBootValueHide.class)) {
                    // field declared ApiBootValueHide Annotation
                    ApiBootValueHide valueHide = field.getDeclaredAnnotation(ApiBootValueHide.class);
                    // don't execute hide
                    if (valueHide.length() <= 0) {
                        return object;
                    }
                    // field value
                    String fieldValue = String.valueOf(value);
                    // convert to char array
                    char[] chars = fieldValue.toCharArray();

                    // hide with start position
                    if (valueHide.start() > 0) {
                        return startHide(valueHide, chars);
                    }
                    // hide with position wrapper
                    else {
                        return positionHide(valueHide, chars);
                    }

                }

            } catch (Exception e) {
                return value;
            }
        }
        return value;
    }

    /**
     * hide with start position
     *
     * @param valueHide ApiBootValueHide annotation
     * @param chars     field value char array
     * @return hidden values
     */
    String startHide(ApiBootValueHide valueHide, char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            if (valueHide.start() - 1 <= i && i < valueHide.start() + valueHide.length() - 1) {
                chars[i] = valueHide.placeholder().charAt(0);
            }
        }
        return new String(chars);
    }

    /**
     * hide with position wrapper
     *
     * @param valueHide ApiBootValueHide annotation
     * @param chars     field value char array
     * @return hidden values
     */
    String positionHide(ApiBootValueHide valueHide, char[] chars) {
        for (int i = 0; i < chars.length; i++) {
            switch (valueHide.position()) {
                // Back to back
                case START:
                    if (i < valueHide.length()) {
                        chars[i] = valueHide.placeholder().charAt(0);
                    }
                    break;
                // middle
                case MIDDLE:
                    // start hide position
                    int startPosition = chars.length / 2;
                    if (i >= startPosition - 1 && i < startPosition - 1 + valueHide.length()) {
                        chars[i] = valueHide.placeholder().charAt(0);
                    }
                    break;
                // From behind
                case END:
                    if (i >= chars.length - valueHide.length()) {
                        chars[i] = valueHide.placeholder().charAt(0);
                    }
                    break;
            }
        }
        return new String(chars);
    }
}
