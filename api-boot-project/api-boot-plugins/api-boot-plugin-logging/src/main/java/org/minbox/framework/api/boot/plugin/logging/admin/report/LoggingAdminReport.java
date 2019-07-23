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

package org.minbox.framework.api.boot.plugin.logging.admin.report;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.logging.ApiBootLog;
import org.springframework.beans.factory.InitializingBean;

import java.util.List;

/**
 * Batch Report Request Logs To Admin
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-21 13:46
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface LoggingAdminReport extends InitializingBean {
    /**
     * Report Request Logs To Admin
     * Loading a specified number of logs from the cache
     *
     * @throws ApiBootException ApiBoot Exception
     */
    void report() throws ApiBootException;

    /**
     * Report Specified Request Logs
     *
     * @param logs Request Logs
     * @throws ApiBootException ApiBoot Exception
     */
    void report(List<ApiBootLog> logs) throws ApiBootException;
}
