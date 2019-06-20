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

package org.minbox.framework.api.boot.plugin.mail;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.mail.request.ApiBootMailRequest;
import org.minbox.framework.api.boot.plugin.mail.response.ApiBootMailResponse;

/**
 * ApiBoot Mail Service Interface
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-06-19 18:53
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface ApiBootMailService {
    /**
     * Send Mail
     *
     * @param apiBootMailRequest Send Mail Request Entity
     * @return Send Mail Response Entity
     * @throws ApiBootException ApiBoot Exception
     */
    ApiBootMailResponse sendMail(ApiBootMailRequest apiBootMailRequest) throws ApiBootException;
}
