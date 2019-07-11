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

package org.minbox.framework.api.boot.sample.mybatis.enhance.service;

import com.gitee.hengboy.mybatis.enhance.dsl.factory.EnhanceDsl;
import com.gitee.hengboy.mybatis.enhance.dsl.factory.EnhanceDslFactory;
import com.gitee.hengboy.mybatis.enhance.dsl.serach.Searchable;
import com.gitee.hengboy.mybatis.enhance.dsl.update.filter.SetFilter;
import com.gitee.hengboy.mybatis.enhance.sort.SortEnum;
import org.junit.Test;
import org.minbox.framework.api.boot.sample.mybatis.enhance.dsl.DSystemUser;
import org.minbox.framework.api.boot.sample.mybatis.enhance.dsl.DSystemUserRole;
import org.minbox.framework.api.boot.sample.mybatis.enhance.dto.SystemUserDTO;
import org.minbox.framework.api.boot.sample.mybatis.enhance.entity.SystemUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-09 22:17
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class SystemUserService {
    /**
     * ApiBoot Enhance 动态工厂
     */
    @Autowired
    private EnhanceDslFactory dslFactory;

    /**
     * 多表关联查询
     *
     * @return
     * @throws Exception
     */
    public List<SystemUser> manyTableJoin() throws Exception {
        // 系统用户动态实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        // 系统用户角色关联动态实体
        DSystemUserRole dSystemUserRole = DSystemUserRole.DSL();
        // 执行查询并且返回SystemUser类型对象
        return dslFactory.createSearchable()
                .selectFrom(dSystemUser)
                .leftJoin(dSystemUser.id, dSystemUserRole.userId)
                .where(dSystemUserRole.roleId.eq("367c8078-a1f1-11e9-9b7e-3417eb9c0f80"))
                .and(dSystemUser.status.eq(1))
                .resultType(SystemUser.class)
                .fetch();

    }

    /**
     * 自定义返回类型
     *
     * @return
     */
    public List<SystemUserDTO> diyResultType() {
        DSystemUser dSystemUser = DSystemUser.DSL();
        return dslFactory.createSearchable()
                .select(dSystemUser.id, dSystemUser.userName, dSystemUser.nickName)
                .from(dSystemUser)
                .where(dSystemUser.status.eq(1))
                .resultType(SystemUserDTO.class)
                .fetch();
    }

    /**
     * 组装查询
     *
     * @return
     */
    public List<SystemUser> assembleQuery(boolean isJoin) {
        // 系统用户动态查询对象
        DSystemUser dSystemUser = DSystemUser.DSL();
        // 创建Searchable查询对象
        Searchable searchable = dslFactory.createSearchable().selectFrom(dSystemUser)
                .where(dSystemUser.status.eq(1));
        // 根据条件组装关联查询
        if (isJoin) {
            // 系统用户角色关联查询对象
            DSystemUserRole dSystemUserRole = DSystemUserRole.DSL();
            searchable.leftJoin(dSystemUser.id, dSystemUserRole.userId);
        }
        // 设置返回值类型 & 查询数据
        return searchable.resultType(SystemUser.class).fetch();
    }

    /**
     * 数据统计
     *
     * @return
     */
    public Long count() {
        // 系统用户动态查询实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        // 系统用户角色动态查询实体
        DSystemUserRole dSystemUserRole = DSystemUserRole.DSL();
        return dslFactory.createSearchable()
                // 建议使用主键统计，主键索引效率会有显著提升
                .count(dSystemUser.id)
                .from(dSystemUser)
                .leftJoin(dSystemUser.id, dSystemUserRole.userId)
                .where(dSystemUser.status.eq(1))
                // 统计的结果类型
                .resultType(Long.class)
                // 查询单个结果
                .fetchOne();
    }

    /**
     * 平均值
     *
     * @return
     */
    public Integer avg() {
        // 系统用户动态查询实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        return dslFactory.createSearchable()
                // 查询用户年龄平均值
                .avg(dSystemUser.age)
                .from(dSystemUser)
                // 用户状态为1
                .where(dSystemUser.status.eq(1))
                // 返回值类型为Integer，根据age对应表内字段而定
                .resultType(Integer.class)
                // 查询单个结果
                .fetchOne();
    }

    /**
     * 总和
     *
     * @return
     */
    public Long sum() {
        // 系统用户动态查询实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        return dslFactory.createSearchable()
                // 查询用户年龄总和
                .sum(dSystemUser.age)
                .from(dSystemUser)
                // 用户状态为1
                .where(dSystemUser.status.eq(1))
                // 返回值类型为Integer，根据age对应表内字段而定
                .resultType(Long.class)
                // 查询单个结果
                .fetchOne();
    }

    /**
     * 最小值
     *
     * @return
     */
    public Integer min() {
        // 系统用户动态查询实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        return dslFactory.createSearchable()
                // 查询最小年龄的用户
                .min(dSystemUser.age)
                .from(dSystemUser)
                // 用户状态为1
                .where(dSystemUser.status.eq(1))
                // 返回值类型为Integer，根据age对应表内字段而定
                .resultType(Integer.class)
                // 查询单个结果
                .fetchOne();
    }

    /**
     * 查询最大值
     *
     * @return
     */
    public Integer max() {
        // 系统用户动态查询实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        return dslFactory.createSearchable()
                // 查询最小年龄的用户
                .max(dSystemUser.age)
                .from(dSystemUser)
                // 用户状态为1
                .where(dSystemUser.status.eq(1))
                // 返回值类型为Integer，根据age对应表内字段而定
                .resultType(Integer.class)
                // 查询单个结果
                .fetchOne();
    }

    /**
     * 动态更新
     */
    public void dslUpdate() {
        // 系统用户动态实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        dslFactory.createUpdateable()
                // 需要更新的表对应的动态实体
                .update(dSystemUser)
                // 更新age对应列值 = 25
                .set(SetFilter.set(dSystemUser.age, 25))
                // 更新mark对应列值 = 备注信息
                .set(SetFilter.set(dSystemUser.mark, "备注信息"))
                // 指定更新用户
                .where(dSystemUser.id.eq("58eea57e-a1f1-11e9-9b7e-3417eb9c0f80"))
                // 执行更新
                .execute();
    }

    /**
     * 动态删除
     */
    public void dslDelete() {
        // 系统用户动态实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        dslFactory.createDeleteable()
                // 删除动态实体对应表内的数据
                .delete(dSystemUser)
                // status对应列值 = 1
                .where(dSystemUser.status.eq(1))
                // age对应列值 > 25
                .and(dSystemUser.age.gt(25))
                // 执行删除
                .execute();
    }

    /**
     * 排序
     */
    public List<SystemUser> order() {
        // 系统用户动态实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        return dslFactory.createSearchable()
                .selectFrom(dSystemUser)
                // 查询status字段对应列值 = 1
                .where(dSystemUser.status.eq(1))
                // 根据age字段对应列值，正序排序，年龄从小到大排序
                .orderBy(dSystemUser.age, SortEnum.ASC)
                .resultType(SystemUser.class)
                .fetch();
    }

    /**
     * 分页查询
     *
     * @return
     */
    public List<SystemUser> page() {
        // 系统用户动态实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        return dslFactory.createSearchable()
                .selectFrom(dSystemUser)
                // 查询status字段对应列值 = 1
                .where(dSystemUser.status.eq(1))
                // 根据age字段对应列值，正序排序，年龄从小到大排序
                .orderBy(dSystemUser.age, SortEnum.ASC)
                // 分页的开始位置，0是第一条
                .offset(0)
                // 每页查询20条
                .limit(20)
                .resultType(SystemUser.class)
                .fetch();
    }

    /**
     * 分组
     *
     * @return
     */
    public List<SystemUser> group() {
        // 系统用户动态实体
        DSystemUser dSystemUser = DSystemUser.DSL();
        return dslFactory.createSearchable()
                // 查询分组的字段
                // 最小年龄
                .select(dSystemUser.userName, dSystemUser.age.min())
                .from(dSystemUser)
                // 查询status字段对应列值 = 1
                .where(dSystemUser.status.eq(1))
                // 根据userName字段对应的列分组
                .groupBy(dSystemUser.userName)
                .resultType(SystemUser.class)
                .fetch();
    }
}
