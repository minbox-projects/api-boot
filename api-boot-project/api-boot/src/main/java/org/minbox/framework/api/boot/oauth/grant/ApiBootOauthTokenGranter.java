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

package org.minbox.framework.api.boot.oauth.grant;

import org.minbox.framework.api.boot.oauth.exception.ApiBootTokenException;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Map;

/**
 * ApiBoot Integrates Oauth2 to Realize Custom Authorization to Acquire Token
 *
 * @author 恒宇少年
 */
public interface ApiBootOauthTokenGranter extends Serializable {
    /**
     * oauth2 grant type for ApiBoot
     *
     * @return grant type
     */
    String grantType();

    /**
     * load userDetails by parameter
     *
     * @param parameters parameter map
     * @return UserDetails
     * @throws ApiBootTokenException
     * @see UserDetails
     */
    UserDetails loadByParameter(Map<String, String> parameters) throws ApiBootTokenException;
}
