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

package org.minbox.framework.api.boot.sample.mybatis.enhance.entity;

import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 系统用户角色关联表
 * @author ApiBoot Mybatis Enhance Codegen
 */
@Data
@Table(name = "iot_system_user_role")
public class SystemUserRole implements Serializable {

    /**
     * 主键自增
     */
    @Id(generatorType = KeyGeneratorTypeEnum.AUTO)
    @Column(name = "SUR_ID")
    private Integer id;
    /**
     * 角色编号，关联iot_system_role主键
     */
    @Column(name = "SUR_ROLE_ID")
    private String roleId;
    /**
     * 用户编号，关联iot_system_user主键
     */
    @Column(name = "SUR_USER_ID")
    private String userId;
    /**
     * 创建时间
     */
    @Column(name = "SUR_CREATE_TIME",insertable = false)
    private Timestamp createTime;
}

