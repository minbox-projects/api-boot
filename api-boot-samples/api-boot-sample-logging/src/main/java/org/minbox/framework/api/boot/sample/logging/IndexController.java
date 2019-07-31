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

import com.alibaba.fastjson.JSON;
import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-15 22:48
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
public class IndexController {

    @Autowired
    private RestTemplate restTemplate;

    @PostMapping(value = "/index")
    public String index(@RequestBody User user) throws Exception {
        HttpEntity<String> httpEntity = new HttpEntity(JSON.toJSONString(user));
        ResponseEntity<String> result = restTemplate.postForEntity("http://localhost:8080/index", httpEntity, String.class);
        System.out.println(result.getBody());
        //user.setName("恒宇少年");
        //throw new Exception("学习下");
        return user.getName();
    }

    @Data
    public static class User {
        private String name;
        private String email;
        private int age;
    }
}
