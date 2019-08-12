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

package org.minbox.framework.api.boot.sample.mybatis.enhance.dsl;

import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.api.boot.sample.mybatis.enhance.entity.SystemUser;

/**
 * 系统用户信息表
 *
 * @author ApiBoot Mybatis Enhance Codegen
 */
public class DSystemUser extends TableExpression<SystemUser> {

    public DSystemUser(String root) {
        super(root);
    }

    public static DSystemUser DSL() {
        return new DSystemUser("iot_system_user");
    }


    /**
     * 主键
     */
    public ColumnExpression id = new ColumnExpression("SU_ID", this);
    /**
     * 用户名
     */
    public ColumnExpression userName = new ColumnExpression("SU_USER_NAME", this);
    /**
     * 用户昵称
     */
    public ColumnExpression nickName = new ColumnExpression("SU_NICK_NAME", this);
    /**
     * 年龄
     */
    public ColumnExpression age = new ColumnExpression("SU_AGE", this);
    /**
     * 用户密码
     */
    public ColumnExpression password = new ColumnExpression("SU_PASSWORD", this);
    /**
     * 用户状态，1：正常，0：冻结，-1：已删除
     */
    public ColumnExpression status = new ColumnExpression("SU_STATUS", this);
    /**
     * 创建时间
     */
    public ColumnExpression createTime = new ColumnExpression("SU_CREATE_TIME", this);
    /**
     * 备注信息
     */
    public ColumnExpression mark = new ColumnExpression("SU_MARK", this);

    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{id, userName, nickName, age, password, status, createTime, mark};
    }

}

