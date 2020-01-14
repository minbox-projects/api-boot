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

package org.minbox.framework.api.boot.plugin.http.converter.filter.annotation;

import org.minbox.framework.api.boot.plugin.http.converter.filter.enums.ValueHidePositionEnum;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ApiBoot Value Hide Value Filter
 *
 * @author 恒宇少年
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ApiBootValueHide {
    /**
     * hide length
     *
     * @return number
     */
    int length() default 0;

    /**
     * start hide position
     *
     * @return start position
     */
    int start() default 0;

    /**
     * Enumeration of hidden locations
     * Start hiding after going (exclude value characters of excludeLength)
     * Start hiding from the back (exclude the value of excludeLength characters)
     *
     * @return {@link ValueHidePositionEnum}
     */
    ValueHidePositionEnum position() default ValueHidePositionEnum.MIDDLE;

    /**
     * Replaced hidden placeholders
     * the default value is "*"
     *
     * @return placeholder
     */
    String placeholder() default "*";
}
