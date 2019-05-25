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

package org.minbox.framework.api.boot.sample;

import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.minbox.framework.api.boot.plugin.rate.limiter.result.RateLimiterOverFlowResponse;
import org.springframework.stereotype.Component;

/**
 * 自定义流量溢出后响应的实体格式
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-05-25 16:21
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Component
public class CustomerResponse implements RateLimiterOverFlowResponse {
    @Override
    public Object overflow(Object[] methodArgs) {
        return ApiBootResult.builder().errorCode("REQUEST_OVER_FLOW").errorMessage("流量被限制.").build();
    }
}
