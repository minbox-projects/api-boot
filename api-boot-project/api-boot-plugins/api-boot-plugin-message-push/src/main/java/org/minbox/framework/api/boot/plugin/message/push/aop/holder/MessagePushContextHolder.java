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

package org.minbox.framework.api.boot.plugin.message.push.aop.holder;

import org.springframework.util.Assert;

/**
 * Message Push Context Holer
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-20 16:33
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class MessagePushContextHolder {
    /**
     * current thread data source pool name
     */
    private static ThreadLocal<String> MESSAGE_PUSH_CLIENT_NAME = new ThreadLocal();

    /**
     * setting current thread pool name
     *
     * @param dataSourcePoolName datasource pool name
     */
    public static void set(String dataSourcePoolName) {
        Assert.notNull(dataSourcePoolName, "DataSource pool name is required.");
        MESSAGE_PUSH_CLIENT_NAME.set(dataSourcePoolName);
    }

    /**
     * get current thread pool name
     *
     * @return data source pool name
     */
    public static String get() {
        return MESSAGE_PUSH_CLIENT_NAME.get();
    }

    /**
     * remove current thread pool name
     */
    public static void remove() {
        MESSAGE_PUSH_CLIENT_NAME.remove();
    }
}
