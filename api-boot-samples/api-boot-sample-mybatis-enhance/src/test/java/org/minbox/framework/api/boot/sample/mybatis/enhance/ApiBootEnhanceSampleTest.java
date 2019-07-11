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

import org.junit.Test;
import org.junit.runner.RunWith;
import org.minbox.framework.api.boot.sample.mybatis.enhance.dto.SystemUserDTO;
import org.minbox.framework.api.boot.sample.mybatis.enhance.entity.SystemUser;
import org.minbox.framework.api.boot.sample.mybatis.enhance.mapper.SystemUserMapper;
import org.minbox.framework.api.boot.sample.mybatis.enhance.service.SystemUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-09 22:16
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiBootMybatisEnhanceApplication.class)
public class ApiBootEnhanceSampleTest {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootEnhanceSampleTest.class);

    @Autowired
    private SystemUserService systemUserService;

    @Autowired
    private SystemUserMapper systemUserMapper;

    @Test
    public void manyTableJoin() throws Exception {
        List<SystemUser> users = systemUserService.manyTableJoin();
        if (!ObjectUtils.isEmpty(users)) {
            users.stream().forEach(user -> {
                logger.info("用户名：{}，昵称：{}", user.getUserName(), user.getNickName());
            });
        }
    }

    @Test
    public void diyResultType() {
        List<SystemUserDTO> users = systemUserService.diyResultType();
        if (!ObjectUtils.isEmpty(users)) {
            users.stream().forEach(user -> {
                logger.info("用户名：{}，昵称：{}", user.getUserName(), user.getNickName());
            });
        }
    }

    @Test
    public void assembleQuery() {
        List<SystemUser> users = systemUserService.assembleQuery(false);
        if (!ObjectUtils.isEmpty(users)) {
            users.stream().forEach(user -> {
                logger.info("用户名：{}，昵称：{}", user.getUserName(), user.getNickName());
            });
        }
    }

    @Test
    public void count() {
        Long count = systemUserService.count();
        logger.info("统计结果：{}", count);
    }

    @Test
    public void avg() {
        Integer avg = systemUserService.avg();
        logger.info("年龄平均值：{}", avg);
    }

    @Test
    public void sum() {
        Long sum = systemUserService.sum();
        logger.info("年龄总和：{}", sum);
    }

    @Test
    public void min() {
        Integer min = systemUserService.min();
        logger.info("最小的年龄：{}", min);
    }

    @Test
    public void max() {
        Integer max = systemUserService.max();
        logger.info("最大的年龄：{}", max);
    }

    @Test
    public void dslUpdate() {
        systemUserService.dslUpdate();
    }

    @Test
    public void dslDelete() {
        systemUserService.dslDelete();
    }

    @Test
    public void dslOrder() {
        List<SystemUser> users = systemUserService.order();
        if (!ObjectUtils.isEmpty(users)) {
            users.stream().forEach(user -> {
                logger.info("用户名：{}，昵称：{}", user.getUserName(), user.getNickName());
            });
        }
    }

    @Test
    public void page() {
        List<SystemUser> users = systemUserService.page();
        if (!ObjectUtils.isEmpty(users)) {
            users.stream().forEach(user -> {
                logger.info("用户名：{}，昵称：{}", user.getUserName(), user.getNickName());
            });
        }
    }

    @Test
    public void group() {
        List<SystemUser> users = systemUserService.group();
        if (!ObjectUtils.isEmpty(users)) {
            users.stream().forEach(user -> {
                logger.info("用户名：{}，昵称：{}", user.getUserName(), user.getNickName());
            });
        }
    }

    @Test
    public void methodNamed() {
        SystemUser user1 = systemUserMapper.findByUserName("admin");
        logger.info("用户名查询：{}", user1.getUserName());
        SystemUser user2 = systemUserMapper.findByUserNameAndStatus("admin", 1);
        logger.info("用户名 and 状态查询：{}", user2.getUserName());
        List<SystemUser> users = systemUserMapper.findByStatus(1);
        if (!ObjectUtils.isEmpty(users)) {
            users.stream().forEach(user -> {
                logger.info("用户名：{}，昵称：{}", user.getUserName(), user.getNickName());
            });
        }
    }

    @Test
    public void countNamed() {
        Long count = systemUserMapper.countByStatus(1);
        logger.info("统计数据：{}", count);
        count = systemUserMapper.countByUserNameAndStatus("admin", 1);
        logger.info("统计数据：{}", count);
    }

    @Test
    public void removeNamed() {
        systemUserMapper.removeByStatus(0);
        systemUserMapper.removeByUserNameAndStatus("admin", 0);
    }

}
