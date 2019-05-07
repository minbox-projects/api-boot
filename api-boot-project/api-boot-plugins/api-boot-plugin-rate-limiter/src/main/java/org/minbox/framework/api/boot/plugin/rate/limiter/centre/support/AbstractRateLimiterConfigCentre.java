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

package org.minbox.framework.api.boot.plugin.rate.limiter.centre.support;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.rate.limiter.centre.RateLimiterConfigCentre;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.StringReader;
import java.util.Enumeration;
import java.util.Properties;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-05-06 18:02
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public abstract class AbstractRateLimiterConfigCentre implements RateLimiterConfigCentre {

    /**
     * data-id
     */
    public static final String DATA_ID = "apiboot-rate-limiter-config";
    /**
     * default qps
     */
    public static final Long DEFAULT_QPS = 0L;
    /**
     * config properties key and value split
     */
    public static final String PROPERTIES_KEY_VALUE_SPLIT = "=";

    /**
     * data convert to properties
     *
     * @param content config data content
     * @return Properties
     */
    protected Properties toProperties(String content) throws ApiBootException {
        try {
            Properties properties = new Properties();
            if (!StringUtils.isEmpty(content)) {
                properties.load(new StringReader(content));
            }
            return properties;
        } catch (IOException e) {
            throw new ApiBootException(e.getMessage(), e);
        }
    }

    /**
     * properties convert to string
     *
     * @param properties config properties
     * @return config string
     * @throws ApiBootException ApiBoot Exception
     */
    protected String fromProperties(Properties properties) throws ApiBootException {
        Enumeration enumeration = properties.propertyNames();
        StringBuffer buffer = new StringBuffer();
        while (enumeration.hasMoreElements()) {
            String propertyKey = String.valueOf(enumeration.nextElement());
            String propertyValue = properties.getProperty(propertyKey);
            buffer.append(propertyKey);
            buffer.append(PROPERTIES_KEY_VALUE_SPLIT);
            buffer.append(propertyValue);
            buffer.append("\n");
        }
        return buffer.toString();
    }

    /**
     * format property key
     *
     * @param requestUri request uri
     * @return property key
     */
    protected String formatPropertyKey(String requestUri) {
        return requestUri.replaceAll("/", ".");
    }
}
