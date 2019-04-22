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

package org.minbox.framework.api.boot.plugin.message.push.support;

import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.*;
import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.message.push.model.MessagePushBody;
import org.minbox.framework.api.boot.plugin.message.push.model.PushClientConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Map;

/**
 * ApiBoot Message JPush Support
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-20 15:51
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootMessagePushJiGuangServiceImpl extends ApiBootAbstractMessagePushServiceImpl {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootMessagePushJiGuangServiceImpl.class);

    public ApiBootMessagePushJiGuangServiceImpl(Map<String, PushClientConfig> pushClientConfigs, boolean production) {
        super(pushClientConfigs, production);
    }

    /**
     * execute push message
     *
     * @param messagePushBody request body
     * @throws ApiBootException ApiBoot Exception
     */
    @Override
    public void executePush(MessagePushBody messagePushBody) throws ApiBootException {
        try {
            // push client config
            PushClientConfig pushClientConfig = getCurrentPushClient();
            // jpush client
            JPushClient pushClient = new JPushClient(pushClientConfig.getMasterSecret(), pushClientConfig.getAppKey(), null, ClientConfig.getInstance());

            PushPayload.Builder builder = PushPayload.newBuilder();

            // setting platform
            addPlatformMeta(messagePushBody, builder);

            // setting tag
            addTagMeta(messagePushBody, builder);
            // setting alias
            // Priority is higher than tag
            addAliasMeta(messagePushBody.getAlias(), builder);

            // setting notification
            addNotificationMeta(builder, messagePushBody);

            // execute push message
            pushClient.sendPush(builder.build());
        } catch (Exception e) {
            logger.error("Execute push message fail.", e);
        }

    }

    /**
     * setting platform
     *
     * @param messagePushBody ApiBoot Message Push request body
     * @param builder         push payload builder
     */
    private void addPlatformMeta(MessagePushBody messagePushBody, PushPayload.Builder builder) {
        switch (messagePushBody.getPlatform()) {
            case ALL:
                builder.setPlatform(Platform.all());
                break;
            case IOS:
                builder.setPlatform(Platform.ios());
                // set is production
                builder.setOptions(Options.newBuilder().setApnsProduction(isProduction()).build());
                break;
            case ANDROID:
                builder.setPlatform(Platform.android());
                break;
            default:
                builder.setPlatform(Platform.all());
                break;
        }
    }

    /**
     * add alias to push payload
     *
     * @param alias   alias list
     * @param builder push payload builder
     */
    private void addAliasMeta(List<String> alias, PushPayload.Builder builder) {
        if (!ObjectUtils.isEmpty(alias)) {
            builder.setAudience(Audience.alias(alias));
        }
    }

    /**
     * add tag
     *
     * @param messagePushBody push message body
     * @param builder         push payload builder
     */
    private void addTagMeta(MessagePushBody messagePushBody, PushPayload.Builder builder) {
        if (!ObjectUtils.isEmpty(messagePushBody.getTags())) {
            builder.setAudience(Audience.tag(messagePushBody.getTags()));
        }
    }

    /**
     * add notification
     *
     * @param builder         push payload builder
     * @param messagePushBody message push body
     */
    private void addNotificationMeta(PushPayload.Builder builder, MessagePushBody messagePushBody) {
        Notification notification = null;
        switch (messagePushBody.getPlatform()) {
            case ALL:
                notification = Notification.newBuilder()
                        .addPlatformNotification(getIosNotificationMeta(messagePushBody))
                        .addPlatformNotification(getAndroidNotificationMeta(messagePushBody))
                        .build();
                break;
            case IOS:
                notification = Notification.newBuilder()
                        .addPlatformNotification(getIosNotificationMeta(messagePushBody)).build();
                break;
            case ANDROID:
                notification = Notification.newBuilder()
                        .addPlatformNotification(getAndroidNotificationMeta(messagePushBody)).build();
                break;

        }
        builder.setNotification(notification);
    }

    /**
     * Ios notification
     *
     * @param messagePushBody push message request body
     * @return IosNotification
     */
    private PlatformNotification getIosNotificationMeta(MessagePushBody messagePushBody) {
        IosNotification.Builder builder = IosNotification.newBuilder().setAlert(IosAlert.newBuilder().setTitleAndBody(messagePushBody.getTitle(), messagePushBody.getSubTitle(), messagePushBody.getMessage()).build()).setBadge(messagePushBody.getBadge()).setSound(messagePushBody.getSound());
        // extras param
        if (!ObjectUtils.isEmpty(messagePushBody.getExtras())) {
            builder.addExtras(messagePushBody.getExtras());
        }
        return builder.build();
    }

    /**
     * android notification
     *
     * @param messagePushBody push message request body
     * @return AndroidNotification
     */
    private PlatformNotification getAndroidNotificationMeta(MessagePushBody messagePushBody) {
        return AndroidNotification.newBuilder().setTitle(messagePushBody.getTitle()).setAlert(messagePushBody.getMessage()).addExtras(messagePushBody.getExtras()).build();
    }
}
