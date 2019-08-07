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

package org.minbox.framework.api.boot.sample.logging;

import org.minbox.framework.logging.client.notice.LoggingNotice;
import org.minbox.framework.logging.core.MinBoxLog;
import org.springframework.stereotype.Component;

/**
 * ApiBootLogNotice示例
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-16 15:35
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Component
public class LocalNoticeSample implements LoggingNotice {
    /**
     * order 值越小执行越靠前
     *
     * @return
     */
    @Override
    public int getOrder() {
        return 0;
    }

    /**
     * 请求日志通知执行方法
     * ApiBootLog为一次请求日志对象基本信息
     *
     * @param minBoxLog ApiBoot Log
     */
    @Override
    public void notice(MinBoxLog minBoxLog) {
        System.out.println(minBoxLog);
    }
}
