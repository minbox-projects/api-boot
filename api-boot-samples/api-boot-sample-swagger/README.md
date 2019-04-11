## ApiBoot Swagger

`ApiBoot`通过整合`Swagger2`完成自动化接口文档生成，只需要一个简单的注解我们就可以实现文档的开启，而且文档上面的所有元素都可以自定义配置，通过下面的介绍来详细了解`ApiBoot Swagger`的简易之处。

### 引入ApiBoot Swagger

在`pom.xml`配置文件内通过添加如下依赖进行集成：

```xml
<!--ApiBoot Swagger-->
<dependency>
	<groupId>org.minbox.framework</groupId>
	<artifactId>api-boot-starter-swagger</artifactId>
</dependency>
```

>  注意：`ApiBoot`所提供的依赖都不需要添加版本号，但是需要添加版本依赖，具体查看[ApiBoot版本依赖](https://github.com/hengboy/api-boot/blob/master/README.md#%E6%B7%BB%E5%8A%A0%E7%89%88%E6%9C%AC%E4%BE%9D%E8%B5%96)

### @EnableApiBootSwagger

在添加依赖后需要通过`@EnableApiBootSwagger`注解进行开启`ApiBoot Swagger`相关的配置信息自动化构建，可以配置在XxxApplication入口类上，也可以是配置类，让`SpringBoot`加载到即可。

### 相关配置

| 配置参数                                    | 参数介绍                           | 默认值                                                       |
| ------------------------------------------- | :--------------------------------- | :----------------------------------------------------------- |
| `api.boot.swagger.enable`                   | 是否启用                           | true                                                         |
| `api.boot.swagger.title`                    | 文档标题                           | ApiBoot快速集成Swagger文档                                   |
| `api.boot.swagger.description`              | 文档描述                           | ApiBoot通过自动化配置快速集成Swagger2文档，仅需一个注解、一个依赖即可。 |
| `api.boot.swagger.base-package`             | 文档扫描的package                  | XxxApplication同级以及子级package                            |
| `api.boot.swagger.version`                  | 文档版本号                         | api.boot.version                                             |
| `api.boot.swagger.license`                  | 文档版权                           | ApiBoot                                                      |
| `api.boot.swagger.license-url`              | 文档版权地址                       | https://github.com/hengboy/api-boot                          |
| `api.boot.swagger.contact.name`             | 文档编写人名称                     | 恒宇少年                                                     |
| `api.boot.swagger.contact.website`          | 文档编写人主页                     | http://blog.yuqiyu.com                                       |
| `api.boot.swagger.contact.email`            | 文档编写人邮箱地址                 | jnyuqy@gmail.com                                             |
| `api.boot.swagger.authorization.name`       | 整合Oauth2后授权名称               | ApiBoot Security Oauth 认证头信息                            |
| `api.boot.swagger.authorization.key-name`   | 整合Oauth2后授权Header内的key-name | Authorization                                                |
| `api.boot.swagger.authorization.auth-regex` | 整合Oauth2后授权表达式             | ^.*$                                                         |
以上是目前版本的所有配置参数，大多数都存在默认值，可自行修改。

### 整合ApiBoot Security Oauth

如果你的项目添加了`Oauth2`资源保护，在`Swagger`界面上访问接口时需要设置`AccessToken`到`Header`才可以完成接口的访问，`ApiBoot Security Oauth`默认开放`Swagger`所有相关路径，如果项目内并非通过`ApiBoot Security Oauth2`来做安全认证以及资源保护，需要自行开放`Swagger`相关路径。

整合`ApiBoot Security Oauth`很简单，访问[ApiBoot Security Oauth](https://github.com/hengboy/api-boot/blob/master/api-boot-samples/api-boot-sample-security-oauth-jwt/README.md) 查看。

### 携带Token访问Api

启动添加`ApiBoot-Swagger`依赖的项目后，访问[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)页面查看`Swagger`所生成的全部文档，页面右侧可以看到**Authorize**，点击后打开配置`AccessToken`的界面，配置的`AccessToken`必须携带类型，如：`Bearer 0798e1c7-64f4-4a2f-aad1-8c616c5aa85b`。

>  注意：通过`ApiBoot Security Oauth`所获取的`AccessToken`类型都为`Bearer`。



### ApiBoot Swagger + @EnableWebMvc = 404？

`@EnableWebMvc`注解使用后会覆盖掉`SpringBoot`默认的资源映射路径`/static`, `/public`, `META-INF/resources`, `/resources`等存放静态资源的目录。

`Swagger`资源都存在在`META-INF/resources`目录下，所以会出现404的情况，只需要添加自定义的资源映射处理就可以再次访问默认资源路径下的文件，如下所示：

```java
@Configuration
@EnableWebMvc
public class ApiBootResourceConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/**").addResourceLocations("/");
    }
}
```

