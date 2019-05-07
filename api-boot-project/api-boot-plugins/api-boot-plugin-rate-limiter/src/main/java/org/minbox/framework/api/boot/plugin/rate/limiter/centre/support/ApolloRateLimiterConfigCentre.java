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

package org.minbox.framework.api.boot.plugin.rate.limiter.centre.support;

import org.minbox.framework.api.boot.common.exception.ApiBootException;

/**
 * Apollo Config Support
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-05-06 21:52
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApolloRateLimiterConfigCentre extends AbstractRateLimiterConfigCentre {

    @Override
    public Long getQps(String configKey) throws ApiBootException {
        return 0L;
    }

    @Override
    public void setQps(String configKey, Long QPS) throws ApiBootException {

    }
}
