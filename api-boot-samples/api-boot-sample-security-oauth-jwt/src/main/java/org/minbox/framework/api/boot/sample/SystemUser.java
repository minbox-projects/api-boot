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

package org.minbox.framework.api.boot.sample;

import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * 系统用户信息表
 * @author ApiBoot Mybatis Enhance Codegen
 */
@Data
@Table(name = "iot_system_user")
public class SystemUser implements Serializable {

    /**
     * 主键
     */
    @Id(generatorType = KeyGeneratorTypeEnum.UUID)
    @Column(name = "SU_ID")
    private String id;
    /**
     * 用户名
     */
    @Column(name = "SU_USER_NAME")
    private String userName;
    /**
     * 用户昵称
     */
    @Column(name = "SU_NICK_NAME")
    private String nickName;
    /**
     * 用户密码
     */
    @Column(name = "SU_PASSWORD")
    private String password;
    /**
     * 用户状态，1：正常，0：冻结，-1：已删除
     */
    @Column(name = "SU_STATUS")
    private Integer status = 1;
    /**
     * 创建时间
     */
    @Column(name = "SU_CREATE_TIME",insertable = false)
    private Timestamp createTime;
    /**
     * 备注信息
     */
    @Column(name = "SU_MARK")
    private String mark;
}

