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

package org.minbox.framework.api.boot.plugin.logging.cache.support;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.logging.ApiBootLog;
import org.minbox.framework.api.boot.plugin.logging.cache.LoggingCache;
import org.springframework.util.ObjectUtils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * ApiBoot Logging Memory Away Cache
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-21 13:50
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class LoggingMemoryCache implements LoggingCache {
    /**
     * Cache ApiBootLog Map
     * For Batch Report
     */
    private static final ConcurrentMap<String, ApiBootLog> CACHE_LOGS = new ConcurrentHashMap();

    /**
     * Cache Single ApiBootLog
     *
     * @param log ApiBootLog
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public void cache(ApiBootLog log) throws ApiBootException {
        if (!ObjectUtils.isEmpty(log)) {
            CACHE_LOGS.put(UUID.randomUUID().toString(), log);
        }
    }

    /**
     * Get Any One ApiBootLog
     *
     * @return ApiBootLog
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public ApiBootLog getAnyOne() throws ApiBootException {
        List<ApiBootLog> logs = get(0);
        return logs.size() > 0 ? logs.get(0) : null;
    }

    /**
     * Gets the specified number of ApiBootLog
     *
     * @param count get count number
     * @return ApiBootLog Collection
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public List<ApiBootLog> getLogs(int count) throws ApiBootException {
        if (CACHE_LOGS.size() >= count) {
            return get(count);
        }
        return null;
    }

    /**
     * Gets All Of ApiBootLog
     *
     * @return ApiBootLog Collection
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public List<ApiBootLog> getAll() throws ApiBootException {
        return get(null);
    }

    /**
     * Get ApiBootLogs
     *
     * @param count get count number
     * @return ApiBootLog Collection
     * @throws ApiBootException ApiBoot Exception
     */
    private List<ApiBootLog> get(Integer count) throws ApiBootException {
        List<ApiBootLog> logs = new ArrayList();
        Iterator<String> iterator = CACHE_LOGS.keySet().iterator();
        int index = 0;
        while (iterator.hasNext()) {
            String key = iterator.next();
            logs.add(CACHE_LOGS.get(key));
            CACHE_LOGS.remove(key);
            if (count != null && index >= count - 1) {
                break;
            }
            index++;
        }
        return logs;
    }
}
