<img src="https://apiboot.minbox.org/img/apiboot-colorful.png" height="100" width="426"/>

# ApiBoot：为组件化构建Api服务而生

[![Ci Builder](https://github.com/minbox-projects/api-boot/workflows/Ci%20Builder/badge.svg)](https://github.com/minbox-projects/api-boot/actions)
[![](https://codecov.io/gh/minbox-projects/api-boot/branch/master/graph/badge.svg)](https://codecov.io/gh/minbox-projects/api-boot)
[![](https://badges.gitter.im/api-boot/community.svg)](https://gitter.im/api-boot/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![](https://img.shields.io/badge/link-官方文档-green.svg?style=flat-square)](https://apiboot.minbox.io)
[![](https://img.shields.io/maven-central/v/org.minbox.framework/api-boot.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:org.minbox.framework)
[![](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://github.com/weibocom/motan/blob/master/LICENSE)
![](https://img.shields.io/badge/JDK-1.8+-green.svg)

## 什么是ApiBoot？

- 中文文档
- [English Document](https://github.com/minbox-projects/api-boot/blob/master/README.md)

ApiBoot是接口服务的落地解决方案，提供了一系列开箱即用的组件，通过封装来简化主流第三方框架的集成，从而提高开发者开发效率、学习成本、降低入门门槛，真正的实现开箱即用！！！

对SpringBoot简单了解的开发者就可以编写安全稳定的接口服务，可为移动端、网页端等多个端点提供丰富的安全接口。

ApiBoot依赖于SpringBoot，可以使用ApiBoot构建独立的Java应用程序。

愿景：

- 为Java开发者提供低门槛第三方框架集成解决方案，让复杂的框架集成使用的门槛更低。
- 开箱即用，内部封装了主流框架，只需添加依赖、简单配置即可使用。
- 各个组件可独立使用，不再冗余你的应用程序。
- 可简单快速的构建安全的restful资源接口服务。
- 可用于构建SpringCloud微服务服务实例。
- 为🇨🇳开源做贡献，希望开源框架可以帮助更多的开发者。

## 安装 & 入门

组件的使用请查看<a href="https://apiboot.minbox.org" target="_blank">官方参考文档</a>，开始使用请访问<a href="https://apiboot.minbox.org/zh-cn/docs/quick-start.html" target="_blank">第一个ApiBoot应用程序</a>

如果你是使用`Maven`来构建项目，你需要添加`ApiBoot`的固化版本依赖到你的`pom.xml`文件内，如下所示：

```xml
<dependencyManagement>
  <dependencies>
    <!--ApiBoot版本依赖-->
    <dependency>
      <groupId>org.minbox.framework</groupId>
      <artifactId>api-boot-dependencies</artifactId>
      <version>${lastVersion}</version>
      <scope>import</scope>
      <type>pom</type>
    </dependency>
  </dependencies>
</dependencyManagement>
```

> 注意：**lastVersion**需要替换为最新的ApiBoot版本，请访问<a href="https://apiboot.minbox.org/zh-cn/docs/version-rely.html" target="_blank">版本依赖 - 2.获取最新的ApiBoot依赖</a>查看。

版本依赖添加完成后，我们接下来就可以进行添加项目内所需要的ApiBoot组件，下面是使用分布式链路组件minbox-logging示例：

```xml
<dependencies>
  <!--ApiBoot MinBox Logging-->
  <dependency>
    <groupId>org.minbox.framework</groupId>
    <artifactId>api-boot-starter-logging</artifactId>
  </dependency>
</dependencies>
```

添加完组件我们就可以根据<a href="https://apiboot.minbox.org" target="_blank">官方参考文档</a>找到对应组件的文档进行配置使用了。

## 使用指南

作者针对每一个组件都提供了一系列的文章进行讲解，请访问 [ApiBoot开源框架各个组件的系列使用文章汇总](https://blog.yuqiyu.com/apiboot-all-articles.html) 进行学习。

## 获取帮助

如果在使用ApiBoot的过程中遇到了问题，你可以通过以下途径获取帮助！

- 查看<a href="https://apiboot.minbox.org" target="_blank">官方参考文档</a>，使用的每一个细节都会在文档中进行体现。
- 在<a href="https://gitee.com/minbox-projects/api-boot/issues" target="_blank">https://gitee.com/minbox-projects/api-boot/issues</a>提交你遇到的使用问题。

## 提交问题建议

每个人可能提出的问题不同，不过也会有一些相同的问题，如果您要提出问题，请遵循以下建议：

- 在提交问题之前，请搜索issues内是否已经有人提出过该问题。
- 如果您即将要提出的问题不存在，请<a href="https://gitee.com/minbox-projects/api-boot/issues" target="_blank">创建issue</a>。
- 请在提出您的问题时提供尽可能有关ApiBoot可能多的信息，比如：ApiBoot的版本、JDK、使用组件等
- 如果提问题时需要粘贴代码，请尽量使用markdown语法```转义符。

## 源码方式构建

ApiBoot正式版本都会发布到Maven Center，如果你想使用源码最新版本的ApiBoot（版本并未发布），可以直接通过源码的方式进行构建安装到本地使用，前提条件如下所示：

- 本地需要配置Maven环境变量，建议<a href="https://maven.apache.org/download.cgi" target="_blank">使用最新版</a>
- 本地需要配置JDK环境变量，<a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html" target="_blank">JDK1.8下载地址</a>
- 本地需要配置Git环境变量，<a href="https://git-scm.com/downloads" target="_blank">Git下载地址</a>

```sh
# 下载master分支源码到本地
➜ git clone https://github.com/minbox-projects/api-boot.git
# 进入api-boot源码根目录
➜ cd api-boot
# 执行安装
➜ mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dgpg.skip
```

## 模块

`ApiBoot`的源码构建使用到了多个模块，下面是一个快速概述：

### api-boot

`api-boot`模块是编译整个项目的根目录，所提供的能力如下所示：

- 提供项目统一版本`revision`的配置
- 提供项目编译时使用的公共插件（`flatten`、`cobertura`...）
- 提供项目编译时使用的`Maven`仓库配置
- 统一项目编译的JDK版本

### api-boot-autoconfigure

`api-boot-autoconfigure`是最为主要的核心模块，内部提供了**全部组件**的`自动化配置类`，这一点完全是利用`SpringBoot`所提供的[条件判断注解](https://docs.spring.io/spring-boot/docs/current/reference/htmlsingle/#boot-features-condition-annotations)，位于`resources/META-INF`目录下还提供了附加的配置参数元数据定义内容文件：`additional-spring-configuration-metadata.json`，项目启用时我们所看到的`banner`输出内容也位于该模块中。

### api-boot-dependencies

该模块的功能与`spring-boot-dependencies`一致，都是为了固化项目中所使用的依赖版本号，让我们在构建项目中可以很好地对某一个依赖进行升级，不再担心各个依赖之间版本不兼容的困扰。

### api-boot-parent

该模块继承自`api-boot-dependencies`，可直接使用固化版本后的依赖，是构建其他模块的统一父依赖。


### api-boot-starters

该模块下定义了开发过程中具体使用的`Starter`依赖，`Starter`依赖内不包含任何的框架代码，只有一个`pom.xml`文件，具体的自动化配置实现以及具体集成第三方的实现分别位于：`api-boot-autoconfigure`、`api-boot-plugins`模块内。

使用方式与`spring-boot-starter-xxx`一致，比如：在项目中集成限流组件，我们只需要在`pom.xml`中添加`api-boot-starter-rate-limiter`依赖即可，版本也无需添加，因为已经通过`api-boot-dependencies`模块进行了固化版本依赖。

### api-boot-tools

该模块会定义一些常用到的工具类，比如：`ApplicationContext`、`BeanFactory`等。

该模块同样是由`api-boot-autoconfigure`进行自动化配置，将部分工具类自动注册到`IOC`。

## 示例

项目源码中`api-boot-samples`模块提供了各个组件的使用示例，也可以结合我博客文章来学习使用，详情请访问：[ApiBoot基础教程](https://blog.yuqiyu.com/apiboot-all-articles.html)。

## 推荐开源项目

| 项目名称               | 作者     | 项目地址                                                     |
| ---------------------- | -------- | ------------------------------------------------------------ |
| 分布式链路日志开源框架 | 恒宇少年 | [https://gitee.com/minbox-projects/minbox-logging](https://gitee.com/minbox-projects/minbox-logging) |
| SpringBoot_v2          | bdj      | [https://gitee.com/bdj/SpringBoot_v2](https://gitee.com/bdj/SpringBoot_v2) |
| Pear Admin Layui       | 就眠仪式 | [https://gitee.com/Jmysy/Pear-Admin-Layui](https://gitee.com/Jmysy/Pear-Admin-Layui) |

## License

ApiBoot采用Apache2开源许可进行编写。

## 开源支持

<a href="https://www.jetbrains.com/?from=api-boot"><img src="https://apiboot.minbox.org/img/jetbrains.png" width="100" heith="100"/></a>
