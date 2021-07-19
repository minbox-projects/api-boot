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

import com.gitee.hengboy.builder.common.enums.JavaTypeEnum;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import com.mysema.codegen.CodeWriter;
import com.mysema.codegen.JavaWriter;
import com.mysema.codegen.model.SimpleType;
import lombok.Data;
import org.apache.maven.plugin.logging.SystemStreamLog;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.EnhanceCodegenConstant;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.wrapper.ClassBuilderWrapper;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.mapping.TypeMapping;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * data entity class builder
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-29 17:20
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class EntityClassBuilder extends AbstractClassBuilder {
    /**
     * The system stream log instance
     */
    private static final SystemStreamLog LOG = new SystemStreamLog();

    public EntityClassBuilder(ClassBuilderWrapper classBuilderWrapper) {
        super(classBuilderWrapper);
    }

    /**
     * generator data entity
     *
     * @return Class Content
     */
    @Override
    public String getClassContent() {
        try {
            StringWriter stringWriter = new StringWriter();
            CodeWriter writer = new JavaWriter(stringWriter);
            // current table
            com.gitee.hengboy.builder.core.database.model.Table table = getWrapper().getTable();
            // package
            writer.packageDecl(getWrapper().getPackageName());

            // choose imports
            chooseImport(table, writer);

            // javadoc
            writer.javadoc(table.getRemark(), AUTHOR);

            // begin class
            writer.annotation(Data.class);
            // @table
            writer.line(String.format(TABLE_ANNOTATION, table.getTableName()));

            // serializable
            SimpleType serializableInterface = new SimpleType(Serializable.class.getName(), EnhanceCodegenConstant.EMPTY_STRING, Serializable.class.getName());

            // public class
            writer.beginClass(new SimpleType(getWrapper().getTableCamelName(), EnhanceCodegenConstant.EMPTY_STRING, getWrapper().getTableCamelName()), null, serializableInterface);

            for (com.gitee.hengboy.builder.core.database.model.Column column : table.getColumns()) {
                // comment
                writer.javadoc(column.getRemark());
                if (column.isPrimaryKey()) {
                    writer.line(column.isAutoincrement() ? ID_AUTO_ANNOTATION : ID_UUID_ANNOTATION);
                }
                // @Column
                writer.line(getColumnAnnotation(column));
                // get mapped javaType
                String javaType = this.mappingJavaType(column.getJdbcTypeName());
                javaType = Optional.ofNullable(javaType).orElse(column.getJavaType());
                // private field
                writer.line(String.format(FIELD, javaType, formatterJavaPropertyName(column.getColumnName()), getColumnDefaultValue(column)));
                LOG.info("Field：" + column.getJavaProperty() + "，Column：" + column.getColumnName() + "，" +
                    "Java Type：" + column.getJavaType() + "，Jdbc Type：" + column.getJdbcTypeName() + "，" +
                    "Default Value：" + column.getDefaultValue() + "， Mark：" + column.getRemark() + ".");
            }

            // end class
            writer.end();

            return stringWriter.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Get the mapped javaType through jdbcType
     *
     * @param jdbcTypeName The column name
     * @return javaType short name
     */
    String mappingJavaType(String jdbcTypeName) {
        String javaType = null;
        List<TypeMapping> typeMappingList = getWrapper().getTypeMappings();
        if (ObjectUtils.isEmpty(typeMappingList)) {
            return null;
        }
        for (TypeMapping typeMapping : typeMappingList) {
            if (jdbcTypeName.equalsIgnoreCase(typeMapping.getJdbcType())) {
                javaType = typeMapping.getJavaType();
                break;
            }
        }
        return javaType;
    }

    /**
     * choose import
     *
     * @param table  current table
     * @param writer code writer
     */
    void chooseImport(com.gitee.hengboy.builder.core.database.model.Table table, CodeWriter writer) throws IOException {
        // basic imports
        writer.imports(Column.class, Id.class, Table.class, KeyGeneratorTypeEnum.class, Data.class, Serializable.class);

        // import bigDecimal
        if (table.isHasBigDecimal()) {
            writer.imports(BigDecimal.class);
        }
        // import date
        if (table.isHasSqlDate()) {
            writer.imports(Date.class);
            writer.imports(LocalDate.class);
        }
        // import timeStamp
        if (table.isHasTimeStamp()) {
            writer.imports(Timestamp.class);
            writer.imports(LocalDateTime.class);
        }
    }

    @Override
    public String getPrefixDir() {
        return EnhanceCodegenConstant.EMPTY_STRING;
    }

    /**
     * get column annotation definition
     *
     * @param column column
     * @return column annotation
     */
    private String getColumnAnnotation(com.gitee.hengboy.builder.core.database.model.Column column) {
        // append content to @column(name=xxx after
        String columnAnnotation = String.format(COLUMN_ANNOTATION, column.getColumnName());
        // is java.sql.Timestamp && default value is current_timestamp
        if (JavaTypeEnum.TYPE_TIMESTAMP.getShortName().equals(column.getJavaType()) && EnhanceCodegenConstant.CURRENT_TIMESTAMP.equals(column.getDefaultValue())) {
            columnAnnotation = String.format(COLUMN_INSERTABLE_ANNOTATION, column.getColumnName());
        }
        return columnAnnotation;
    }

    /**
     * get column default value
     * string default value
     * int default value
     *
     * @param column column
     * @return column default value
     */
    private String getColumnDefaultValue(com.gitee.hengboy.builder.core.database.model.Column column) {
        String pattern = " = %s";
        String defaultValue = EnhanceCodegenConstant.EMPTY_STRING;
        // don't have default value
        if (StringUtils.isEmpty(column.getDefaultValue())) {
            return defaultValue;
        }

        // java.lang.Integer
        if (JavaTypeEnum.TYPE_INTEGER.getShortName().equals(column.getJavaType())) {
            defaultValue = column.getDefaultValue();
        }
        // java.lang.String
        else if (JavaTypeEnum.TYPE_STRING.getShortName().equals(column.getJavaType())) {
            defaultValue = String.format("%s%s%s", "\"", column.getDefaultValue(), "\"");
        }
        // other is ignore
        else {

        }
        return !StringUtils.isEmpty(defaultValue) ? String.format(pattern, defaultValue) : defaultValue;
    }

}
