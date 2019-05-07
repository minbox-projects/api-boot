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

| 配置名称                                | 默认值 | 描述                               |
| --------------------------------------- | ------ | ---------------------------------- |
| `api.boot.rate-limiter.interceptor-url` | /**    | 数组类型，可配置多个限流的路径地址 |

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

