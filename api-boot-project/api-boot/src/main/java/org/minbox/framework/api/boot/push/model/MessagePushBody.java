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

package org.minbox.framework.api.boot.push.model;

import lombok.Builder;
import lombok.Data;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * ApiBoot Message Push Body
 *
 * @author 恒宇少年
 */
@Data
@Builder
public class MessagePushBody {
    /**
     * pusher platform
     * default push all platform
     */
    private PusherPlatform platform;
    /**
     * alias list
     */
    private List<String> alias;
    /**
     * tag list
     */
    private List<String> tags;
    /**
     * badge number
     */
    private int badge;
    /**
     * sound
     */
    private String sound;
    /**
     * extras map context
     */
    private Map<String, String> extras;
    /**
     * message title
     */
    private String title;
    /**
     * message sub title
     */
    private String subTitle;
    /**
     * message context
     */
    private String message;

    /**
     * get message push sound
     *
     * @return sound
     */
    public String getSound() {
        return StringUtils.isEmpty(sound) ? "default" : sound;
    }
}
