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

package org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.template;

import com.mysema.codegen.CodeWriter;
import com.mysema.codegen.JavaWriter;
import com.mysema.codegen.model.SimpleType;
import lombok.Data;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.EnhanceCodegenConstant;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.wrapper.ClassBuilderWrapper;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.expression.VariableExpression;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.setting.CodegenSetting;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.template.model.*;
import org.springframework.util.ObjectUtils;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * codegen template
 *
 * @author 恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-06-03 15:14
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
public class CodegenTemplate {
    /**
     * class builder wrapper
     */
    private ClassBuilderWrapper wrapper;
    /**
     * codegen setting json entity
     */
    private CodegenSetting codegenSetting;

    public CodegenTemplate(ClassBuilderWrapper wrapper, String codegenSetting) {
        this.wrapper = wrapper;
        // init variable & replace variable
        this.codegenSetting = VariableExpression.initVariable(wrapper, codegenSetting);
    }

    /**
     * formatter java file list with template list
     * - circular template execution formatting content
     * - instance codegenField with content
     * - add return list
     *
     * @return CodegenFile
     */
    public List<CodegenFile> formatJavaFiles() {
        List<CodegenFile> files = new ArrayList();
        codegenSetting.getTemplates().stream().forEach(template -> {
            CodegenFile codegenFile = new CodegenFile();
            codegenFile.setFileName(wrapper.getTableCamelName() + template.getJavaSuffix());
            codegenFile.setPackageName(template.getPackageName());
            codegenFile.setJavaContent(formatJavaContent(template, codegenFile));
            files.add(codegenFile);
        });
        return files;
    }

    /**
     * format java content
     *
     * @param template    template
     * @param codegenFile codegen file
     * @return java content
     */
    private String formatJavaContent(Template template, CodegenFile codegenFile) {
        try {
            StringWriter stringWriter = new StringWriter();
            CodeWriter writer = new JavaWriter(stringWriter);
            // package
            writer.packageDecl(wrapper.getPackageName() + EnhanceCodegenConstant.POINT + template.getPackageName());
            // import
            writer.importClasses(imports(template));
            // javadoc
            writer.javadoc(codegenSetting.getNotes().toArray(new String[]{}));

            // annotation
            if (!ObjectUtils.isEmpty(template.getAnnotations())) {
                writeAnnotation(writer, template.getAnnotations());
            }

            // implement list
            SimpleType[] implementList = getImplementList(template);

            // extends
            writeExtend(writer, template.getExtendList(), template.isInterface(), implementList, codegenFile);

            // fields
            if (!ObjectUtils.isEmpty(template.getFields())) {
                for (Field field : template.getFields()) {
                    // field annotations
                    writeAnnotation(writer, field.getAnnotations());
                    writer.line(field.getName());
                }
            }

            // end class
            writer.end();

            return stringWriter.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * write extend list
     *
     * @param writer        writer
     * @param extendList    extend list
     * @param isInterface   is interface
     * @param implementList implement list
     * @param codegenFile   codegen file
     * @throws Exception exception
     */
    private void writeExtend(CodeWriter writer, List<Extend> extendList, boolean isInterface, SimpleType[] implementList, CodegenFile codegenFile) throws Exception {
        // dont't have extend class
        if (ObjectUtils.isEmpty(extendList)) {
            if (isInterface) {
                if (ObjectUtils.isEmpty(implementList)) {
                    // public class
                    writer.beginInterface(new SimpleType(codegenFile.getFileName(), EnhanceCodegenConstant.EMPTY_STRING, codegenFile.getFileName()));
                } else {
                    // public class
                    writer.beginInterface(new SimpleType(codegenFile.getFileName(), EnhanceCodegenConstant.EMPTY_STRING, codegenFile.getFileName()), implementList);
                }
            } else {
                if (ObjectUtils.isEmpty(implementList)) {
                    // public class
                    writer.beginClass(new SimpleType(codegenFile.getFileName(), EnhanceCodegenConstant.EMPTY_STRING, codegenFile.getFileName()));
                } else {
                    // public class
                    writer.beginClass(new SimpleType(codegenFile.getFileName(), EnhanceCodegenConstant.EMPTY_STRING, codegenFile.getFileName()), null, implementList);
                }
            }
        }
        // have extend class
        else {
            for (Extend extend : extendList) {
                String superClassName = extend.getName();
                if (isInterface) {
                    // begin class
                    writer.beginInterface(new SimpleType(codegenFile.getFileName(), EnhanceCodegenConstant.EMPTY_STRING, codegenFile.getFileName()),
                            new SimpleType(superClassName, EnhanceCodegenConstant.EMPTY_STRING, superClassName));
                } else {
                    if (ObjectUtils.isEmpty(implementList)) {
                        // begin class
                        writer.beginClass(new SimpleType(codegenFile.getFileName(), EnhanceCodegenConstant.EMPTY_STRING, codegenFile.getFileName()),
                                new SimpleType(superClassName, EnhanceCodegenConstant.EMPTY_STRING, superClassName));

                    } else {
                        // begin class
                        writer.beginClass(new SimpleType(codegenFile.getFileName(), EnhanceCodegenConstant.EMPTY_STRING, codegenFile.getFileName()),
                                new SimpleType(superClassName, EnhanceCodegenConstant.EMPTY_STRING, superClassName), implementList);
                    }
                }
            }

        }
    }

    /**
     * write annotation
     *
     * @param writer      writer
     * @param annotations annotation list
     * @throws Exception exception
     */
    private void writeAnnotation(CodeWriter writer, List<Annotation> annotations) throws Exception {
        // annotation
        if (!ObjectUtils.isEmpty(annotations)) {
            for (Annotation annotation : annotations) {
                writer.line(annotation.getName());
            }
        }
    }

    /**
     * get implement list
     *
     * @param template template instance
     * @return SimpleType
     */
    private SimpleType[] getImplementList(Template template) {
        SimpleType[] implementList = null;
        if (!ObjectUtils.isEmpty(template.getImplementList()) && template.getImplementList().size() > 0) {
            implementList = new SimpleType[template.getImplementList().size()];
            for (int i = 0; i < template.getImplementList().size(); i++) {
                Implement implement = template.getImplementList().get(i);
                implementList[i] = new SimpleType(implement.getName(), EnhanceCodegenConstant.EMPTY_STRING, implement.getName());
            }
        }
        return implementList;
    }

    /**
     * Screen all imports
     *
     * @param template template instance
     * @return import array
     */
    private String[] imports(Template template) {
        List<String> imports = new ArrayList();
        // annotation import
        if (!ObjectUtils.isEmpty(template.getAnnotations())) {
            template.getAnnotations().forEach(annotation -> imports.addAll(annotation.getImports()));
        }
        // extend import
        if (!ObjectUtils.isEmpty(template.getExtendList())) {
            template.getExtendList().forEach(extend -> imports.addAll(extend.getImports()));
        }
        // implement import
        if (!ObjectUtils.isEmpty(template.getImplementList())) {
            template.getImplementList().forEach(implement -> imports.addAll(implement.getImports()));
        }
        // field import
        if (!ObjectUtils.isEmpty(template.getFields())) {
            template.getFields().forEach(field -> {
                imports.addAll(field.getImports());
                if (!ObjectUtils.isEmpty(field.getAnnotations())) {
                    field.getAnnotations().forEach(annotation -> imports.addAll(annotation.getImports()));
                }
            });
        }
        return imports.toArray(new String[]{});
    }
}
