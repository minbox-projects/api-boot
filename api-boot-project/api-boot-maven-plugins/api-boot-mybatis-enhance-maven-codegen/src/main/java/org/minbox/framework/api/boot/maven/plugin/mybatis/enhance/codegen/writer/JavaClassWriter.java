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

package org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.writer;

import java.io.*;

/**
 * Java Class File Writer
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-29 16:48
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class JavaClassWriter {
    /**
     * java file default charset
     */
    private static final String DEFAULT_CHARSET_NAME = "UTF-8";

    /**
     * Write code to entity classes
     *
     * @param javaFilePath java  file path
     * @param content      file content
     */
    public static void writeToJavaFile(String javaFilePath, String content) {
        try {
            OutputStreamWriter writerStream = new OutputStreamWriter(new FileOutputStream(javaFilePath), DEFAULT_CHARSET_NAME);
            BufferedWriter bw = new BufferedWriter(writerStream);
            bw.append(content);
            bw.close();
            writerStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
