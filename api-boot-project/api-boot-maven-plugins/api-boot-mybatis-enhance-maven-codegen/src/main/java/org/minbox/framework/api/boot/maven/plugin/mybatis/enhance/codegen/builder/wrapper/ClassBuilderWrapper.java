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

package org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.wrapper;

import com.gitee.hengboy.builder.core.database.model.Table;
import lombok.Builder;
import lombok.Data;

/**
 * ClassBuilder Param
 * use for create new instance
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-30 09:17
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@Builder
public class ClassBuilderWrapper {
    /**
     * package name
     */
    private String packageName;
    /**
     * table camel name
     * for class name
     */
    private String tableCamelName;
    /**
     * current table
     */
    private Table table;
}
