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

package org.minbox.framework.api.boot.plugin.logging.admin.storage;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.logging.ApiBootLog;

import java.sql.SQLException;
import java.util.List;

/**
 * ApiBoot Logging Storage
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-22 17:00
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface LoggingStorage {
    /**
     * Insert ApiBootLogs To DataBase
     *
     * @param serviceDetailId ServiceDetail ID
     * @param log             ApiBootLog
     * @throws SQLException Sql Exception
     */
    void insertLog(String serviceDetailId, ApiBootLog log) throws SQLException;

    /**
     * Insert ServiceDetail To DataBase
     *
     * @param serviceId   ServiceId
     * @param serviceIp   Service Ip Address
     * @param servicePort ServicePort
     * @return ServiceDetail Pk Value
     * @throws SQLException Sql Exception
     */
    String insertServiceDetail(String serviceId, String serviceIp, int servicePort) throws SQLException;

    /**
     * Select ServiceDetails Id
     *
     * @param serviceId   Service Id
     * @param serviceIp   Service Ip Address
     * @param servicePort Service Port
     * @return ServiceDetail Id
     * @throws SQLException Sql Exception
     */
    String selectServiceDetailId(String serviceId, String serviceIp, int servicePort) throws SQLException;

    /**
     * Update ServiceDetail Last Report Time
     *
     * @param serviceDetailId ServiceDetail Pk Value
     * @throws SQLException Sql Exception
     */
    void updateLastReportTime(String serviceDetailId) throws SQLException;
}
