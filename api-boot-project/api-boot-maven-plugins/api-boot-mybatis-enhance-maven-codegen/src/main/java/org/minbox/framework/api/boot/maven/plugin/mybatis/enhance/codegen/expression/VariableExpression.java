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

package org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.expression;

import com.alibaba.fastjson.JSON;
import com.gitee.hengboy.builder.core.database.model.Column;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.EnhanceCodegenConstant;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.wrapper.ClassBuilderWrapper;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.setting.CodegenSetting;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.template.model.Template;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.template.variable.CodegenTemplateVariable;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.tools.DateFormatTools;
import org.springframework.util.ObjectUtils;

import java.util.Iterator;
import java.util.List;

/**
 * variable expression
 *
 * @author 恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-06-04 10:07
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class VariableExpression {
    /**
     * #template.package.name
     */
    private static final String TEMPLATE_PACKAGE_NAME = "#%s.name";
    /**
     * #template.package.position
     */
    private static final String TEMPLATE_PACKAGE_POSITION = "#%s.position";

    /**
     * init variable map
     *
     * @param wrapper            class builder wrapper
     * @param codegenSettingJson codegen setting json
     */
    public static CodegenSetting initVariable(ClassBuilderWrapper wrapper, String codegenSettingJson) {
        CodegenTemplateVariable.VARIABLES.put(CodegenTemplateVariable.NOW, DateFormatTools.formatTime(System.currentTimeMillis(), DateFormatTools.YYYY_MM_DD_HH_MM_SS));
        CodegenTemplateVariable.VARIABLES.put(CodegenTemplateVariable.DESC, wrapper.getTable().getRemark());
        CodegenTemplateVariable.VARIABLES.put(CodegenTemplateVariable.ENTITY_NAME, wrapper.getTableCamelName());
        CodegenTemplateVariable.VARIABLES.put(CodegenTemplateVariable.ENTITY_POSITION, wrapper.getPackageName() + EnhanceCodegenConstant.POINT + wrapper.getTableCamelName());
        List<Column> pks = wrapper.getTable().getPrimaryKeys();
        if (!ObjectUtils.isEmpty(pks) && pks.size() > 0) {
            Column pk = pks.get(0);
            CodegenTemplateVariable.VARIABLES.put(CodegenTemplateVariable.ENTITY_ID_TYPE, pk.getJavaType());
        }
        // parse CodegenSetting
        CodegenSetting codegenSetting = JSON.parseObject(codegenSettingJson, CodegenSetting.class);
        List<Template> templates = codegenSetting.getTemplates();
        if (!ObjectUtils.isEmpty(templates)) {
            templates.stream().forEach(template -> {
                // entity name
                // template java suffix
                String templateName = String.format("%s%s", wrapper.getTableCamelName(), template.getJavaSuffix());
                // entity package name
                // .
                // template package
                // .
                // template name
                String templatePosition = String.format("%s%s%s%s%s", wrapper.getPackageName(), EnhanceCodegenConstant.POINT, template.getPackageName(), EnhanceCodegenConstant.POINT, templateName);

                CodegenTemplateVariable.VARIABLES.put(String.format(TEMPLATE_PACKAGE_NAME, template.getPackageName()), templateName);
                CodegenTemplateVariable.VARIABLES.put(String.format(TEMPLATE_PACKAGE_POSITION, template.getPackageName()), templatePosition);
            });
        }

        // replace variable
        codegenSettingJson = convertVariable(codegenSettingJson);
        // parse CodegenSetting
        return JSON.parseObject(codegenSettingJson, CodegenSetting.class);
    }

    /**
     * 遍历转换
     *
     * @param content 内容
     * @return converter after content
     */
    private static String convertVariable(String content) {
        Iterator<String> iterator = CodegenTemplateVariable.VARIABLES.keySet().iterator();
        while (iterator.hasNext()) {
            String key = iterator.next();
            content = content.replace(key, CodegenTemplateVariable.VARIABLES.get(key));
        }
        return content;
    }
}
