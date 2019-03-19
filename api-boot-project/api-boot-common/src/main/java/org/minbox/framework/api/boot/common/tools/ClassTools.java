/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.minbox.framework.api.boot.common.tools;

import org.reflections.Reflections;
import org.reflections.scanners.SubTypesScanner;
import org.reflections.scanners.TypeAnnotationsScanner;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.reflections.util.FilterBuilder;

import java.util.Collection;

/**
 * class 工具类
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 17:02
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ClassTools {
    /**
     * 初始化反射对象
     *
     * @param scannerPackage 扫描的package
     * @return 反射对象
     */
    static Reflections initReflections(String scannerPackage) {
        ConfigurationBuilder configurationBuilder = new ConfigurationBuilder();
        configurationBuilder.setScanners(new TypeAnnotationsScanner(), new SubTypesScanner());
        configurationBuilder.filterInputsBy(new FilterBuilder().includePackage(scannerPackage));
        configurationBuilder.addUrls(ClasspathHelper.forPackage(scannerPackage));
        return new Reflections(scannerPackage);
    }

    /**
     * 获取指定package下的接口实现类
     *
     * @param scannerPackage 扫描的package
     * @return 实现类集合
     */
    public static Collection<?> getSubClassList(String scannerPackage, Class clazz) {
        return initReflections(scannerPackage).getSubTypesOf(clazz);
    }
}
