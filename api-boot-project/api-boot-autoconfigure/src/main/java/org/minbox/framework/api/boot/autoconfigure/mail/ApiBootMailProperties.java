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

package org.minbox.framework.api.boot.autoconfigure.mail;

import lombok.Data;
import org.minbox.framework.api.boot.plugin.mail.MailRegion;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.mail.ApiBootMailProperties.API_BOOT_MAIL_PREFIX;

/**
 * ApiBoot Mail Properties
 *
 * @author 恒宇少年
 */
@Configuration
@ConfigurationProperties(prefix = API_BOOT_MAIL_PREFIX)
@Data
public class ApiBootMailProperties {
    /**
     * ApiBoot mail properties config prefix
     */
    public static final String API_BOOT_MAIL_PREFIX = "api.boot.mail";
    /**
     * AliYun console allocation Access Key
     */
    private String accessKey;
    /**
     * AliYun console allocation Access Secret
     */
    private String accessSecret;
    /**
     * The mail address configured in the management console.
     */
    private String accountName;
    /**
     * Use the reply address configured in the administrative console (status must be validated).
     */
    private boolean replyToAddress = true;
    /**
     * Random accounts range from 0 to 1:0 and addresses from 1.
     */
    private int addressType = 1;
    /**
     * Default From Alias
     */
    private String fromAlias = "ApiBootMail";
    /**
     * Default Tag Name
     */
    private String tagName;
    /**
     * Mail Region
     */
    private MailRegion region;
}
