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
 * ApiBoot提供的默认数据委托实现类
 * 注意：需要遵循ApiBoot的表名、表结构创建用户表
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 16:03
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootDefaultStoreDelegate implements ApiBootStoreDelegate {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootDefaultStoreDelegate.class);
    /**
     * 默认查询用户sql
     */
    static String DEFAULT_SELECT_USER_SQL = "SELECT UI_ID, UI_USER_NAME, UI_NICK_NAME, UI_PASSWORD, UI_EMAIL, UI_AGE, UI_ADDRESS, UI_IS_LOCKED, UI_IS_ENABLED, UI_STATUS, UI_CREATE_TIME FROM API_BOOT_USER_INFO WHERE UI_USER_NAME = ?";
    /**
     * 注入dataSource数据源对象
     */
    private DataSource dataSource;

    public ApiBootDefaultStoreDelegate(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return 用户对象信息
     * @throws UsernameNotFoundException 用户不存的异常跑出
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        ApiBootDefaultUserDetails userDetails = findUser(username);
        logger.debug("Load user ：{} complete.", username);
        return userDetails;
    }

    /**
     * 查询用户信息
     * 根据数据源的数据库链接进行执行查询用户信息
     *
     * @param username 用户名
     * @return 用户实例
     */
    private ApiBootDefaultUserDetails findUser(String username) {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet resultSet = null;
        try {
            connection = dataSource.getConnection();
            ps = connection.prepareStatement(DEFAULT_SELECT_USER_SQL);
            ps.setString(1, username);
            // 执行查询
            resultSet = ps.executeQuery();
            // 返回转换后的实体对象
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
     * Cleanup helper method that closes the given <code>ResultSet</code>
     * while ignoring any errors.
     *
     * @param connection 数据链接对象
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
     * Cleanup helper method that closes the given <code>ResultSet</code>
     * while ignoring any errors.
     *
     * @param rs restSet对象
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
     * @param statement Statement执行对象
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
     * 封装处理单个结果
     * <p>
     * 约定：返回对象的字段遵循首字母小写，"_"后第一个字母大写规则命名(对应列名)
     *
     * @param resultClass 返回对象类型
     * @param rs          查询结果集
     * @param <T>         返回对象泛型类型
     * @return 新对象
     */
    private <T> T wrapperOneResult(Class<T> resultClass, ResultSet rs) throws ApiBootException {
        Object resultObj = null;
        try {
            // 查询结果的元数据信息
            ResultSetMetaData metaData = rs.getMetaData();
            int columnCount = metaData.getColumnCount();

            while (rs.next()) {
                // 实例化返回对象
                resultObj = resultClass.newInstance();
                // 遍历元数据内的列信息，根据列名进行设置到返回对象的对应字段
                for (int index = 1; index < columnCount + 1; index++) {
                    // 列名
                    String columnName = metaData.getColumnName(index);
                    Object columnValue = rs.getObject(columnName);
                    if (columnValue != null) {
                        // 格式化后的字段名称
                        String fieldName = columnNameToFieldName(columnName);
                        Field field;
                        try {
                            // 获取本类定义的field
                            field = resultClass.getDeclaredField(fieldName);
                        } catch (NoSuchFieldException e) {
                            try {
                                // 获取父类定义的field
                                field = resultClass.getSuperclass().getDeclaredField(fieldName);
                            } catch (NoSuchFieldException e2) {
                                throw new ApiBootException("No such filed ： " + fieldName);
                            }
                        }
                        field.setAccessible(true);
                        // 设置字段的值
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
     * 列名转换Field的名称
     *
     * @param columnName 列名
     * @return Field名称
     */
    private String columnNameToFieldName(String columnName) {
        // 默认field为全小写的列名
        String fieldName = columnName.toLowerCase();
        // 返回转换驼峰后的字符串
        return CaseFormat.LOWER_UNDERSCORE.to(CaseFormat.LOWER_CAMEL, fieldName);
    }
}
