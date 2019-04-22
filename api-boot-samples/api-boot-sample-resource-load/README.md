## ApiBoot Resource Load
`ApiBoot Resource Load`是一款资源与业务完全分离的基础框架，可以整合`微服务(Feign、OpenFeign)`进行负载均衡读取固定类型、固定所属业务的资源信息，遵循一定的资源`存储规则`完成`自动化`资源读取、添加、更新、删除、缓存等。

### 使用场景

- 业务图片存储
- 业务音频、视频文件存储
- 业务文件
- 其他资源文件...

### 引入 ApiBoot Resource Load
在`pom.xml`配置文件内添加如下依赖：

```xml
<!--ApiBoot Resource Load-->
<dependency>
  <groupId>org.minbox.framework</groupId>
  <artifactId>api-boot-starter-resource-load</artifactId>
</dependency>
```
> `ApiBoot`所提供的依赖都不需要添加版本号，但是需要添加版本依赖，具体查看[ApiBoot版本依赖](https://github.com/hengboy/api-boot/blob/master/README.md#%E6%B7%BB%E5%8A%A0%E7%89%88%E6%9C%AC%E4%BE%9D%E8%B5%96)

### 了解ApiBootResourceStoreDelegate

`ApiBootResourceStoreDelegate`是一个资源数据读取的委托驱动接口，在使用`ApiBoot Resource Load`时，需要实现该接口完成资源的读取方法`loadResourceUrl()`，该方法的参数如下所示：

1. 第一个参数`sourceFieldValue`，是查询资源的业务编号，具体的配置详见下面的示例。
2. 第二个参数`resourceType`，是查询资源的类型，相同的业务编号下很有可能存在多种类型，比如：用户编号对应用户头像、用户封面等。

**ApiBootResourceStoreDelegate示例：**

```java
// 示例
@Service
public class ResourceLoadService implements ApiBootResourceStoreDelegate {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ResourceLoadService.class);

    @Override
    public List<String> loadResourceUrl(String sourceFieldValue, String resourceType) throws ApiBootException {
        logger.info("查询资源的业务逻辑字段值：{}", sourceFieldValue);
        logger.info("资源类型：{}", resourceType);
        return Arrays.asList(new String[]{"http://test.oss.com/111.png"});
    }
}
```

>  `loadResourceUrl`方法需要返回根据`resourceFieldValue`、`resourceType`字段查询到的资源列表。



### 内置注解


- `@ResourceLoad`

  标注方法需要进行`ApiBoot Resource Load`自动化读取资源信息，该注解必须添加，且只能添加在方法上。

- `@ResourceFields`

  `@ResourceField`注解的集合

- `@ResourceField`

  配置`@ResourceLoad`标注的方法具体有哪些字段需要进行资源的自动映射，参数解释如下所示：

  - `name`：查询资源后设置到类内`Field`的名称
  - `source`：查询资源所需的业务逻辑编号类内`Field`的名称
  - `type`：资源类型，自行定义
  - `isArray`：接收查询后资源的`Field`类型是否为`array`，true：array
  - `isList`：接收查询后资源的`Field`类型是否为`list`，true：list

### 单对象资源加载

资源加载一般都是实体类的方式进行返回的，下面我们先来创建一个实体类方便示例测试，如下所示：

```java
/**
  * 示例对象
  */
@Data
class SampleUserInfo {
  public SampleUserInfo(String userId, int age) {
    this.userId = userId;
    this.age = age;
  }

  private String userId;
  private String headImage;
  private String shortImage;
  private int age;
}
```

**返回值为单对象资源加载示例：**

```java
/**
  * 返回值为单个对象的示例
  *
  * @return
  */
@ResourceLoad
@ResourceFields({
  @ResourceField(name = "headImage", source = "userId", type = "HEAD_IMAGE"),
  @ResourceField(name = "shortImage", source = "userId", type = "SHORT_IMAGE")
})
public SampleUserInfo singleObjectSample() {
  return new SampleUserInfo("yuqiyu", 24);
}
```

>  在上面，我们配置读取两种类型的资源，分别是：`HEAD_IMAGE`、`SHORT_IMAGE`，而且配置的业务资源编号都是`userId`字段，这两个字段也就是会传递给`ApiBootResourceStoreDelegate#loadResourceUrl`方法作为参数。
>
> 其中`HEAD_IMAGE`读取到的资源路径设置到`SampleUserInfo`类内的`headImage`，`SHORT_IMAGE`读取到的资源路径设置到`SampleUserInfo`类内的`shortImage`字段。

> 注意：如果你的方法返回对象只有一个资源对象需要映射，可以单独配置使用`@ResourceField`注解。

### List集合资源加载

```java
/**
  * 返回值为list集合的示例
  *
  * @return
  */
@ResourceLoad
@ResourceFields({
  @ResourceField(name = "headImage", source = "userId", type = "HEAD_IMAGE"),
  @ResourceField(name = "shortImage", source = "userId", type = "SHORT_IMAGE")
})
public List<SampleUserInfo> listSample() {
  List<SampleUserInfo> users = new ArrayList();
  users.add(new SampleUserInfo("yuqiyu", 24));
  users.add(new SampleUserInfo("hengboy", 24));
  return users;
}
```

在上面，会为返回值`list`内的每一个`SampleUserInfo`对象进行设置查询的资源信息。

### Map集合资源加载

```java
/**
  * 返回值为map集合的示例
  *
  * @return
  */
@ResourceLoad
@ResourceFields({
  @ResourceField(name = "headImage", source = "userId", type = "HEAD_IMAGE"),
  @ResourceField(name = "shortImage", source = "userId", type = "SHORT_IMAGE")
})
public Map<String, SampleUserInfo> mapSample() {
  Map<String, SampleUserInfo> users = new HashMap<>(2);
  users.put("yuqiyu", new SampleUserInfo("yuqiyu", 24));
  users.put("hengboy", new SampleUserInfo("hengboy", 24));
  return users;
}
```

`Map`类型作为返回值时，其中注意`map -> value`必须是对象类型。

### 内存方式缓存资源

`ApiBoot Resource Load`提供了内存缓存的支持，相同类型、相同业务逻辑的资源数据只会从数据库内读取一次，除非执行资源的删除、更新，否则只能等到下次重启项目时进行更新。

### Redis方式缓存资源

`ApiBoot Resource Load`支持使用`redis`进行自动缓存资源数据，尽可能减轻数据库的读取压力。

#### 添加redis集成

使用`spring-boot-starter-data-redis`依赖来完成集成`redis`，在`pom.xml`添加依赖如下所示：

```xml
<!--Spring Boot Redis Starter-->
<dependency>
  <groupId>org.springframework.boot</groupId>
  <artifactId>spring-boot-starter-data-redis</artifactId>
</dependency>
```

添加依赖后需要进行相应的配置，在`application.yml`配置文件内，如下所示：

```yaml
spring:
  redis:
    password: 123456
```

在上面仅仅配置了连接密码，`spring-boot-starter-data-redis`配置文件提供了大多数的默认配置，可自行修改。



> `ApiBoot Resource Load`自动通过`RedisTemplate`来完成资源缓存，无需额外配置

### 表达式使用

`@ResourceField`注解的`name`、`source`属性都支持表达式使用，如下示例：

```java
@ResourceLoad(event = ResourceStoreEvent.INSERT)
@ResourceFields({
  @ResourceField(name = "#p2", source = "#p1.userId", type = ResourceConstant.SHORT_IMAGE),
  @ResourceField(name = "#p3", source = "#p0", type = ResourceConstant.HEAD_IMAGE)
})
public void insertResource(String userId, UserInfo userInfo, List<String> shortImage, String headImage) {
	//...
}
```

在上面代码中，`#p`是正则表达式所匹配的规则，`#p0`为第一个参数，`#p1`则为第二个参数，以此类推。

如果参数是对象类型，可以通过`#p1.userId`来指定`source`对应业务编号的字段。

`#p1.userId`则对应参数`userInfo`对象内的`userId`字段。

### 自动添加资源

配置资源的自动添加，是通过方法的参数值来进行实现，如下所示：

```java
@ResourceLoad(event = ResourceStoreEvent.INSERT)
@ResourceFields({
  @ResourceField(name = "#p1", source = "#p0.userId", type = ResourceConstant.SHORT_IMAGE),
  @ResourceField(name = "#p2", source = "#p0.userId", type = ResourceConstant.HEAD_IMAGE)
})
public void insertUser(UserInfo userInfo, List<String> shortImage, String headImage) {
  //..
}
```

在上面添加资源示例中，要注意，`@ResourceLoad`的`event`属性需要修改为`ResourceStoreEvent.INSERT`。

```java
@ResourceField(name = "#p2", source = "#p1.userId", type = ResourceConstant.SHORT_IMAGE)
```

- `name`：配置使用第二个参数作为`SHORT_IMAGE`类型的资源列表。
- `source`：注意`#p`索引是从`0`开始，所以这里`#p1`是第二个参数，`#p0.userId`配置使用第一个参数的`userId`作为业务逻辑编号字段。
- `type`：常量，自定义

>  解释：
>
> 当调用配置以上注解的方法时，会自动调用`ApiBootResourceStoreDelegate#addResource`方法完成资源的添加，在调用之前，需要从`#p0.userId`标注的参数对象中拿到`userId`的值作为资源编号，然后拿到`#p1`标注的参数值作为资源列表，最终拿到`type`的值一并传递给`ApiBootResourceStoreDelegate#addResource`方法，做自行保存处理。

```java
@ResourceField(name = "#p2", source = "#p0.userId", type = ResourceConstant.HEAD_IMAGE)
```

> 解释：
>
> 调用方法之前，需要拿到`#p0.userId`第一个参数作为业务逻辑编号，然后拿到`#p2`第三个参数作为资源列表，最后拿到`type`的值一并传递给`ApiBootResourceStoreDelegate#addResource`方法。



注意：`insertResource`方法配置了两个`@ResourceField`所以在执行时，会调用两次`ApiBootResourceStoreDelegate#addResource`方法。

### 自动更新资源

配置资源的自动更新，同样是通过方法的参数值来进行实现，如下所示：

```java
@ResourceLoad(event = ResourceStoreEvent.UPDATE)
@ResourceFields({
  @ResourceField(name = "#p1", source = "#p0", type = ResourceConstant.SHORT_IMAGE)
})
public void updateUserImage(String userId, List<String> shortImage) {
	//...
}
```

在上面示例中，配置`@ResourceField`注解则会完成，类型为`SHORT_IMAGE`且业务逻辑编号为第一个参数值的资源更新，而更新的资源列表则是第二个参数，也就是List集合。



具体解释与自动添加资源一致。

### 自动删除资源

配置资源的自动删除，同样是通过方法的参数值来进行实现，如下所示：

```java
@ResourceLoad(event = ResourceStoreEvent.DELETE)
@ResourceFields({
  @ResourceField(name = "shortImage", source = "#p0", type = ResourceConstant.SHORT_IMAGE),
  @ResourceField(name = "headImage", source = "#p0", type = ResourceConstant.HEAD_IMAGE)
})
public void deleteUser(String userId) {
	//删除用户逻辑
}
```

在上面代码中，删除用户时，会自动删除`userId`业务编号下的`SHORT_IMAGE`、`HEAD_IMAGE`等资源列表。



### 资源字段是List或者Array？

如果查询资源时，返回值对象接收资源的字段为List或者Array，可以通过`@ResourceField`字段的属性来配置，如下所示：

```java
// isList
@ResourceField(name = "shortImage", source = "userId", type = ResourceConstant.SHORT_IMAGE, isList = true)
// isArray
@ResourceField(name = "shortImage", source = "userId", type = ResourceConstant.SHORT_IMAGE, isArray = true)
```

