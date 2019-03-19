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

package org.minbox.framework.api.boot.autoconfigure.security.web.userdetails;

import org.minbox.framework.api.boot.autoconfigure.security.web.delegate.ApiBootStoreDelegate;
import org.minbox.framework.api.boot.common.event.LoadUserEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * ApiBoot整合SpringSecurity用户查询逻辑实现
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 16:00
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootUserDetailsService implements UserDetailsService {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootUserDetailsService.class);
    /**
     * 注入上下文
     */
    @Autowired
    private ApplicationContext applicationContext;
    /**
     * ApiBoot数据委托类
     */
    @Autowired
    private ApiBootStoreDelegate apiBootStoreDelegate;

    /**
     * 根据用户名读取用户基本信息
     * <p>
     * 查询完成用户信息后执行发布LoadUserEvent事件
     *
     * @param username 用户名
     * @return 查询出该用户名的用户信息
     * @throws UsernameNotFoundException 用户未找到异常
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Login user：[{}]", username);
        UserDetails userDetails = apiBootStoreDelegate.loadUserByUsername(username);

        // publish loadUserEvent
        applicationContext.publishEvent(new LoadUserEvent(this, username));

        return userDetails;
    }
}
