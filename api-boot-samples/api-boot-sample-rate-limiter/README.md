​    

## ApiBoot RateLimiter

`ApiBoot RateLimiter`基于拦截器的实现，封装了`Google`的`令牌桶方式`的限流实现，可通过注解配置每个接口的流量`QPS`（每秒允许的流量请求）。

> 注意：目前不支持分布式系统的流量限制，会在下个版本进行更新添加。

### 添加依赖

```xml
<dependencies>
  <!--ApiBoot RateLimiter-->
  <dependency>
    <groupId>org.minbox.framework</groupId>
    <artifactId>api-boot-starter-rate-limiter</artifactId>
  </dependency>
  <!--省略其他依赖-->
</dependencies>
<dependencyManagement>
  <dependencies>
    <!--ApiBoot 版本依赖-->
    <dependency>
      <groupId>org.minbox.framework</groupId>
      <artifactId>api-boot-dependencies</artifactId>
      <version>2.0.7.RELEASE</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```

### 相关配置

| 配置名称                                  | 默认值 | 描述                               |
| ----------------------------------------- | ------ | ---------------------------------- |
| `api.boot.rate-limiter.interceptor-url`   | /**    | 数组类型，可配置多个限流的路径地址 |
| `api.boot.rate-limiter.enable-global-qps` | false  | 是否开启全局限流配置               |
| `api.boot.rate-limiter.global-qps`        | 10     | 全局限流QPS                        |

### QPS定义

限流注解`RateLimiter`配置使用如下所示：

```java
@RestController
@RequestMapping(value = "/resource")
public class ResourceSampleController {
    /**
     * QPS = 10
     * 配置该接口每秒只允许访问10次
     *
     * @return
     */
    @RequestMapping(value = "/")
    @RateLimiter(QPS = 10)
    public UserInfo user() {
        return new UserInfo("admin");
    }
}
```

### 单服务限流

对于单个服务的场景使用限流时，`ApiBoot RateLimiter`内部使用`Google Guava`采用令牌桶的方式实现，具体源码实现详见`GoogleGuavaRateLimiter`类。

> 引用`ApiBoot RateLimiter`的项目如果并未添加`spring-boot-starter-data-redis`依赖，项目中并未初始化`RedisTemplate`时则会采用`Google Guava`方式来进行限流。

### 分布式服务限流

如果你采用微服务、负载均衡的方式进行部署服务时，单服务限流是无法完成预期的效果的，`ApiBoot RateLimiter`内部集成了`Redis`方式来自动完成限流，使用`Redis`后期也利于扩展，如果应用程序过大也可以搭建`Redis 集群`完成限流。

`ApiBoot RateLimiter`内部的`Redis`使用了`SpringCloud GateWay`官方用的`Lua`脚本来保证限流的原子性、线程安全性。

使用`Redis`方式很简单，只需要在项目中添加`Redis`依赖后进行配置后`ApiBoot RateLimiter`就会自动通过`RedisTemplate`来操作`Lua`脚本，步骤如下所示：

##### 第一步：添加Redis依赖

```xml
<!--Redis-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

##### 第二步：配置Redis

```yaml
spring:
  # redis 相关配置
  redis:
    password: 123456
```

>  如果你所使用的`Redis`有密码，这里需要进行配置，其他配置参数使用默认值。

#### 测试限流

当我们访问被限流的接口时，`ApiBoot RateLimiter`会自动向`Redis`写入两条数据，`key`如下所示：

```java
// redis key list
qps_rate_limiter.{/test/}.timestamp
qps_rate_limiter.{/test/}.tokens
```

- `qps_rate_limiter.{/test/}.timestamp`：存放上一次请求的时间戳
- `qps_rate_limiter.{/test/}.tokens`：存放剩余的请求令牌数量

`key`的格式为`qps_rate_limiter.{xxx}.timestamp`、`qps_rate_limiter.{xxx}.tokens`。

其中`xxx`为请求接口的路径。

**当然单服务实例也可以使用`Redis`方式**

### 配置中心支持

为了保证有前瞻性突发流量的处理，`ApiBoot RateLimiter`支持了外部配置中心，在配置中心修改接口限流`QPS`后会实时更新到应用程序内。

#### Nacos 配置中心

如果你想使用`Nacos Config`作为`ApiBoot RateLimiter`的外部`QPS`配置方式，那么我们需要进行下面的步骤来完成：

##### 添加依赖

```xml
<!--Nacos-->
<dependency>
  <groupId>com.alibaba.boot</groupId>
  <artifactId>nacos-config-spring-boot-starter</artifactId>
</dependency>
```

> `ApiBoot`内置了`Nacos Starter`的版本，这里无需添加版本号。

##### 配置参数

我们添加了`Nacos`依赖，那需要进行配置`Nacos Server`的地址，具体`Nacos Server`怎么安装，可以去看我的博客文章或者直接访问[Nacos 快速开始](<https://nacos.io/zh-cn/docs/quick-start.html>)

`Nacos在application.yml`配置如下所示：

```yaml
# nacos config
nacos:
  config:
    server-addr: 127.0.0.1:8848
```

`8848`是`Nacos Server`的默认端口号，这里直接配置使用。

##### 测试动态修改

我们在启动应用程序时，`ApiBoot RateLimiter`会自动从`Nacos`读取`DATA_ID`为`apiboot-rate-limiter-config`的配置，分组为`DEFAULT_GROUP`的`Properties`类型的配置文件，然后**缓存到本地**。

如果`Nacos`并没有该配置文件，则在**第一次访问接口时自动创建**。

![](<http://image.yuqiyu.com/apiboot/rate-limiter-nacos-config.png>)

`apiboot-rate-limiter-config`配置文件是`Properties`形式的存储的，那么`Key`的生成规则则是把请求接口地址的`/`替换为了`.`，如下所示：

```properties
.resource.=50
.resource.detail=10
```

下面我们来测试修改配置后，`ApiBoot RateLimiter`是否可以实时生效，将`.resource.`修改为20后，控制台会打印如下日志信息：

```sh
Update local current RateLimiter configuration is complete，content：{.resource.=20, .resource.detail=10}
```

当我们再次访问`/resource/`接口时就会发现限流的`QPS`一秒内只允许访问`20`次。

