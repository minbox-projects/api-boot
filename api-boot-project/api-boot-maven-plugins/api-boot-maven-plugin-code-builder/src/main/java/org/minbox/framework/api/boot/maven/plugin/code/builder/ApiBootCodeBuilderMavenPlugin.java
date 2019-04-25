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

package org.minbox.framework.api.boot.maven.plugin.code.builder;

import com.gitee.hengboy.builder.common.CodeBuilderProperties;
import com.gitee.hengboy.builder.common.enums.DbTypeEnum;
import com.gitee.hengboy.builder.common.enums.EngineTypeEnum;
import com.gitee.hengboy.builder.core.configuration.BuilderConfiguration;
import com.gitee.hengboy.builder.core.invoke.CodeBuilderInvoke;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Execute;
import org.apache.maven.plugins.annotations.LifecyclePhase;
import org.apache.maven.plugins.annotations.Mojo;
import org.apache.maven.plugins.annotations.Parameter;

import java.util.List;

/**
 * ApiBoot Code Builder Maven Plugin
 * Entity classes can be generated from any freemarker template
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-23 16:32
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Mojo(name = "generator", defaultPhase = LifecyclePhase.COMPILE)
@Execute(phase = LifecyclePhase.COMPILE)
public class ApiBootCodeBuilderMavenPlugin extends AbstractMojo {
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
    @Parameter
    private String dbPassword;
    /**
     * database driver class name
     */
    @Parameter
    private String dbDriverClassName;
    /**
     * ignore class prefix
     * for example：sys_menu_info -> MenuInfo
     */
    @Parameter
    private String ignoreClassPrefix;

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
     * Generate by specified prefix
     * for example：sys_
     * matching：sys_menu_info、sys_role_info、sys_button_info
     */
    @Parameter
    private String generatorByPattern;
    /**
     * Driver template type
     */
    @Parameter(defaultValue = "FREEMARKER")
    private EngineTypeEnum engineType;

    /**
     * project base dir
     */
    @Parameter(defaultValue = "${basedir}")
    private String projectBaseDir;

    /**
     * Directory address of builder configuration file
     */
    @Parameter(defaultValue = "target.classes.templates.builder")
    private String builderDir;

    /**
     * Target root address after file generation
     */
    @Parameter(defaultValue = "target.generated-sources.java")
    private String targetDir;
    /**
     * Automatic Generation of Configuration Information Entities
     */
    @Parameter
    private BuilderConfiguration builder;

    /**
     * execute generator
     *
     * @throws MojoExecutionException mojo execute exception
     * @throws MojoFailureException   mojo failure exception
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        try {
            /*
             * Entity parameters required to assemble code generators
             * Pass to the required configuration class
             */
            CodeBuilderProperties codeBuilderProperties = CodeBuilderProperties.builder()
                    .execute(execute)
                    .dbType(dbType)
                    .dbName(dbName)
                    .dbUserName(dbUserName)
                    .dbPassword(dbPassword)
                    .dbUrl(dbUrl)
                    .dbDriverClassName(dbDriverClassName)
                    .tables(tables)
                    .generatorByPattern(generatorByPattern)
                    .ignoreClassPrefix(ignoreClassPrefix)
                    .projectBaseDir(projectBaseDir)
                    .builderDir(builderDir)
                    .targetDir(targetDir)
                    .builder(builder)
                    .engineTypeEnum(engineType)
                    .build();

            // Execute code generation
            CodeBuilderInvoke.invoke(codeBuilderProperties);

        } catch (Exception e) {
            getLog().error("Invoke have errors ：" + e.getMessage());
        }
    }
}
