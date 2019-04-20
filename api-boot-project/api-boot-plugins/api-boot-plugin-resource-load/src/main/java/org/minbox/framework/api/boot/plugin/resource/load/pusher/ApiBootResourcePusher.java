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

package org.minbox.framework.api.boot.plugin.resource.load.pusher;

import org.minbox.framework.api.boot.common.exception.ApiBootException;

import java.lang.reflect.Method;

/**
 * ApiBoot Resource Pusher Interface
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-19 09:32
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 * @see org.minbox.framework.api.boot.plugin.resource.load.pusher.support.ApiBootJdbcResourcePusher
 * @see org.minbox.framework.api.boot.plugin.resource.load.pusher.support.ApiBootRedisResourcePusher
 */
public interface ApiBootResourcePusher {
    /**
     * Push resource to result field
     *
     * @param declaredMethod      declared method
     * @param methodExecuteResult method execute result
     * @throws ApiBootException ApiBoot Exception
     */
    void loadResource(Method declaredMethod, Object methodExecuteResult) throws ApiBootException;

    /**
     * Pull resource from param
     *
     * @param declaredMethod declared method
     * @param param          method param array
     * @throws ApiBootException ApiBoot Exception
     */
    void insertResource(Method declaredMethod, Object[] param) throws ApiBootException;

    /**
     * Delete resource
     *
     * @param declaredMethod declared method
     * @param param          method param array
     * @throws ApiBootException ApiBoot Exception
     */
    void deleteResource(Method declaredMethod, Object[] param) throws ApiBootException;

    /**
     * Update resource
     *
     * @param declaredMethod declared method
     * @param param          method param array
     * @throws ApiBootException ApiBoot Exception
     */
    void updateResource(Method declaredMethod, Object[] param) throws ApiBootException;
}
