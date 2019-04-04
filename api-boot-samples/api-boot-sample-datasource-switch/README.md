## ApiBoot DataSource Switch

顾名思义，`DataSource Switch`是用于数据源选择切换的框架，这是一款基于`Spring AOP`切面指定注解实现的，通过简单的数据源注解配置就可以完成访问时的自动切换，`DataSource Switch`切换过程中是线程安全的。

### 添加依赖

使用`DataSource Switch`很简单，在`pom.xml`配置文件内添加如下依赖：

```xml
<!--ApiBoot DataSource Switch-->
<dependency>
  <groupId>org.minbox.framework</groupId>
  <artifactId>api-boot-starter-datasource-switch</artifactId>
</dependency>
```
`ApiBoot`所提供的依赖都不需要添加版本号，具体查看[ApiBoot版本依赖](https://github.com/hengboy/api-boot/blob/master/README.md#%E6%B7%BB%E5%8A%A0%E7%89%88%E6%9C%AC%E4%BE%9D%E8%B5%96)

### 集成数据源实现

目前`ApiBoot DataSource Switch`集成了`Druid`、`HikariCP`两种数据源实现依赖，在使用方面也有一定的差异，因为每一个数据源的内置参数不一致。

- `Druid`：参数配置前缀为`api.boot.datasource.druid`
- `HikariCP`：参数配置前缀为`api.boot.datasource.hikari`

**具体使用请查看下面功能配置介绍。**



### 配置参数

| 参数名                                                    | 参数默认值               | 是否必填 | 参数描述                                                     |
| --------------------------------------------------------- | ------------------------ | -------- | ------------------------------------------------------------ |
| `api.boot.datasource.primary`                             | master                   | 否       | 主数据源名称                                                 |
| `api.boot.datasource.druid.{poolName}.url`                | 无                       | 是       | 数据库连接字符串                                             |
| `api.boot.datasource.druid.{poolName}.username`           | 无                       | 是       | 用户名                                                       |
| `api.boot.datasource.druid.{poolName}.password`           | 无                       | 是       | 密码                                                         |
| `api.boot.datasource.druid.{poolName}.driver-class-name`  | com.mysql.cj.jdbc.Driver | 否       | 驱动类型                                                     |
| `api.boot.datasource.druid.{poolName}.filters`            | stat,wall,slf4j          | 否       | Druid过滤                                                    |
| `api.boot.datasource.druid.{poolName}.max-active`         | 20                       | 否       | 最大连接数                                                   |
| `api.boot.datasource.druid.{poolName}.initial-size`       | 1                        | 否       | 初始化连接数                                                 |
| `api.boot.datasource.druid.{poolName}.max-wait`           | 60000                    | 否       | 最大等待市场，单位：毫秒                                     |
| `api.boot.datasource.druid.{poolName}.validation-query`   | select 1 from dual       | 否       | 检查sql                                                      |
| `api.boot.datasource.druid.{poolName}.test-while-idle`    | true                     | 否       | 建议配置为true，不影响性能，并且保证安全性。申请连接的时候检测，如果空闲时间大于timeBetweenEvictionRunsMillis，执行validationQuery检测连接是否有效。 |
| `api.boot.datasource.druid.{poolName}.test-on-borrow`     | false                    | 否       | 申请连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 |
| `api.boot.datasource.druid.{poolName}.test-on-return`     | false                    | 否       | 归还连接时执行validationQuery检测连接是否有效，做了这个配置会降低性能。 |
| `api.boot.datasource.hikari.{poolName}.url`               | 无                       | 是       | 数据库连接字符串                                             |
| `api.boot.datasource.hikari.{poolName}.username`          | 无                       | 是       | 用户名                                                       |
| `api.boot.datasource.hikari.{poolName}.password`          | 无                       | 是       | 密码                                                         |
| `api.boot.datasource.hikari.{poolName}.driver-class-name` | com.mysql.cj.jdbc.Driver | 否       | 数据库驱动类全限定名                                         |
| `api.boot.datasource.hikari.{poolName}.property`          | 无                       | 否       | HikariCP属性配置                                             |

`HikariCP`数据源是`SpringBoot2.x`自带的，配置参数请访问[HikariCP](https://github.com/brettwooldridge/HikariCP)。

### 单主配置

`ApiBoot DataSource Switch`支持单主数据源的配置，`application.yml`配置文件如下所示：

```yaml
api:
  boot:
    datasource:
      # 配置使用hikari数据源
      hikari:
        # master datasource config
        master:
          url: jdbc:mysql://localhost:3306/test?characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: root
          password: 123456
```

### 修改主数据源名称

`master`为默认的主数据源的`poolName`，这里可以进行修改为其他值，不过需要对应修改`primary`参数，如下所示：

```yaml
api:
  boot:
    datasource:
      # 主数据源，默认值为master
      primary: main
      # 配置使用hikari数据源
      hikari:
        # main datasource config
        main:
          url: jdbc:mysql://localhost:3306/test?characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: root
          password: 123456
```

在上面配置主数据源的`poolName`修改为`main`。

### 主从配置

如果你的项目内存在`单主单从`、`一主多从`的配置方式，如下所示：

```yaml
api:
  boot:
    datasource:
      # 配置使用hikari数据源
      hikari:
        # master datasource config
        master:
          url: jdbc:mysql://localhost:3306/test?characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: root
          password: 123456
          # 默认值为【com.mysql.cj.jdbc.Driver】
          #driver-class-name: com.mysql.cj.jdbc.Driver
        # slave 1  datasource config
        slave_1:
          url: jdbc:mysql://localhost:3306/oauth2?characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: root
          password: 123456
        # slave 2  datasource config
        slave_2:
          url: jdbc:mysql://localhost:3306/resources?characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: root
          password: 123456
```

在上面是`一主多从`的配置方式，分别是`master`、`slave_1`、`slave_2`。

### 多类型数据库配置

`ApiBoot DataSource Switch`提供了一个项目内连接多个不同类型的数据库，如：`MySQL`、`Oracle`...等，如下所示：

```yaml
api:
  boot:
      # 主数据源，默认值为master
      primary: mysql
      hikari:
        mysql:
          url: jdbc:mysql://localhost:3306/test?characterEncoding=utf8&serverTimezone=Asia/Shanghai
          username: root
          password: 123456
        oracle:
          url: jdbc:oracle:thin:@172.16.10.25:1521:torcl
          username: root
          password: 123456
          driver-class-name: oracle.jdbc.driver.OracleDriver
```

在上面配置中，`master`主数据源使用的`MySQL`驱动连接`MySQL`数据库，而`slave`从数据源则是使用的`Oracle`驱动连接的`Oracle`数据库。

### 动态创建数据源

`ApiBoot DataSource Switch`内部提供了动态创建数据源的方法，可以通过注入`ApiBootDataSourceFactoryBean`来进行添加，如下所示：

```java
@Autowired
private ApiBootDataSourceFactoryBean factoryBean;

public void createNewDataSource() throws Exception {
  // 创建Hikari数据源
  // 如果创建Druid数据源，使用DataSourceDruidConfig
  DataSourceHikariConfig config = new DataSourceHikariConfig();
  // 数据库连接：必填
  config.setUrl("jdbc:mysql://localhost:3306/resources");
  // 用户名：必填
  config.setUsername("root");
  // 密码：必填
  config.setPassword("123456");
  // 数据源名称：必填(用于@DataSourceSwitch注解value值使用)
  config.setPoolName("dynamic");

  // 创建数据源
  DataSource dataSource = factoryBean.newDataSource(config);
  Connection connection = dataSource.getConnection();
  System.out.println(connection.getCatalog());
  connection.close();
}
```

### 自动切换

`ApiBoot DataSource Switch`的数据源自动切换主要归功于`Spring`的`AOP`，通过切面`@DataSourceSwitch`注解，获取注解配置的`value`值进行设置当前线程所用的数据源名称，从而通过`AbstractRoutingDataSource`进行数据源的路由切换。

我们沿用上面**一主多从**的配置进行代码演示，配置文件`application.yml`参考上面配置，代码示例如下：

#### 从数据源示例类

```java
@Service
@DataSourceSwitch("slave")
public class SlaveDataSourceSampleService {
    /**
     * DataSource Instance
     */
    @Autowired
    private DataSource dataSource;

    /**
     * 演示输出数据源的catalog
     *
     * @throws Exception
     */
    public void print() throws Exception {
        // 获取链接
        Connection connection = dataSource.getConnection();
        // 输出catalog
        System.out.println(this.getClass().getSimpleName() + " ->" + connection.getCatalog());
        // 关闭链接
        connection.close();
    }
}
```

#### 主数据源示例类

```java
@Service
@DataSourceSwitch("master")
public class MasterDataSourceSampleService {
    /**
     * DataSource Instance
     */
    @Autowired
    private DataSource dataSource;
    /**
     * Slave Sample Service
     */
    @Autowired
    private SlaveDataSourceSampleService slaveDataSourceSampleService;

    /**
     * 演示输出主数据源catalog
     * 调用从数据源类演示输出catalog
     * 
     * @throws Exception
     */
    public void print() throws Exception {
        Connection connection = dataSource.getConnection();
        System.out.println(this.getClass().getSimpleName() + " ->" + connection.getCatalog());
        connection.close();
        slaveDataSourceSampleService.print();
    }
}
```

- 在`主数据源`的示例类内，我们通过`@DataSourceSwitch("master")`注解的`value`进行定位连接`master`数据源数据库。
- 同样在`从数据库`的示例类内，我们也可以通过`@DataSourceSwitch("slave")`注解的`value`进行定位连接`slave`数据源数据库。

#### 单元测试示例

在上面的测试示例中，我们使用交叉的方式进行验证`数据源路由`是否可以正确的进行切换，可以编写一个单元测试进行验证结果，如下所示：

```java
@Autowired
private MasterDataSourceSampleService masterDataSourceSampleService;
@Test
public void contextLoads() throws Exception {
	masterDataSourceSampleService.print();
}
```

运行上面测试方法，结果如下所示：

```sh
MasterDataSourceSampleService ->test
2019-04-04 10:20:45.407  INFO 7295 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-2 - Starting...
2019-04-04 10:20:45.411  INFO 7295 --- [           main] com.zaxxer.hikari.HikariDataSource       : HikariPool-2 - Start completed.
SlaveDataSourceSampleService ->oauth2
```

单次执行数据源切换没有任何的问题，`master`数据源获取`catalog`输出后，调用`slave`示例类进行输出`catalog`。

>  `ApiBoot DataSource Switch`会在项目启动时首先初始化`master`节点`DataSource`实例，其他实例会在第一次调用时进行初始化。

### 压力性能测试

单次执行单线程操作没有问题，不代表多线程下不会出现问题，在开头说到过`ApiBoot DataSource Switch`是线程安全的，所以接下来我们来验证这一点，我们需要添加压力测试的依赖，如下所示：

```xml
<dependency>
  <groupId>org.databene</groupId>
  <artifactId>contiperf</artifactId>
  <version>2.3.4</version>
  <scope>test</scope>
</dependency>
```

接下来把上面的单元测试代码改造下，如下所示：

```java
// 初始化压力性能测试对象
@Rule
public ContiPerfRule i = new ContiPerfRule();

@Autowired
private MasterDataSourceSampleService masterDataSourceSampleService;
/**
* 开启500个线程执行10000次
*/
@Test
@PerfTest(invocations = 10000, threads = 500)
public void contextLoads() throws Exception {
  masterDataSourceSampleService.print();
}
```

>  测试环境：
>
> 硬件：i7、16G、256SSD
>
> 系统：OS X
>
> 整个过程大约是10秒左右，`ApiBoot DataSource Switch`并没有发生出现切换错乱的情况。

### 注意事项

1. 在使用`ApiBoot DataSource Switch`时需要添加对应数据库的依赖
2. 如果使用`Druid`连接池，不要配置使用`druid-starter`的依赖，请使用`druid`依赖。
3. 配置`poolName`时不要添加特殊字符、中文、中横线等。
