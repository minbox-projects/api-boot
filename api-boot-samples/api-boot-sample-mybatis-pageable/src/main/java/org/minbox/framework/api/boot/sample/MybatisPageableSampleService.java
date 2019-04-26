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

import com.gitee.hengboy.mybatis.pageable.Page;
import com.gitee.hengboy.mybatis.pageable.request.PageableRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Mybatis Pageable Sample Service
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-26 16:13
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
public class MybatisPageableSampleService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 自动分页查询
     *
     * @return
     */
    public Page<UserEntity> pageable() {
        return PageableRequest.of(1, 1).request(() -> listAll());
    }

    /**
     * 查询全部用户
     *
     * @return
     */
    public List<UserEntity> listAll() {
        return userMapper.selectAll();
    }
}
