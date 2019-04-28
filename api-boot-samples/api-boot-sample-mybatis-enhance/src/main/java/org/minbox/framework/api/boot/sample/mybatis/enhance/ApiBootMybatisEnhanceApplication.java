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

package org.minbox.framework.api.boot.sample.mybatis.enhance;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * ApiBoot Mybatis Enhance
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-26 17:02
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@SpringBootApplication
@RestController
public class ApiBootMybatisEnhanceApplication {


    public static void main(String[] args) {
        SpringApplication.run(ApiBootMybatisEnhanceApplication.class, args);
    }

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private UserService userService;

    /**
     * 查询全部用户
     *
     * @return
     */
    @GetMapping(value = "/users")
    public List<UserEntity> list() {
        return userMapper.selectAll();
    }

    /**
     * 查询用户详情
     *
     * @param userId
     * @return
     */
    @GetMapping(value = "/{userId}")
    public UserEntity one(@PathVariable String userId) {
        return userMapper.selectOne(userId);
    }

    /**
     * 示例：动态查询用户详情
     *
     * @param userId 用户编号
     * @return
     */
    @GetMapping(value = "/dynamic/{userId}")
    public UserEntity dynamic(@PathVariable String userId) {
        return userService.dynamicSelectOne(userId);
    }
}
