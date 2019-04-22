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

package org.minbox.framework.api.boot.sample.message.push;

import org.minbox.framework.api.boot.plugin.message.push.ApiBootMessagePushService;
import org.minbox.framework.api.boot.plugin.message.push.annotation.MessagePushSwitch;
import org.minbox.framework.api.boot.plugin.message.push.model.MessagePushBody;
import org.minbox.framework.api.boot.plugin.message.push.model.PusherPlatform;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-22 14:46
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
public class MessagePushSampleService {
    /**
     * ApiBoot Message Push Service
     */
    @Autowired
    private ApiBootMessagePushService apiBootMessagePushService;

    /**
     * 注解 @MessagePushSwitch 不添加时使用默认配置
     * 注解 @MessagePushSwitch 添加时不配置value使用默认配置
     * 注解 @MessagePushSwitch 添加时配置value，则使用配置的value配置
     * 对应api.boot.push.multiple的key
     *
     * @see org.minbox.framework.api.boot.plugin.message.push.model.PushClientConfig
     */
    //@MessagePushSwitch
    //@MessagePushSwitch("user")
    @MessagePushSwitch("other")
    public void test() {
        apiBootMessagePushService.executePush(
                MessagePushBody.builder()
                        .platform(PusherPlatform.ANDROID)
                        .title("消息推送")
                        .message("测试消息推送内容")
                        .alias(Arrays.asList("xxxx"))
                        .tags(Arrays.asList("group1"))
                        .badge(999)
                        .build()
        );
    }
}
