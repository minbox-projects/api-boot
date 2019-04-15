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



>  如果你有想要的使用方式，你就可以提交issuse！！！
