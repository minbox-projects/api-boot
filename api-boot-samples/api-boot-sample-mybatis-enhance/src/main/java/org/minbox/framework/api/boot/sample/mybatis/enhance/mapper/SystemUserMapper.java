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

package org.minbox.framework.api.boot.sample.mybatis.enhance.mapper;

import com.gitee.hengboy.mybatis.enhance.mapper.EnhanceMapper;
import org.apache.ibatis.annotations.Param;
import org.minbox.framework.api.boot.sample.mybatis.enhance.entity.SystemUser;

import java.util.List;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-09 22:15
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface SystemUserMapper extends EnhanceMapper<SystemUser, String> {
    /**
     * 根据userName查询单条记录
     *
     * @param userName
     * @return
     */
    SystemUser findByUserName(@Param("userName") String userName);

    /**
     * 根据userName and status 查询
     *
     * @param userName
     * @param userStatus
     * @return
     */
    SystemUser findByUserNameAndStatus(@Param("userName") String userName, @Param("status") Integer userStatus);

    /**
     * 根据status查询多条记录
     *
     * @param userStatus
     * @return
     */
    List<SystemUser> findByStatus(@Param("status") Integer userStatus);

    /**
     * 根据状态统计
     *
     * @param status
     * @return
     */
    Long countByStatus(@Param("status") Integer status);

    /**
     * 根据用户名 以及 状态统计
     *
     * @param userName
     * @param userStatus
     * @return
     */
    Long countByUserNameAndStatus(@Param("userName") String userName, @Param("status") Integer userStatus);

    /**
     * 根据状态删除
     *
     * @param status
     */
    void removeByStatus(@Param("status") Integer status);

    /**
     * 根据用户名 and 状态删除
     *
     * @param userName
     * @param userStatus
     */
    void removeByUserNameAndStatus(@Param("userName") String userName, @Param("status") Integer userStatus);
}
