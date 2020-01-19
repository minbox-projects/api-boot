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

package org.minbox.framework.api.boot.plugin.security.userdetails;

import org.minbox.framework.api.boot.common.event.LoadUserEvent;
import org.minbox.framework.api.boot.plugin.security.delegate.ApiBootStoreDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * The {@link UserDetailsService} implement class
 * <p>
 * ApiBoot integrates spring security user query logic implementation
 *
 * @author 恒宇少年
 */
public class ApiBootUserDetailsService implements UserDetailsService {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootUserDetailsService.class);
    /**
     * Spring {@link ApplicationContext}
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Query basic user information according to user name
     * <p>
     * Publish load user event event event after querying user information
     *
     * @param username {@link UserDetails#getUsername()}
     * @return {@link UserDetails}
     * @throws UsernameNotFoundException Throw the exception when the user does not exist
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.info("Login user：[{}]", username);

        // find ApiBootStoreDelegate support instance
        // default is org.minbox.framework.api.boot.plugin.security.delegate.ApiBootDefaultStoreDelegate
        ApiBootStoreDelegate apiBootStoreDelegate = applicationContext.getBean(ApiBootStoreDelegate.class);
        UserDetails userDetails = apiBootStoreDelegate.loadUserByUsername(username);

        // publish loadUserEvent
        applicationContext.publishEvent(new LoadUserEvent(this, username));

        return userDetails;
    }
}
