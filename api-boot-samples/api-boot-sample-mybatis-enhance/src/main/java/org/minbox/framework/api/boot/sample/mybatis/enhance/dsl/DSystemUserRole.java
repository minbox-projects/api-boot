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
import org.minbox.framework.api.boot.sample.mybatis.enhance.entity.SystemUserRole;

/**
 * 系统用户角色关联表
 * @author ApiBoot Mybatis Enhance Codegen
 */
public class DSystemUserRole extends TableExpression<SystemUserRole> {

    public DSystemUserRole(String root) {
        super(root);
    }

    public static DSystemUserRole DSL() {
        return new DSystemUserRole("iot_system_user_role");
    }

    /**
     * 主键自增
     */
    public ColumnExpression id = new ColumnExpression("SUR_ID", this);
    /**
     * 角色编号，关联iot_system_role主键
     */
    public ColumnExpression roleId = new ColumnExpression("SUR_ROLE_ID", this);
    /**
     * 用户编号，关联iot_system_user主键
     */
    public ColumnExpression userId = new ColumnExpression("SUR_USER_ID", this);
    /**
     * 创建时间
     */
    public ColumnExpression createTime = new ColumnExpression("SUR_CREATE_TIME", this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{id, roleId, userId, createTime};
    }

}

