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

package org.minbox.framework.api.boot.plugin.logging;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

/**
 * ApiBoot Log Object
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-15 16:41
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
public class ApiBootLog implements Serializable {
    /**
     * trace id
     */
    private String traceId;
    /**
     * span id
     */
    private String spanId;
    /**
     * parent span id
     */
    private String parentSpanId;
    /**
     * request uri
     */
    private String requestUri;
    /**
     * request method
     */
    private String requestMethod;
    /**
     * http status code
     */
    private int httpStatus;
    /**
     * request ip
     */
    private String requestIp;
    /**
     * service ip address
     */
    private String serviceIp;
    /**
     * service port
     */
    private String servicePort;
    /**
     * start time
     */
    private Long startTime;
    /**
     * end time
     */
    private Long endTime;
    /**
     * this request time consuming
     */
    private long timeConsuming;
    /**
     * service id
     */
    private String serviceId;
    /**
     * request headers
     */
    private Map<String, String> requestHeaders;
    /**
     * request param
     */
    private String requestParam;
    /**
     * request body
     */
    private String requestBody;
    /**
     * response headers
     */
    private Map<String, String> responseHeaders;
    /**
     * response body
     */
    private String responseBody;
    /**
     * exception stack
     */
    private String exceptionStack;
}
