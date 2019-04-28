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

package org.minbox.framework.api.boot.sample.mybatis.enhance;

import com.gitee.hengboy.mybatis.enhance.dsl.factory.EnhanceDslFactory;
import com.gitee.hengboy.mybatis.enhance.dsl.update.filter.SetFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-27 12:35
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
public class UserService {
    /**
     * Mybatis Enhance Dsl Factory
     */
    @Autowired
    private EnhanceDslFactory dslFactory;

    /**
     * 示例：动态条件查询用户信息
     *
     * @param userId 用户编号
     * @return 用户基本信息
     */
    public UserEntity dynamicSelectOne(String userId) {

        DUserEntity dUserEntity = DUserEntity.DSL();

        return dslFactory.createSearchable()
                .selectFrom(dUserEntity)
                .where(dUserEntity.uiId.eq(userId))
                .resultType(UserEntity.class)
                .fetchOne();
    }

    /**
     * 示例：动态更新手机号
     *
     * @param userId 用户编号
     * @param phone  手机号码
     */
    public void dynamicUpdateAge(String userId, String phone) {
        DUserEntity dUserEntity = DUserEntity.DSL();
        dslFactory.createUpdateable()
                .update(dUserEntity)
                .set(SetFilter.set(dUserEntity.uiPhone, phone))
                .where(dUserEntity.uiId.eq(userId))
                .execute();
    }

    /**
     * 实例：动态根据手机号、用户编号删除
     *
     * @param userId 用户编号
     * @param phone  手机号
     */
    public void dynamicDeleteUser(String userId, String phone) {
        DUserEntity dUserEntity = DUserEntity.DSL();
        dslFactory.createDeleteable()
                .delete(dUserEntity)
                .where(dUserEntity.uiPhone.eq(phone))
                .and(dUserEntity.uiId.eq(userId))
                .execute();
    }
}
