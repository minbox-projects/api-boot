## ApiBoot Message Push

`消息推送`是接口服务项目内不可或缺的一部分，用于向用户发送操作消息提醒、广告等。

`ApiBoot`内提供了`消息推送`的支持，目前集成的第三方组件为`极光推送`，`ApiBoot`遵循开箱即用的原则，所以在集成推送时仍然只需要简单的必要配置即可完成集成。

### 添加依赖

在`pom.xml`配置文件添加依赖如下所示：

```xml
//...
<dependencies>
  <!--ApiBoot Message Push Starter-->
  <dependency>
    <groupId>org.minbox.framework</groupId>
    <artifactId>api-boot-starter-message-push</artifactId>
  </dependency>
</dependencies>

<dependencyManagement>
  <dependencies>
    <!--ApiBoot 版本依赖-->
    <dependency>
      <groupId>org.minbox.framework</groupId>
      <artifactId>api-boot-dependencies</artifactId>
      <version>2.0.7.RELEASE</version>
      <scope>import</scope>
      <type>pom</type>
    </dependency>
  </dependencies>
</dependencyManagement>
//...
```



### 相关配置

| 配置名称                                   | 默认值 | 描述                                                     |
| ------------------------------------------ | ------ | -------------------------------------------------------- |
| `api.boot.push.production`                 | `true` | 配置IOS平台的推送环境，true：生产环境(AppStore下载的APP) |
| `api.boot.push.client.master-secret`       |        | 单客户端推送配置masterSecret                             |
| `api.boot.push.client.app-key`             |        | 单客户端推送配置appKey                                   |
| `api.boot.push.multiple.xxx.master-secret` |        | 多客户端推送配置masterSecret                             |
| `api.boot.push.multiple.xxx.app-key`       |        | 多客户端推送配置appKey                                   |

> 在上面`multiple`相关的配置内部是key->value结构配置，其中"xxx"用于配置@MessagePushSwitch("xxx")的value值

### 单环境推送

如果你的接口服务项目只集成推送到一个APP客户端，那么可以采用`单环境推送`的模式，该环境使用`api.boot.push.client`前缀的配置进行配置极光推送的两个秘钥，如下所示：

```yaml
# ApiBoot Config
api:
  boot:
    push:
      # 推送默认配置
      client:
        # 秘钥
        master-secret: xxx
        # appKey
        app-key: xxx
```

配置完成后我们就可以通过注入`ApiBootMessagePushService`接口来进行发送推送业务，如下所示：

```java
/**
 * ApiBoot Message Push Service
 */
@Autowired
private ApiBootMessagePushService apiBootMessagePushService;

/**
  * 安卓推送示例
  * @see org.minbox.framework.api.boot.plugin.message.push.model.PushClientConfig
  */
public void test() {
  apiBootMessagePushService.executePush(
    MessagePushBody.builder()
    // 推送安卓平台
    // 如需更换推送平台，可以查看PusherPlatform枚举定义
    .platform(PusherPlatform.ANDROID)
    // 标题
    .title("消息推送")
    // 内容
    .message("测试消息推送内容")
    // 接收人的别名(App端开发人员进行设置)
    .alias(Arrays.asList("xxxx"))
    .build()
  );
}
```

> `ApiBoot Message Push`如不配置使用`@MessagePushSwithc`注解时，使用默认的配置，也就是`api.boot.push.client`开头的相关配置信息。

### 多环境推送

在一个接口服务系统内，可能存在向多个APP客户端进行推送消息，那么这种情况`ApiBoot Message Push`也做了支持，内部通过`AOP`的方法切面进行处理。

多环境下的配置要做修改，如下所示：

```yaml
# ApiBoot Config
api:
  boot:
    push:
      multiple:
        # user app 推送配置
        user:
          master-secret: xxx
          app-key: xxxx
        # other app 推送配置
        other:
          master-secret: xxxxxx
          app-key: xxxxxx
```

在上面我们配置了两个APP客户端的推送信息，名称分别为`user`、`other`，这个名称对应`@MessagePushSwitch`注解的`value`值使用。

在上面配置了`user`、`other`两个客户端，使用具体的环境我们只需要在调用方法上添加`@MessagePushSwitch`注解，如下所示：

```java
// other app 推送
@MessagePushSwitch("other")
public void testOther() {
  //....
}

// user app 推送
@MessagePushSwitch("user")
public void testUser() {
  //....
}
```



### 指定别名推送

在推送消息时，`ApiBoot`提供了多种方式，其中可以根据`alias`别名进行推送，`alias`是由`App`前端开发人员在集成`SDK`时设置的值，这时就需要接口开发人员与APP开发人员的约定来完成推送逻辑，一般可以使用用户的编号来作为别名，根据别名推送如下所示：

```java
// 别名推送示例
public void test() {
  apiBootMessagePushService.executePush(
    MessagePushBody.builder()
    .platform(PusherPlatform.ANDROID)
    .title("消息推送")
    .message("测试消息推送内容")
    // 配置别名，别名可配置多个
    .alias(Arrays.asList("xxxx"))
    .build()
  );
}
```



### 指定分组推送

分组推送跟别名推送几乎一致，也是APP开发人员在调用`SDK`时对具体的用户划分的一个分组群体，向分组进行推送消息时，分组内的所有用户都可以收到。

如下所示：

```java
// 分组推送示例
public void test() {
  apiBootMessagePushService.executePush(
    MessagePushBody.builder()
    .platform(PusherPlatform.ANDROID)
    .title("消息推送")
    .message("测试消息推送内容")
    // 推送group1内的所有用户
    // 注意：分组优先级低于别名
    .tags(Arrays.asList("group1"))
    .build()
  );
}
```

> 如果同时配置别名、分组两种推送方式，则会使用别名

### 携带自定义参数推送

`ApiBoot Message Push`可以给APP端推送的消息携带自定义的扩展参数，让APP端做一些特殊的处理，携带扩展参数如下所示：

```java
// 自定义参数示例
public void test() {
  apiBootMessagePushService.executePush(
    MessagePushBody.builder()
    .platform(PusherPlatform.ANDROID)
    .title("消息推送")
    .message("测试消息推送内容")
    .alias(Arrays.asList("xxxx"))
    // 扩展参数
    .extras(new HashMap(1) {
      {
        put("role", "USER");
      }
    })
    .build()
  );
}
```



### 自定义推送提示声音

`ApiBoot Message Push`可以推送自定义提示的声音，当然也需要APP开发人员做配置才可以，如下所示：

```java
public void test() {
  apiBootMessagePushService.executePush(
    MessagePushBody.builder()
    .platform(PusherPlatform.ANDROID)
    .title("消息推送")
    .message("测试消息推送内容")
    .alias(Arrays.asList("xxxx"))
    // 手机系统默认提示音
    .sound("default")
    .build()
  );
}
```

`sound`方法可以设置与`APP`开发人员约定的声明唯一名称。

### 设置推送角标数值

推送消息时`APP`的角标数值是可以自定义的，当然我们一般都是叠加，如下所示：

```java
public void test() {
  apiBootMessagePushService.executePush(
    MessagePushBody.builder()
    .platform(PusherPlatform.ANDROID)
    .title("消息推送")
    .message("测试消息推送内容")
    .alias(Arrays.asList("xxxx"))
    .tags(Arrays.asList("group1"))
    // 角标数值，默认为+1
    .badge(999)
    .build()
  );
}
```

