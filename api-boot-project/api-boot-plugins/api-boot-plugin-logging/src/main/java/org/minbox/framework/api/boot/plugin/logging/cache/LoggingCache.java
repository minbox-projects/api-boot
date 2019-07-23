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

package org.minbox.framework.api.boot.plugin.logging.cache;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.logging.ApiBootLog;

import java.util.List;

/**
 * ApiBoot Logging Cache
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-21 13:49
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface LoggingCache {
    /**
     * Cache Single ApiBootLog
     *
     * @param log ApiBootLog
     * @throws ApiBootException ApiBoot Exception
     */
    void cache(ApiBootLog log) throws ApiBootException;

    /**
     * Get Any One ApiBootLog
     *
     * @return ApiBootLog
     * @throws ApiBootException ApiBoot Exception
     */
    ApiBootLog getAnyOne() throws ApiBootException;

    /**
     * Gets the specified number of ApiBootLog
     *
     * @param count get count number
     * @return ApiBootLog Collection
     * @throws ApiBootException ApiBoot Exception
     */
    List<ApiBootLog> getLogs(int count) throws ApiBootException;

    /**
     * Gets All Of ApiBootLog
     *
     * @return ApiBootLog Collection
     * @throws ApiBootException ApiBoot Exception
     */
    List<ApiBootLog> getAll() throws ApiBootException;
}
