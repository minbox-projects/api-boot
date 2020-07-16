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

package org.minbox.framework.api.boot.mail;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.mail.request.ApiBootMailRequest;
import org.minbox.framework.api.boot.mail.response.ApiBootMailResponse;

/**
 * ApiBoot Mail Service Interface
 *
 * @author 恒宇少年
 */
public interface ApiBootMailService {
    /**
     * send mail
     *
     * @param apiBootMailRequest {@link ApiBootMailRequest}
     * @return {@link ApiBootMailResponse}
     * @throws ApiBootException ApiBoot Exception
     */
    ApiBootMailResponse sendMail(ApiBootMailRequest apiBootMailRequest) throws ApiBootException;
}
