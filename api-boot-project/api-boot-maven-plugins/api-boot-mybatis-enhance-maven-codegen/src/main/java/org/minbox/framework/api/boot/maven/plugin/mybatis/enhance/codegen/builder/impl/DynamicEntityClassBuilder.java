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

import com.gitee.hengboy.builder.core.database.model.Column;
import com.gitee.hengboy.builder.core.database.model.Table;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import com.mysema.codegen.CodeWriter;
import com.mysema.codegen.JavaWriter;
import com.mysema.codegen.model.Parameter;
import com.mysema.codegen.model.SimpleType;
import com.mysema.codegen.model.Types;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.wrapper.ClassBuilderWrapper;

import java.io.StringWriter;

/**
 * Dynamic Data Entity Class Builder
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-29 17:20
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class DynamicEntityClassBuilder extends AbstractClassBuilder {
    /**
     * dynamic class prefix
     */
    private static final String DYNAMIC_CLASS_PREFIX = "D";
    /**
     * constructor parameter name
     */
    private static final String CONSTRUCTOR_PARAMETER_NAME = "root";
    /**
     * dynamic method name
     */
    private static final String DYNAMIC_METHOD_NAME = "DSL";
    /**
     * column expression array
     */
    private static final String COLUMN_EXPRESSION_ARRAY = "ColumnExpression[]";
    /**
     * get column method name
     */
    private static final String GET_COLUMN_METHOD_NAME = "getColumns";

    public DynamicEntityClassBuilder(ClassBuilderWrapper classBuilderWrapper) {
        super(classBuilderWrapper);
    }

    /**
     * generator dynamic entity
     *
     * @return
     */
    @Override
    public String getClassContent() {
        try {
            StringWriter stringWriter = new StringWriter();
            CodeWriter writer = new JavaWriter(stringWriter);
            // current table
            Table table = getWrapper().getTable();

            // package
            writer.packageDecl(getWrapper().getPackageName());

            writer.imports(ColumnExpression.class, TableExpression.class);

            // formatter super class name
            String superClassName = String.format(TABLE_EXPRESSION, getEntityName(getWrapper().getTableCamelName()));

            // java doc
            writer.javadoc(table.getRemark(), AUTHOR);
            // begin class
            writer.beginClass(new SimpleType(getWrapper().getTableCamelName(), EMPTY_STRING, getWrapper().getTableCamelName()),
                    new SimpleType(superClassName, EMPTY_STRING, superClassName));

            // constructor
            writer.beginConstructor(new Parameter(CONSTRUCTOR_PARAMETER_NAME, Types.STRING));
            writer.line(String.format(SUPER_CONSTRUCTOR, CONSTRUCTOR_PARAMETER_NAME));
            writer.end();

            // dsl method
            writer.beginStaticMethod(new SimpleType(getWrapper().getTableCamelName(), EMPTY_STRING, getWrapper().getTableCamelName()), DYNAMIC_METHOD_NAME);
            writer.line(String.format(DYNAMIC_INSTANCE, getWrapper().getTableCamelName(), table.getTableName()));
            writer.end();

            StringBuffer columns = new StringBuffer();

            // column expression
            for (int i = 0; i < table.getColumns().size(); i++) {
                Column column = table.getColumns().get(i);
                // column java doc
                writer.javadoc(column.getRemark());
                writer.line(String.format(COLUMN_EXPRESSION, column.getJavaProperty(), column.getColumnName()));
                columns.append(column.getJavaProperty());
                columns.append(i == table.getColumns().size() - 1 ? EMPTY_STRING : ", ");
            }

            // getColumns method
            writer.annotation(Override.class);
            writer.beginPublicMethod(new SimpleType(COLUMN_EXPRESSION_ARRAY, EMPTY_STRING, COLUMN_EXPRESSION_ARRAY), GET_COLUMN_METHOD_NAME);
            writer.line(String.format(COLUMN_EXPRESSION_ARRAY_INSTANCE, columns.toString()));
            writer.end();

            // end class
            writer.end();

            return stringWriter.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * get entity name
     *
     * @param camelName dynamic entity name
     * @return entity name
     */
    public String getEntityName(String camelName) {
        return camelName.replaceFirst(DYNAMIC_CLASS_PREFIX, EMPTY_STRING);
    }

    @Override
    public String getDefaultPrefix() {
        return DYNAMIC_CLASS_PREFIX;
    }
}
