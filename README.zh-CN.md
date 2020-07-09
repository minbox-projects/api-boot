<img src="https://apiboot.minbox.org/img/apiboot-colorful.png" height="100" width="426"/>

# ApiBoot：为组件化构建Api服务而生

[![](https://www.travis-ci.org/hengboy/api-boot.svg?branch=master)](https://www.travis-ci.org/github/minbox-projects/api-boot)
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

如果你是使用Maven来构建项目，你需要添加ApiBoot的版本依赖到你的pom.xml文件内，如下所示：

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

## 组件使用

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

## 分支

ApiBoot由于需要支持SpringBoot的不同分支的代码（SpringBoot版本相互不兼容的问题导致），因此也对应创建的分支。

- `2.1.x` 对应SpringBoot的`2.1.0`及以上版本。
- `2.2.x` 对应SpringBoot的`2.2.0`及以上版本。

## 源码方式构建

ApiBoot正式版本都会发布到Maven Center，如果你想使用源码最新版本的ApiBoot（版本并未发布），可以直接通过源码的方式进行构建安装到本地使用，前提条件如下所示：

- 本地需要配置Maven环境变量，建议<a href="https://maven.apache.org/download.cgi" target="_blank">使用最新版</a>
- 本地需要配置JDK环境变量，<a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html" target="_blank">JDK1.8下载地址</a>
- 本地需要配置Git环境变量，<a href="https://git-scm.com/downloads" target="_blank">Git下载地址</a>

```sh
# 下载master分支源码到本地
➜ git clone https://gitee.com/minbox-projects/api-boot.git
# 进入api-boot源码根目录
➜ cd api-boot
# 执行安装
➜ mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dgpg.skip
```

## 组件

ApiBoot内部提供了多个组件，下面简单的介绍组件的用途：

### 链路日志组件

内部通过集成整合<a href="https://gitee.com/minbox-projects/minbox-logging" target="_blank">minbox-logging</a>开源框架，提供零侵入式分布式链路日志分析框架的使用，可应用到SpringCloud微服务应用内，提供Admin端点进行采集日志、分析日志、日志告警通知、服务性能分析等。通过Admin Ui可查看实时链路日志信息、在线业务服务列表。

### 安全组件

内部通过整合SpringSecurity + OAuth2两大常用资源安全、认证授权框架来保证接口服务的安全性，**内存方式只需要添加几行配置就可以完成整合**，ApiBoot针对SpringSecurity提供了两种查询用户的方式：memory（内存）、jdbc（数据库）。而针对OAuth2则提供了三种方式存储生成后的Token以及Client信息：memory、jdbc、redis等。

>  可以直接配置使用JWT格式化OAuth2生成的Token.

### 接口文档组件

通过集成`Swagger2`来完成文档的侵入式生成，侵入式文档后期会被替代，`ApiBoot Security Oauth`已默认排除`swagger2`相关的资源路径（如果自定义集成了OAuth2或者SpringSecurity需要手动排除Swagger资源路径）。

### ORM组件（数据库持久化组件）

ApiBoot通过封装Mybatis提供了一款吸取JPA、Mybatis、QueryDSL等主流ORM框架的优点整合框架<a href="https://gitee.com/hengboy/mybatis-enhance" target="_blank">mybatis-enhance</a>，内部提供了常用CRUD方法，无需编写一行SQL就可以完成对数据的持久化操作，提供方法命名规则查询、动态查询等新特性。

### 代码生成组件

ApiBoot为mybatis-enhance提供了专门定制代码生成插件，可为数据库表对应生成数据实体生成、动态查询实体，mybatis-enhance-codegen是一款Maven Plugin，配置数据库链接信息后可根据配置过滤指定的表、全部表、指定前缀的表进行生成。

### 多数据源组件

ApiBoot针对多数据源切换的场景提供了自动化切换的方式，内部提供了两种数据源类型的配置实现，分别是：Druid、HikariCP，通过在类、方法上配置注解的方式切面自动切换为配置数据源，如未配置则使用默认的数据源。

### 自动分页组件

ApiBoot针对Mybatis持久化框架的使用者提供了自动化分页的插件<a href="https://gitee.com/hengboy/mybatis-pageable" target="_blank">mybatis-pageable</a>，这是一款基于Mybatis Plugin实现的插件，根据传递的分页参数可以自动查询出分页信息，如：总页数、每页条数、当前页码、是否存在上一页、下一页等。

> 支持主流的12种数据库。

### 限流组件

ApiBoot针对单应用、分布式集群应用分别提供了一种限流的方式，针对单应用提供了Google的令牌桶方式限流，而针对服务集群环境提供了Redis Lua方式。

> 限流配置秒级QPS访问量。

### 阿里云OSS组件

集成阿里云OSS提供的SDK来完成文件的上传、下载等方法实现，开箱即用。

### 阿里云短信组件

集成阿里云提供的SMS服务，简单配置即可完成短信发送，覆盖全球的短信服务，友好、高效、智能的互联化通讯能力，帮助企业迅速搭建客户触达通道。

### 阿里云邮件组件

集成阿里云提供的Mail服务，简单配置后，通过ApiBoot提供的封装类几行代码就可以完成邮件发送。

## 示例

ApiBoot提供了每一个组件的使用示例，在源码[api-boot-samples](https://gitee.com/minbox-projects/api-boot/tree/master/api-boot-samples)目录下根据组件名归类。

## 使用指南

请访问作者博客<a href="http://blog.yuqiyu.com" target="_blank">恒宇少年De成长之路</a>获取ApiBoot、MinBox开源组织内开源框架的最新的使用指南。



## 推荐开源项目

| 项目名称               | 作者     | 项目地址                                                     |
| ---------------------- | -------- | ------------------------------------------------------------ |
| 分布式链路日志开源框架 | 恒宇少年 | [https://gitee.com/minbox-projects/minbox-logging](https://gitee.com/minbox-projects/minbox-logging) |
| SpringBoot_v2          | bdj      | [https://gitee.com/bdj/SpringBoot_v2](https://gitee.com/bdj/SpringBoot_v2) |
| Pear Admin Layui       | 就眠仪式 | [https://gitee.com/Jmysy/Pear-Admin-Layui](https://gitee.com/Jmysy/Pear-Admin-Layui) |



## 联系作者

作者公众号：

<img src="http://blog.yuqiyu.com/images/mp.jpg" style="width:200px;"/>

扫码关注公众号请回复**ApiBoot**获取作者微信号。

## License

ApiBoot采用Apache2开源许可进行编写。

## 开源支持
<img src="https://apiboot.minbox.org/img/jetbrains.png" width="100" heith="100"/>
