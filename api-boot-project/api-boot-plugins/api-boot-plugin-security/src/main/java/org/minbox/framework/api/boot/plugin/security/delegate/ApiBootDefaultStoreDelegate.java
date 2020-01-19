/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.minbox.framework.api.boot.plugin.security.delegate;

import com.google.common.base.CaseFormat;
import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.security.userdetails.ApiBootDefaultUserDetails;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.util.ObjectUtils;

import javax.sql.DataSource;
import java.lang.reflect.Field;
import java.sql.*;

/**
 * The {@link ApiBootStoreDelegate} default implement
 * <p>
 * Query {@link UserDetails} according to the agreed default table structure
 *
 * @author 恒宇少年
 */
public class ApiBootDefaultStoreDelegate implements ApiBootStoreDelegate {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootDefaultStoreDelegate.class);
    /**
     * Query {@link UserDetails} SQL by default
     *
     * @see ApiBootDefaultUserDetails
     * @see org.minbox.framework.api.boot.plugin.security.jdbc.ApiBootDefaultUserEntity
     */
    static String DEFAULT_SELECT_USER_SQL = "SELECT UI_ID, UI_USER_NAME, UI_NICK_NAME, UI_PASSWORD, UI_EMAIL, UI_AGE, UI_ADDRESS, UI_IS_LOCKED, UI_IS_ENABLED, UI_STATUS, UI_CREATE_TIME FROM API_BOOT_USER_INFO WHERE UI_USER_NAME = ?";
    /**
     * DataSource Instance
     * <p>
     * Get {@link Connection} read {@link UserDetails}
     */
    private DataSource dataSource;

    /**
     * Initialize {@link DataSource} with constructor
     *
     * @param dataSource {@link #dataSource}
     */
    public ApiBootDefaultStoreDelegate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Query {@link UserDetails}
     * <p>
     * Query the user information in the "api_boot_user_info" table by default
     *
     * @param username {@link UserDetails#getUsername()}
     * @return {@link ApiBootDefaultUserDetails}
     * @throws UsernameNotFoundException Throw the exception when the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiBootDefaultUserDetails userDetails = findUser(username);
        logger.debug("Load user ：{} complete.", username);
        return userDetails;
    }

    /**
     * Query User
     * <p>
     * Query user information according to database link of data source
     *
     * @param username {@link UserDetails#getUsername()}
     * @return {@link ApiBootDefaultUserDetails}
     */
    private ApiBootDefaultUserDetails findUser(String username) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(DEFAULT_SELECT_USER_SQL);
            ps.setString(1, username);
            resultSet = ps.executeQuery();
            return wrapperOneResult(ApiBootDefaultUserDetails.class, resultSet);
        } catch (Exception e) {
            throw new UsernameNotFoundException("Username：" + username + "，not found.");
        } finally {
            closeResultSet(resultSet);
            closeStatement(ps);
            closeConnection(connection);
        }
    }

    /**
     * Cleanup helper method that closes the given <code>{@link ResultSet}</code>
     * while ignoring any errors.
     *
     * @param connection {@link Connection}
     */
    private static void closeConnection(Connection connection) {
        if (null != connection) {
            try {
                connection.close();
            } catch (SQLException ignore) {
            }
        }
    }

    /**
     * Cleanup helper method that closes the given <code>{@link ResultSet}</code>
     * while ignoring any errors.
     *
     * @param rs {@link ResultSet}
     */
    private static void closeResultSet(ResultSet rs) {
        if (null != rs) {
            try {
                rs.close();
            } catch (SQLException ignore) {
            }
        }
    }

    /**
     * Cleanup helper method that closes the given <code>Statement</code>
     * while ignoring any errors.
     *
     * @param statement {@link Statement}
     */
    private static void closeStatement(Statement statement) {
        if (null != statement) {
            try {
                statement.close();
            } catch (SQLException ignore) {
            }
        }
    }

    /**
     * Encapsulation handles individual results
     * <p>
     * Convention: return the field of the object to follow the rule of lowercase first letter,
     * uppercase first letter after '?' (corresponding column name)
     *
     * @param resultClass Result Object Class
     * @param rs          {@link ResultSet}
     * @param <T>         generic types
     * @return Result Object Instance
     */
    private <T> T wrapperOneResult(Class<T> resultClass, ResultSet rs) throws ApiBootException {
        Object resultObj = null;
        try {
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                resultObj = resultClass.newInstance();
                for (int index = 1; index < columnCount + 1; index++) {
                    String columnName = metaData.getColumnName(index);
                    Object columnValue = rs.getObject(columnName);
                    if (columnValue != null) {
                        String fieldName = columnNameToFieldName(columnName);
                        Field field;
                        try {
                            field = resultClass.getDeclaredField(fieldName);
                        } catch (NoSuchFieldException e) {
                            try {
                                field = resultClass.getSuperclass().getDeclaredField(fieldName);
                            } catch (NoSuchFieldException e2) {
                                throw new ApiBootException("No such filed ： " + fieldName);
                            }
                        }
                        field.setAccessible(true);
                        field.set(resultObj, columnValue);
                    }
                }
            }
        } catch (Exception e) {
            throw new ApiBootException("Encapsulation result set object encounters exception information：" + e.getMessage(), e);
        }
        return ObjectUtils.isEmpty(resultObj) ? null : (T) resultObj;
    }

    /**
     * Column name converts the name of a field
     * for example：
     * "user_name" to "userName"
     *
     * @param columnName The column name
     * @return class field name
     */
    private String columnNameToFieldName(String columnName) {
        String fieldName = columnName.toLowerCase();
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldName);
    }
}
