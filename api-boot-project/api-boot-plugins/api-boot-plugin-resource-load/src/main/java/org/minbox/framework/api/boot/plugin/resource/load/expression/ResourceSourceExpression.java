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

package org.minbox.framework.api.boot.plugin.resource.load.expression;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ApiBoot Resource Source Field Name Expression
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-19 14:47
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ResourceSourceExpression {
    /**
     * Source Name Basic Pattern
     */
    static final Pattern SOURCE_NAME_PATTERN_BASIC = Pattern.compile("#p(\\d+)");
    /**
     * Source Name Ognl Pattern
     */
    static final Pattern SOURCE_NAME_PATTERN_OGNL = Pattern.compile("#p(\\d+).(\\S+)");

    /**
     * If match basic pattern
     *
     * @param sourceFieldName ResourceField annotation source value
     * @return true: match , false: don't match
     * @see org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField
     */
    public static Matcher getBasicMatch(String sourceFieldName) {
        Matcher matcher = SOURCE_NAME_PATTERN_BASIC.matcher(sourceFieldName);
        return matcher;
    }

    /**
     * If match ognl pattern
     *
     * @param sourceFieldName ResourceField annotation source value
     * @return true: match , false: don't match
     * @see org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField
     */
    public static Matcher getOgnlMatch(String sourceFieldName) {
        Matcher matcher = SOURCE_NAME_PATTERN_OGNL.matcher(sourceFieldName);
        return matcher;
    }

    /**
     * get match context list
     *
     * @param sourceFieldName ResourceField annotation source value
     * @return match context list
     */
    public static List<String> getMatchContent(String sourceFieldName) {
        List<String> matchContents = new ArrayList<>();
        Matcher matcher = getOgnlMatch(sourceFieldName);
        boolean isMatch = matcher.find();
        // If don't match ognl expression
        if (!isMatch) {
            // use basic expression
            matcher = getBasicMatch(sourceFieldName);
            isMatch = matcher.find();
        }
        // If match basic expression
        if (isMatch) {
            // get all match value
            for (int i = 1; i <= matcher.groupCount(); i++) {
                matchContents.add(matcher.group(i));
            }
        }
        return matchContents;
    }
}
