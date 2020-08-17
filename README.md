<img src="https://apiboot.minbox.org/img/apiboot-colorful.png" height="100" width="426"/>

# ApiBoot: Born to build Api services as components


[![Ci Builder](https://github.com/minbox-projects/api-boot/workflows/Ci%20Builder/badge.svg)](https://github.com/minbox-projects/api-boot/actions)
[![](https://codecov.io/gh/minbox-projects/api-boot/branch/master/graph/badge.svg)](https://codecov.io/gh/minbox-projects/api-boot)
[![](https://badges.gitter.im/api-boot/community.svg)](https://gitter.im/api-boot/community?utm_source=badge&utm_medium=badge&utm_campaign=pr-badge)
[![](https://img.shields.io/badge/link-官方文档-green.svg?style=flat-square)](https://apiboot.minbox.io)
[![](https://img.shields.io/maven-central/v/org.minbox.framework/api-boot.svg?label=Maven%20Central)](https://search.maven.org/search?q=g:org.minbox.framework)
[![](https://img.shields.io/badge/License-Apache%202.0-green.svg)](https://github.com/weibocom/motan/blob/master/LICENSE)
![](https://img.shields.io/badge/JDK-1.8+-green.svg)

## What is ApiBoot ?

- [中文文档](https://github.com/minbox-projects/api-boot/blob/master/README.zh-CN.md)
- English Document

ApiBoot is a landing solution for interface services. 
It provides a series of out-of-the-box components to simplify the integration of mainstream third-party frameworks through encapsulation, 
thereby improving developer development efficiency, learning costs, and lowering the entry threshold. Ready to use! ! !

Developers who have a simple understanding of Spring Boot can write secure and stable interface services, which can provide rich security interfaces for multiple endpoints such as mobile terminals and web pages.

ApiBoot depends on SpringBoot and can be used to build standalone Java applications.

Vision:

- Provide low-threshold third-party framework integration solutions for Java developers, so that the threshold for the use of complex framework integration is lower.
- Out of the box, the mainstream framework is encapsulated inside, just add dependencies and simple configuration to use.
- Each component can be used independently, no longer redundant your application.
- Can easily and quickly build a secure restful resource interface service.
- It can be used to build SpringCloud microservice service instances.
- To contribute to open source, I hope that the open source framework can help more developers.

## Installation & Getting Started

Please check the use of components<a href="https://apiboot.minbox.org" target="_blank">Official Reference Document</a>，Get started please visit<a href="https://apiboot.minbox.org/zh-cn/docs/quick-start.html" target="_blank">The first ApiBoot application</a>

If you are using Maven to build the project, you need to add the version dependency of ApiBoot to your pom.xml file as follows:

```xml
<dependencyManagement>
  <dependencies>
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

> Note：**lastVersion**Need to be replaced with the latest ApiBoot version，Please visit<a href="https://apiboot.minbox.org/zh-cn/docs/version-rely.html" target="_blank">Version dependencies- 2. Get the latest ApiBoot dependencies</a>

After the version dependency is added, we can then add the required ApiBoot components in the project. The following is an example of using the distributed link component minbox-logging:

```xml
<dependencies>
  <!--ApiBoot MinBox Logging-->
  <dependency>
    <groupId>org.minbox.framework</groupId>
    <artifactId>api-boot-starter-logging</artifactId>
  </dependency>
</dependencies>
```

After adding the components, we can find the corresponding component documentation according to <a href="https://apiboot.minbox.org" target="_blank">official reference document</a> to configure and use.

## Get help

If you encounter problems while using ApiBoot, you can get help through the following channels!

- Check the <a href="https://apiboot.minbox.org" target="_blank">official reference document</a>, and every detail used will be reflected in the document.

## Create Issues

Everyone may ask different questions, but there will be some of the same questions. If you want to ask questions, please follow the suggestions below:

- Before submitting an issue, please search for any issues in the issues.
- Please provide as much information as possible about ApiBoot when asking your question, such as: ApiBoot version, JDK, use components, etc.
- If you need to paste the code when asking questions, please try to use the markdown syntax ``` escape character.

## Source code construction

The official version of ApiBoot will be released to Maven Center. If you want to use the latest version of ApiBoot (the version is not released), you can directly build and install it to local use through the source code. The prerequisites are as follows:

- Local Maven environment variables need to be configured, it is recommended to <a href="https://maven.apache.org/download.cgi" target="_blank">use the latest version</a>
- The local JDK environment variables need to be configured, <a href="https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html" target="_blank">JDK1.8 download address< /a>
- Local Git environment variables need to be configured, <a href="https://git-scm.com/downloads" target="_blank">Git download address</a>

```sh
# Download master branch source code to local
➜ git clone https://github.com/minbox-projects/api-boot.git
➜ cd api-boot
# install
➜ mvn clean install -Dmaven.test.skip=true -Dmaven.javadoc.skip=true -Dgpg.skip
```

## License

ApiBoot is written under the Apache2 open source license。

## OpenSource Support

<a href="https://www.jetbrains.com/?from=api-boot"><img src="https://apiboot.minbox.org/img/jetbrains.png" width="100" heith="100"/></a>

