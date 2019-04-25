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

package org.minbox.framework.api.boot.autoconfigure.pageable;

import com.gitee.hengboy.mybatis.pageable.common.enums.DialectEnum;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Field;
import java.util.Properties;

import static org.minbox.framework.api.boot.autoconfigure.pageable.ApiBootMyBatisPageableProperties.API_BOOT_PAGEABLE_PREFIX;


/**
 * mybatis-pageable自动化配置属性
 *
 * @author：于起宇 ===============================
 * Created with IDEA.
 * Date：2018/8/4
 * Time：2:22 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_PAGEABLE_PREFIX)
public class ApiBootMyBatisPageableProperties {
    /**
     * mybatis pageable prefix
     */
    public static final String API_BOOT_PAGEABLE_PREFIX = "api.boot.pageable";
    /**
     * 数据库方言
     * 默认使用mysql数据库方言
     */
    private DialectEnum dialect = DialectEnum.MYSQL;

    /**
     * 获取属性配置
     *
     * @return 配置文件对象
     */
    public Properties getProperties() {

        // 返回的配置对象
        Properties properties = new Properties();
        /*
         * 获取本类内创建的field列表
         * 添加到配置对象集合内
         */
        Field[] fields = this.getClass().getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                // 数据库方言
                if ("dialect".equals(field.getName())) {
                    properties.setProperty(field.getName(), dialect.getValue().getName());
                } else {
                    properties.setProperty(field.getName(), String.valueOf(field.get(this)));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return properties;
    }
}
