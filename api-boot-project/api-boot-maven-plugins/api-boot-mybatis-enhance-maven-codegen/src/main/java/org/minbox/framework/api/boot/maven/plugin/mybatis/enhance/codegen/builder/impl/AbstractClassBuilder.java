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

package org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.impl;

import lombok.Getter;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.ClassBuilder;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.wrapper.ClassBuilderWrapper;

/**
 * Abstract Entity Builder
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-29 17:13
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Getter
public abstract class AbstractClassBuilder implements ClassBuilder {
    /**
     * empty string
     */
    public static final String EMPTY_STRING = "";
    /**
     * author
     */
    public static final String AUTHOR = "@author ApiBoot Mybatis Enhance Codegen";
    /**
     * field placeholder
     */
    public static final String FIELD = "private %s %s;";
    /**
     * Column Annotation
     */
    public static final String COLUMN_ANNOTATION = "@Column(name = \"%s\")";
    /**
     * Id auto Annotation
     */
    public static final String ID_AUTO_ANNOTATION = "@Id(generatorType = KeyGeneratorTypeEnum.AUTO)";
    /**
     * Id uuid Annotation
     */
    public static final String ID_UUID_ANNOTATION = "@Id(generatorType = KeyGeneratorTypeEnum.UUID)";
    /**
     * Table Annotation
     */
    public static final String TABLE_ANNOTATION = "@Table(name = \"%s\")";
    /**
     * Table Expression
     */
    public static final String TABLE_EXPRESSION = "TableExpression<%s>";
    /**
     * super Constructor
     */
    public static final String SUPER_CONSTRUCTOR = "super(%s);";
    /**
     * dynamic instance
     */
    public static final String DYNAMIC_INSTANCE = "return new %s(\"%s\");";
    /**
     * Column Expression
     */
    public static final String COLUMN_EXPRESSION = "public ColumnExpression %s = new ColumnExpression(\"%s\", this);";
    /**
     * column expression array instance
     */
    public static final String COLUMN_EXPRESSION_ARRAY_INSTANCE = "return new ColumnExpression[]{%s};";

    /**
     * Encapsulated objects needed for instantiation
     */
    private ClassBuilderWrapper wrapper;

    public AbstractClassBuilder(ClassBuilderWrapper classBuilderWrapper) {
        this.wrapper = classBuilderWrapper;
    }

    /**
     * default prefix is empty string
     *
     * @return file prefix
     */
    @Override
    public String getDefaultPrefix() {
        return "";
    }
}
