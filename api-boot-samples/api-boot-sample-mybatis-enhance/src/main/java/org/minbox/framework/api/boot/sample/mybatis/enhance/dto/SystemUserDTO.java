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

package org.minbox.framework.api.boot.sample.mybatis.enhance.dto;

import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import lombok.Data;

/**
 * 自定义系统用户数据转换实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-10 09:08
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
public class SystemUserDTO {
    /**
     * 主键
     */
    @Column(name = "SU_ID")
    private String id;
    /**
     * 用户名
     */
    @Column(name = "SU_USER_NAME")
    private String userName;
    /**
     * 用户昵称
     */
    @Column(name = "SU_NICK_NAME")
    private String nickName;
}
