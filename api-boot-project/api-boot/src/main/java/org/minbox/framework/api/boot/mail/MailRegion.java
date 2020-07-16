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

import lombok.Getter;

/**
 * Mail Region
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-06-20 09:47
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Getter
public enum MailRegion {
    /**
     * 华北1 - 青岛
     */
    qingdao("cn-qingdao"),
    /**
     * 华北2 - 北京
     */
    beijing("cn-beijing"),
    /**
     * 华北3 - 张家口
     */
    zhangjiakou("cn-zhangjiakou"),
    /**
     * 华北5 - 呼和浩特
     */
    huhehaote("cn-huhehaote"),
    /**
     * 华东1 - 杭州
     */
    hangzhou("cn-hangzhou"),
    /**
     * 华东2 - 上海
     */
    shanghai("cn-shanghai"),
    /**
     * 华南1 - 深圳
     */
    shenzhen("cn-shenzhen"),
    /**
     * 香港
     */
    hongkong("cn-hongkong"),
    /**
     * 亚太东南 1 - 新加坡
     */
    apsoutheast1("ap-southeast-1"),
    /**
     * 亚太东南 2 - 悉尼
     */
    apsoutheast2("ap-southeast-2"),
    /**
     * 亚太东南 3 - 吉隆坡
     */
    apsoutheast3("ap-southeast-3"),
    /**
     * 亚太东南 5 - 雅加达
     */
    apsoutheast5("ap-southeast-5"),
    /**
     * 亚太南部 1 - 孟买
     */
    apsouth1("ap-south-1"),
    /**
     * 亚太东北 1 - 东京
     */
    apnortheast1("ap-northeast-1"),
    /**
     * 美国西部 1 - 硅谷
     */
    uswest1("us-west-1"),
    /**
     * 美国东部 1 - 弗吉尼亚
     */
    useast1("us-east-1"),
    /**
     * 欧洲中部 1 - 法兰克福
     */
    eucentral1("eu-central-1"),
    /**
     * 英国（伦敦）
     */
    euwest1("eu-west-1"),
    /**
     * 中东东部 1 - 迪拜
     */
    meeast1("me-east-1");

    private String value;

    MailRegion(String value) {
        this.value = value;
    }
}
