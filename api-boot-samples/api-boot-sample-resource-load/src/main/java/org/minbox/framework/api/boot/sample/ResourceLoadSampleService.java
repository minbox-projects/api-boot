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

import lombok.Data;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceFields;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceLoad;
import org.minbox.framework.api.boot.plugin.resource.load.enums.ResourceStoreEvent;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-15 11:48
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
public class ResourceLoadSampleService {

    /**
     * 添加用户自动更新资源示例
     *
     * @param userInfo
     * @param shortImage
     * @param headImage
     */
    @ResourceLoad(event = ResourceStoreEvent.UPDATE)
    @ResourceFields({
            @ResourceField(name = "#p2", source = "#p0.userId", type = "HEAD_IMAGE"),
            @ResourceField(name = "#p1", source = "#p0.userId", type = "SHORT_IMAGE")
    })
    public void updateUser(SampleUserInfo userInfo, List<String> shortImage, String headImage) {

    }
    /**
     * 添加用户自动添加资源示例
     *
     * @param userInfo
     * @param shortImage
     * @param headImage
     */
    @ResourceLoad(event = ResourceStoreEvent.INSERT)
    @ResourceFields({
            @ResourceField(name = "#p2", source = "#p0.userId", type = "HEAD_IMAGE"),
            @ResourceField(name = "#p1", source = "#p0.userId", type = "SHORT_IMAGE")
    })
    public void insertUser(SampleUserInfo userInfo, List<String> shortImage, String headImage) {

    }

    /**
     * 自动删除资源示例
     *
     * @param userId 用户编号（资源业务逻辑编号）
     */
    @ResourceLoad(event = ResourceStoreEvent.DELETE)
    @ResourceFields({
            @ResourceField(name = "headImage", source = "#p0", type = "HEAD_IMAGE"),
            @ResourceField(name = "shortImage", source = "#p0", type = "SHORT_IMAGE")
    })
    public void deleteUser(String userId) {
        // 删除用户逻辑
    }

    /**
     * 返回值为单个对象的示例
     *
     * @return
     */
    @ResourceLoad
    @ResourceFields({
            @ResourceField(name = "headImage", source = "userId", type = "HEAD_IMAGE"),
            @ResourceField(name = "shortImage", source = "userId", type = "SHORT_IMAGE")
    })
    public SampleUserInfo singleObjectSample() {
        return new SampleUserInfo("yuqiyu", 24);
    }

    /**
     * 返回值为list集合的示例
     *
     * @return
     */
    @ResourceLoad
    @ResourceFields({
            @ResourceField(name = "headImage", source = "userId", type = "HEAD_IMAGE"),
            @ResourceField(name = "shortImage", source = "userId", type = "SHORT_IMAGE")
    })
    public List<SampleUserInfo> listSample() {
        List<SampleUserInfo> users = new ArrayList();
        users.add(new SampleUserInfo("yuqiyu", 24));
        users.add(new SampleUserInfo("hengboy", 24));
        return users;
    }

    /**
     * 返回值为map集合的示例
     *
     * @return
     */
    @ResourceLoad
    @ResourceFields({
            @ResourceField(name = "headImage", source = "userId", type = "HEAD_IMAGE"),
            @ResourceField(name = "shortImage", source = "userId", type = "SHORT_IMAGE")
    })
    public Map<String, SampleUserInfo> mapSample() {
        Map<String, SampleUserInfo> users = new HashMap<>(2);
        users.put("yuqiyu", new SampleUserInfo("yuqiyu", 24));
        users.put("hengboy", new SampleUserInfo("hengboy", 24));
        return users;
    }

    /**
     * 示例对象
     */
    @Data
    class SampleUserInfo {
        public SampleUserInfo(String userId, int age) {
            this.userId = userId;
            this.age = age;
        }

        private String userId;
        private String headImage;
        private String shortImage;
        private int age;
    }
}
