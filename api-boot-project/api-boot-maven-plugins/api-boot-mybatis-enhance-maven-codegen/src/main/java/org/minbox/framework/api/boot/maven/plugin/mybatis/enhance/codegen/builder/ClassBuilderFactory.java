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

package org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder;

import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.wrapper.ClassBuilderWrapper;

import java.lang.reflect.Constructor;

/**
 * Class Builder Factory
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-30 09:00
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ClassBuilderFactory {
    /**
     * instantiation ClassBuilder sub class
     *
     * @param impClass sub class
     * @param wrapper  ClassBuilder Wrapper
     * @return ClassBuilder Sub Instance
     */
    public static ClassBuilder newInstance(Class<? extends ClassBuilder> impClass, ClassBuilderWrapper wrapper) {
        try {
            Constructor constructor = impClass.getDeclaredConstructor(ClassBuilderWrapper.class);
            return (ClassBuilder) constructor.newInstance(wrapper);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
