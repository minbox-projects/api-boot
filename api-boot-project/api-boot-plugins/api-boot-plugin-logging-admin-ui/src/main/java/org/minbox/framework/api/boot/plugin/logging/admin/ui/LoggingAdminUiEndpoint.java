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

package org.minbox.framework.api.boot.plugin.logging.admin.ui;

import org.minbox.framework.api.boot.plugin.logging.admin.endpoint.Endpoint;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ApiBoot Logging Admin UI Endpoint
 * The ability to provide logging admin data to the public
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-26 14:17
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Endpoint
public class LoggingAdminUiEndpoint {

    @GetMapping(value = "/test")
    @ResponseBody
    public ResponseEntity<String> test() {
        return new ResponseEntity("this is test body.", HttpStatus.OK);
    }
}
