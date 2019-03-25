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

package org.minbox.framework.api.boot.plugin.security.jdbc;

import lombok.Data;

import java.sql.Timestamp;

/**
 * Created with Code-Builder.
 * 表名: api_boot_user_info - ApiBoot默认的用户信息表 - 数据实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：Mar 12, 2019 11:44:15 AM
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
public class ApiBootDefaultUserEntity {
    /**
     * UI_ID - 用户编号，主键自增
     */
    private Integer uiId;
    /**
     * UI_USER_NAME - 用户名
     */
    private String uiUserName;
    /**
     * UI_NICK_NAME - 用户昵称
     */
    private String uiNickName;
    /**
     * UI_PASSWORD - 用户密码
     */
    private String uiPassword;
    /**
     * UI_EMAIL - 用户邮箱地址
     */
    private String uiEmail;
    /**
     * UI_AGE - 用户年龄
     */
    private Integer uiAge;
    /**
     * UI_ADDRESS - 用户地址
     */
    private String uiAddress;
    /**
     * UI_IS_LOCKED - 是否锁定
     */
    private String uiIsLocked;
    /**
     * UI_IS_ENABLED - 是否启用
     */
    private String uiIsEnabled;
    /**
     * UI_STATUS - O：正常，D：已删除
     */
    private String uiStatus;
    /**
     * UI_CREATE_TIME - 用户创建时间
     */
    private Timestamp uiCreateTime;
}
