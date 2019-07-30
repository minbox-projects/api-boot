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

import com.alibaba.fastjson.JSON;
import org.minbox.framework.api.boot.plugin.logging.ApiBootLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.UUID;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-22 17:01
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class LoggingDataSourceStorage implements LoggingStorage {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingDataSourceStorage.class);
    /**
     * Insert ServiceDetail SQL
     */
    private static final String SQL_INSERT_SERVICE_DETAILS = "insert into logging_service_details (lsd_id, lsd_service_id, lsd_service_ip, lsd_service_port) values (?,?,?,?)";
    /**
     * Select ServiceDetails Id SQL
     */
    private static final String SQL_SELECT_SERVICE_DETAILS_ID = "select lsd_id from logging_service_details where lsd_service_id = ? and lsd_service_ip = ? and lsd_service_port = ? limit 1";
    /**
     * Update ServiceDetail Last Report Time SQL
     */
    private static final String SQL_UPDATE_LAST_REPORT_SERVICE_DETAILS = "update logging_service_details set lsd_last_report_time = ? where lsd_id = ?";
    /**
     * Insert Request Log SQL
     */
    private static final String SQL_INSERT_LOG = "insert into logging_request_logs (lrl_id, lrl_service_detail_id, lrl_trace_id, lrl_parent_span_id, lrl_span_id,\n" +
            "                                  lrl_start_time, lrl_end_time, lrl_http_status, lrl_request_body, lrl_request_headers,\n" +
            "                                  lrl_request_ip, lrl_request_method, lrl_request_uri, lrl_response_body,\n" +
            "                                  lrl_response_headers, lrl_time_consuming,lrl_request_params,lrl_exception_stack) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";

    private DataSource dataSource;

    public LoggingDataSourceStorage(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Insert Request Log
     *
     * @param serviceDetailId ServiceDetail ID
     * @param log             ApiBootLog
     * @throws SQLException SqlException
     */
    @Override
    public void insertLog(String serviceDetailId, ApiBootLog log) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_INSERT_LOG);
        ps.setString(1, UUID.randomUUID().toString());
        ps.setString(2, serviceDetailId);
        ps.setString(3, log.getTraceId());
        ps.setString(4, log.getParentSpanId());
        ps.setString(5, log.getSpanId());
        ps.setLong(6, log.getStartTime());
        ps.setLong(7, log.getEndTime());
        ps.setInt(8, log.getHttpStatus());
        ps.setString(9, log.getRequestBody());
        ps.setString(10, JSON.toJSONString(log.getRequestHeaders()));
        ps.setString(11, log.getRequestIp());
        ps.setString(12, log.getRequestMethod());
        ps.setString(13, log.getRequestUri());
        ps.setString(14, log.getResponseBody());
        ps.setString(15, JSON.toJSONString(log.getResponseHeaders()));
        ps.setLong(16, log.getTimeConsuming());
        ps.setString(17, log.getRequestParam());
        ps.setString(18, log.getExceptionStack());
        ps.executeUpdate();
        ps.close();
        closeConnection(connection);
    }

    /**
     * Insert ServiceDetails
     *
     * @param serviceId   ServiceId
     * @param serviceIp   Service Ip Address
     * @param servicePort ServicePort
     * @return ServiceDetails Pk Value
     * @throws SQLException Sql Exception
     */
    @Override
    public String insertServiceDetail(String serviceId, String serviceIp, int servicePort) throws SQLException {
        String serviceDetailId = UUID.randomUUID().toString();
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_INSERT_SERVICE_DETAILS);
        ps.setString(1, serviceDetailId);
        ps.setString(2, serviceId);
        ps.setString(3, serviceIp);
        ps.setInt(4, servicePort);
        ps.executeUpdate();
        ps.close();
        closeConnection(connection);
        return serviceDetailId;
    }

    /**
     * Select ServiceDetails Id
     *
     * @param serviceId   Service Id
     * @param serviceIp   Service Ip Address
     * @param servicePort Service Port
     * @return ServiceDetail Id
     * @throws SQLException Sql Exception
     */
    @Override
    public String selectServiceDetailId(String serviceId, String serviceIp, int servicePort) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_SELECT_SERVICE_DETAILS_ID);
        ps.setString(1, serviceId);
        ps.setString(2, serviceIp);
        ps.setInt(3, servicePort);
        ResultSet rs = ps.executeQuery();
        String serviceDetailId = null;
        while (rs.next()) {
            serviceDetailId = rs.getString(1);
            break;
        }
        rs.close();
        ps.close();
        closeConnection(connection);
        return serviceDetailId;
    }

    /**
     * Update ServiceDetails Last Report Time
     *
     * @param serviceDetailId ServiceDetail Pk Value
     * @throws SQLException Sql Exception
     */
    @Override
    public void updateLastReportTime(String serviceDetailId) throws SQLException {
        Connection connection = getConnection();
        PreparedStatement ps = connection.prepareStatement(SQL_UPDATE_LAST_REPORT_SERVICE_DETAILS);
        ps.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
        ps.setString(2, serviceDetailId);
        ps.executeUpdate();
        ps.close();
        closeConnection(connection);
    }

    /**
     * Get DataSource Connection
     *
     * @return Connection
     * @throws SQLException Sql Exception
     */
    Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    /**
     * Close DataSource Connection
     *
     * @param connection DataSource Connection
     * @throws SQLException Sql Exception
     */
    void closeConnection(Connection connection) throws SQLException {
        connection.close();
    }
}
