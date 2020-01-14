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

package org.minbox.framework.api.boot.plugin.tools;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.util.ObjectUtils;

/**
 * json tools
 *
 * @author 恒宇少年
 */
public class JsonTools {
    /**
     * beautify object to json
     *
     * @param object object
     * @return object json string
     */
    public static String beautifyJson(Object object) {
        if (ObjectUtils.isEmpty(object)) {
            return null;
        }
        return JSON.toJSONString(object,
            SerializerFeature.PrettyFormat,
            SerializerFeature.SortField,
            SerializerFeature.MapSortField
        );
    }

    /**
     * beautify string to json
     *
     * @param json json string
     * @return after beautify json
     */
    public static String beautifyJson(String json) {
        if (ObjectUtils.isEmpty(json)) {
            return json;
        }
        return JSON.toJSONString(
            JSONObject.parse(json),
            SerializerFeature.PrettyFormat
        );
    }
}
