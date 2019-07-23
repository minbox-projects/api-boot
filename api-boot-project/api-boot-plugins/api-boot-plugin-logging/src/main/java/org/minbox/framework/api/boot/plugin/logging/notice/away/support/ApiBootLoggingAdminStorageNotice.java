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

package org.minbox.framework.api.boot.plugin.logging.notice.away.support;

import org.minbox.framework.api.boot.plugin.logging.ApiBootLog;
import org.minbox.framework.api.boot.plugin.logging.cache.LoggingCache;
import org.minbox.framework.api.boot.plugin.logging.notice.away.ApiBootLogStorageNotice;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ApiBoot Logging Admin Notice
 * report every request log to api-boot-logging-admin
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-19 15:03
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootLoggingAdminStorageNotice implements ApiBootLogStorageNotice {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootLoggingAdminStorageNotice.class);
    /**
     * Logging Cache
     */
    private LoggingCache loggingCache;

    public ApiBootLoggingAdminStorageNotice(LoggingCache loggingCache) {
        this.loggingCache = loggingCache;
    }

    /**
     * Cache Request Log
     *
     * @param apiBootLog ApiBoot Log
     */
    @Override
    public void notice(ApiBootLog apiBootLog) {
        loggingCache.cache(apiBootLog);
        logger.debug("Cache Request Logging Complete.");
    }
}
