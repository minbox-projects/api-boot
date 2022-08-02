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

package org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen;

import com.alibaba.fastjson.JSON;
import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.common.enums.DbTypeEnum;
import com.gitee.hengboy.builder.core.database.DataBase;
import com.gitee.hengboy.builder.core.database.DataBaseFactory;
import com.gitee.hengboy.builder.core.database.model.Table;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;
import org.codehaus.plexus.util.FileUtils;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.ClassBuilder;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.ClassBuilderFactory;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.impl.DynamicEntityClassBuilder;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.impl.EntityClassBuilder;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.builder.wrapper.ClassBuilderWrapper;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.mapping.TypeMapping;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.setting.SshProxySetting;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.template.CodegenFile;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.template.CodegenTemplate;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.tools.CamelTools;
import org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.writer.JavaClassWriter;
import org.minbox.framework.ssh.agent.AgentConnection;
import org.minbox.framework.ssh.agent.DefaultAgentConnection;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import java.io.File;
import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.EnhanceCodegenConstant.EMPTY_STRING;

/**
 * ApiBoot Mybatis Enhance Codegen Maven Plugin
 * 1. Generating data entities
 * 2. Generating dynamic query entities
 * 3. Generate Mapper files
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-29 16:28
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Mojo(name = "generator", defaultPhase = LifecyclePhase.COMPILE)
@Execute(phase = LifecyclePhase.COMPILE)
public class ApiBootMybatisEnhanceCodegen extends AbstractMojo {
    /**
     * Whether to execute automatically
     * Default not to execute
     */
    @Parameter(defaultValue = "false", required = true)
    private boolean execute;
    /**
     * database type
     */
    @Parameter(defaultValue = "MySQL")
    private DbTypeEnum dbType;
    /**
     * database name
     */
    @Parameter(required = true)
    private String dbName;
    /**
     * database url
     * for example：jdbc:mysql://xxx.xx.xx.xxx:3306
     */
    @Parameter(required = true)
    private String dbUrl;
    /**
     * database username
     */
    @Parameter(required = true)
    private String dbUserName;
    /**
     * database password
     */
    @Parameter(required = true)
    private String dbPassword;
    /**
     * database driver class name
     */
    @Parameter
    private String dbDriverClassName;
    /**
     * table name pattern
     * Used to get a list of tables
     */
    @Parameter(defaultValue = "%")
    private String tableNamePattern;
    /**
     * Specify the list of generated tables
     * for example：
     * <tables>
     * <table>sys_menu_info</table>
     * <table>sys_role_info</table>
     * </tables>
     */
    @Parameter
    private List<String> tables;
    /**
     * project base dir
     */
    @Parameter(defaultValue = "${basedir}")
    private String projectBaseDir;

    /**
     * generator class target dir
     */
    @Parameter(defaultValue = "target.generated-sources.java")
    private String targetDir;
    /**
     * java file package name
     */
    @Parameter(required = true)
    private String packageName;
    /**
     * ignore table name prefix
     */
    @Parameter
    private String ignoreTablePrefix;
    /**
     * ignore column prefix
     */
    @Parameter
    private boolean ignoreColumnPrefix = false;
    /**
     * The mapping relationship between {@link java.sql.Types} and java types
     */
    @Parameter
    private List<TypeMapping> typeMappings;
    /**
     * 是否在类上追加Lombok "@Data"注解
     */
    @Parameter
    private boolean appendLombokData = false;
    /**
     * 是否在类上追加Lombok "@Accessors(chain=true)"注解
     */
    @Parameter
    private boolean appendLombokAccessorsChain = false;
    /**
     * 是否启用ssh代理方式连接数据库
     */
    @Parameter
    private boolean enableSshProxy = false;
    /**
     * Ssh Proxy代理参数设置
     */
    @Parameter
    private SshProxySetting sshProxySetting;
    /**
     * 是否在控制台输出字段信息
     */
    @Parameter
    private boolean outputFieldInTheConsole = false;
    /**
     * 是否在表名前追加schema
     */
    @Parameter
    private boolean appendSchemaBeforeTableName = true;

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        if (!execute) {
            getLog().warn("Automatic code generation is not turned on. If you need to generate entity classes, configure 【execute=true】");
            return;
        }
        AgentConnection agentConnection = null;
        if (enableSshProxy) {
            agentConnection = this.startingSshProxy(sshProxySetting);
        }
        // code builder properties
        CodeBuilderProperties codeBuilderProperties = CodeBuilderProperties.builder().dbType(dbType).dbName(dbName).dbUrl(dbUrl).dbUserName(dbUserName).dbPassword(dbPassword).dbDriverClassName(dbDriverClassName).build();

        // get database instance by DbTypeEnum
        DataBase dataBase = DataBaseFactory.newInstance(codeBuilderProperties);

        // load codegen.setting.json
        String codegenSetting = loadCodegenSetting();

        List<String> tableNames = ObjectUtils.isEmpty(tables) ? getTableNames(dataBase) : tables;
        if (!ObjectUtils.isEmpty(tableNames)) {
            getLog().info("The entity class corresponding to the " + tableNames.size() + " tables will be generated，and the table name list: " +
                JSON.toJSONString(tableNames));
        }
        tableNames.stream().forEach(tableName -> {
            LocalDateTime startGetInfoTime = LocalDateTime.now();
            // get table
            Table table = dataBase.getTable(tableName);
            getLog().info(EMPTY_STRING);
            getLog().info("----------------< Start Generating " + tableName + "(" + table.getRemark() + ") >----------------");
            getLog().info("Get information is complete, time consuming " +
                Duration.between(startGetInfoTime, LocalDateTime.now()).toMillis() + " ms.");

            // execute the builders created
            // ClassBuilder implementation classes can be added
            Class<? extends ClassBuilder>[] builders = new Class[]{
                EntityClassBuilder.class,
                DynamicEntityClassBuilder.class
            };

            // ignore table prefix
            if (!StringUtils.isEmpty(ignoreTablePrefix)) {
                tableName = tableName.replace(ignoreTablePrefix, EMPTY_STRING);
            }
            // formatter class name
            String className = CamelTools.upper(tableName);

            // Encapsulated objects needed to perform generation
            ClassBuilderWrapper wrapper = ClassBuilderWrapper.builder().packageName(packageName)
                .tableCamelName(className).table(table).typeMappings(typeMappings).ignoreColumnPrefix(ignoreColumnPrefix)
                .appendLombokData(appendLombokData).appendLombokAccessorsChain(appendLombokAccessorsChain)
                .outputFieldInTheConsole(outputFieldInTheConsole).appendSchemaBeforeTableName(appendSchemaBeforeTableName)
                .dataBaseName(dbName)
                .build();

            // execute generator
            Arrays.stream(builders).forEach(builderClass -> {
                ClassBuilder builder = ClassBuilderFactory.newInstance(builderClass, wrapper);
                if (!ObjectUtils.isEmpty(builder)) {
                    // setting class prefix
                    wrapper.setTableCamelName(builder.getDefaultPrefix() + wrapper.getTableCamelName());

                    // setting prefix dir
                    if (!ObjectUtils.isEmpty(builder.getPrefixDir())) {
                        wrapper.setPackageName(wrapper.getPackageName() + EnhanceCodegenConstant.POINT + builder.getPrefixDir());
                    }
                    // class file path
                    String classPath = getNewClassPath(wrapper.getTableCamelName(), builder.getPrefixDir());
                    // class content
                    String classContent = builder.getClassContent();
                    // invoke content write
                    JavaClassWriter.writeToJavaFile(classPath, classContent);
                    getLog().info("Generated class [" + classPath + "] at " + LocalDateTime.now());
                }
            });

            // generator java file with codegen.setting.json
            if (!ObjectUtils.isEmpty(codegenSetting)) {
                // cover old data
                wrapper.setTableCamelName(className);
                wrapper.setPackageName(packageName);

                CodegenTemplate codegenTemplate = new CodegenTemplate(wrapper, codegenSetting);

                // formatter all template java file
                List<CodegenFile> files = codegenTemplate.formatJavaFiles();

                if (!ObjectUtils.isEmpty(files)) {
                    files.stream().forEach(file -> {
                        getLog().info("generator 【" + file.getFileName() + "】 by codegen template.");
                        // generator package dir & return full file path
                        String fullFilePath = getNewClassPath(file.getFileName(), file.getPackageName());
                        if (!StringUtils.isEmpty(file.getJavaContent()) && !StringUtils.isEmpty(fullFilePath)) {
                            // invoke content write
                            JavaClassWriter.writeToJavaFile(fullFilePath, file.getJavaContent());
                        }
                    });
                }
            }
        });
        // 断开ssh连接
        if (!ObjectUtils.isEmpty(agentConnection)) {
            agentConnection.disconnect();
            getLog().info("SSH Connection disconnected successfully");
        }
    }

    private AgentConnection startingSshProxy(SshProxySetting setting) {
        AgentConnection connection = null;
        try {
            connection = new DefaultAgentConnection(setting);
            connection.connect();
        } catch (Exception e) {
            getLog().error("SSH Connection：" + setting.getServerIp() + ":" + setting.getForwardTargetPort() + "，try agent failure.", e);
        }
        return connection;
    }

    /**
     * load codegen.setting.json parse to CodegenSetting entity
     * read codegen.setting.json content
     */
    private String loadCodegenSetting() {
        try {
            // formatter codegen.setting.json path
            String settingJsonPath = String.format("%s%s", EnhanceCodegenConstant.CLASSES_PATH.replace(EnhanceCodegenConstant.POINT, File.separator), EnhanceCodegenConstant.SETTING_JSON);
            File file = new File(projectBaseDir + settingJsonPath);
            if (file.exists()) {
                return FileUtils.fileRead(file);
            }
        } catch (Exception e) {
            getLog().error(e);
        }
        return null;
    }

    /**
     * get table name list
     *
     * @param dataBase data base instance
     * @return table name list
     */
    private List<String> getTableNames(DataBase dataBase) {
        return dataBase.getTableNames(tableNamePattern);
    }

    /**
     * get generator dir
     *
     * @param prefixDir prefix dir
     * @return generator dir
     */
    private String getGeneratorDir(String prefixDir) {
        // java file base dir
        String baseDir = String.format("%s%s%s", projectBaseDir, File.separator, targetDir.replace(EnhanceCodegenConstant.POINT, File.separator));
        // package dir
        String packageDir = String.format("%s%s%s%s", baseDir, File.separator, packageName.replace(EnhanceCodegenConstant.POINT, File.separator), File.separator + prefixDir);
        // create dirs
        File dir = new File(packageDir);
        dir.mkdirs();

        return packageDir;
    }

    /**
     * get generator class path
     *
     * @param entityClassName entity class name
     * @param prefixDir       prefix dir
     * @return class path
     */
    private String getNewClassPath(String entityClassName, String prefixDir) {
        return String.format("%s%s%s%s", getGeneratorDir(prefixDir), File.separator, entityClassName, EnhanceCodegenConstant.JAVA_SUFFIX);
    }
}
