## ApiBoot Quartz

`ApiBoot`内部集成了`Quartz`，提供了`数据库方式`、`内存方式`的进行任务的存储，其中`数据库`方式提供了`分布式集群任务调度`，任务自动平滑切换执行节点。

### 引用ApiBoot Quartz

在`pom.xml`配置文件内添加，如下配置：

```xml
<!--ApiBoot Quartz-->
<dependency>
  <groupId>org.minbox.framework</groupId>
  <artifactId>api-boot-starter-quartz</artifactId>
</dependency>
```

>  备注：如果使用`ApiBoot Quartz`的内存方式，仅需要添加上面的依赖即可。

### 相关配置

| 参数名称                                                | 是否必填 | 默认值    | 描述                                       |
| ------------------------------------------------------- | -------- | --------- | ------------------------------------------ |
| `api.boot.quartz.job-store-type`                        | 否       | memory    | 任务存储源方式，默认内存方式               |
| `api.boot.quartz.scheduler-name`                        | 否       | scheduler | 调度器名称                                 |
| `api.boot.quartz.auto-startup`                          | 否       | true      | 初始化后是否自动启动调度程序               |
| `api.boot.quartz.startup-delay`                         | 否       | 0         | 初始化完成后启动调度程序的延迟。           |
| `api.boot.quartz.wait-for-jobs-to-complete-on-shutdown` | 否       | false     | 是否等待正在运行的作业在关闭时完成。       |
| `api.boot.quartz.overwrite-existing-jobs`               | 否       | false     | 配置的作业是否应覆盖现有的作业定义。       |
| `api.boot.quartz.properties`                            | 否       |           | Quartz自定义的配置属性，具体参考quartz配置 |
| `api.boot.quartz.jdbc`                                  | 否       |           | 配置数据库方式的Jdbc相关配置               |

### 内存方式

`ApiBoot Quartz`在使用内存方式存储任务时，不需要做配置调整。

### 数据库方式

需要在`application.yml`配置文件内修改`api.boot.quartz.job-store-type`参数，如下所示：

```yaml
api:
  boot:
    quartz:
      # Jdbc方式
      job-store-type: jdbc
```

#### Quartz所需表结构

`Quartz`的数据库方式内部通过`DataSource`获取数据库连接对象来进行操作数据，所操作数据表的表结构是固定的，`ApiBoot`把`Quartz`所支持的所有表结构都进行了整理，访问[Quartz支持数据库建表语句列表](<https://github.com/hengboy/api-boot/tree/master/api-boot-samples/api-boot-sample-quartz/src/main/resources/schemas>)查看，复制执行对应数据库语句即可。

### 创建任务类

我们只需要让新建类集成`QuartzJobBean`就可以完成创建一个任务类，如下简单示例：

```java
/**
 * 任务定义示例
 * 与Quartz使用方法一致，ApiBoot只是在原生基础上进行扩展，不影响原生使用
 * <p>
 * 继承QuartzJobBean抽象类后会在项目启动时会自动加入Spring IOC
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-28 17:26
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class DemoJob extends QuartzJobBean {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(DemoJob.class);

    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        logger.info("定时任务Job Key ： {}", context.getJobDetail().getKey());
        logger.info("定时任务执行时所携带的参数：{}", JSON.toJSONString(context.getJobDetail().getJobDataMap()));
        //...处理逻辑
    }
}
```



#### 任务参数

在任务执行时传递参数是必须的，`ApiBoot Quartz`提供了比较方便的传递方式，不过最终`Quartz`会把传递的值都会转换为`String`类型数据。

#### 任务Key默认值

`ApiBoot Quartz`的`newJob`方法所创建的定时任务，如果在不传递`Job Key`参数时，会默认使用`UUID`随机字符串作为`Job Key`以及`Trigger Key`。

#### 自定义任务开始时间

任务开始时间可以通过`startAtTime`方法进行设置，在不设置的情况下，任务创建完成后会立刻执行。

#### Cron 表达式任务

创建`Cron`类型任务如下所示：

```java
String jobKey = apiBootQuartzService.newJob(ApiBootCronJobWrapper.Context()
                        .jobClass(DemoJob.class)
                        .cron("0/5 * * * * ?")
                        .param(
                                ApiBootJobParamWrapper.wrapper().put("param", "测试"))
                        .wrapper());
```

Cron 表达式任务由`ApiBootCronJobWrapper`类进行构建。

上面的`DemoJob`任务类将会每隔`5秒`执行一次。

#### Loop 重复任务

`Loop`循环任务，当在不传递重复执行次数时，不进行重复执行，仅仅执行一次，如下所示：

```java
String jobKey = apiBootQuartzService.newJob(
                ApiBootLoopJobWrapper.Context()
                        // 参数
                        .param(
                                ApiBootJobParamWrapper.wrapper()
                                        .put("userName", "恒宇少年")
                                        .put("userAge", 24)
                        )
                        // 每次循环的间隔时间，单位：毫秒
                        .loopIntervalTime(2000)
                        // 循环次数
                        .repeatTimes(5)
                        // 开始时间，10秒后执行
                        .startAtTime(new Date(System.currentTimeMillis() + 10000))
                        // 任务类
                        .jobClass(DemoJob.class)
                        .wrapper()
        );
```

Loop 任务由`ApiBootLoopJobWrapper`类进行构建。

上面的定时任务将会重复执行`5次`，连上自身执行的一次也就是会执行`6次`，每次的间隔时间为`2秒`，在任务创建`10秒`后进行执行。

#### Once 一次性任务

`Once`一次性任务，任务执行一次会就会被自动释放，如下所示：

```java

Map paramMap = new HashMap(1);
paramMap.put("paramKey", "参数值");

String jobKey = apiBootQuartzService.newJob(
  ApiBootOnceJobWrapper.Context()
  .jobClass(DemoJob.class)
  // 参数
  .param(
    ApiBootJobParamWrapper.wrapper()
    .put("mapJson", JSON.toJSONString(paramMap))
  )
  // 开始时间，2秒后执行
  .startAtTime(new Date(System.currentTimeMillis() + 2000))
  .wrapper()
);

```

Once 任务由`ApiBootOnceJobWrapper`类进行构建。

在参数传递时可以是对象、集合，不过需要进行转换成字符串才可以进行使用。

### 暂停任务执行

任务在执行过程中可以进行暂停操作，通过`ApiBoot Quartz`提供的`pauseJob`方法就可以很简单的实现，当然暂停时需要传递`Job Key`，`Job Key`可以从创建任务方法返回值获得。

暂停任务如下所示：

```java
// 暂停指定Job Key的任务
apiBootQuartzService.pauseJob(jobKey);
// 暂停多个执行中任务
apiBootQuartzService.pauseJobs(jobKey,jobKey,jobKey);
```

### 恢复任务执行

任务执行完暂停后，如果想要恢复可以使用如下方式：

```java
// 恢复指定Job Key的任务执行
apiBootQuartzService.resumeJob(jobKey);
// 恢复多个暂停任务
apiBootQuartzService.resumeJobs(jobKey,jobKey,jobKey);
```



### 修改Cron表达式

修改`Cron`表达式的场景如下：

- 已创建 & 未执行
- 已创建 & 已执行

修改方法如下所示：

```java
// 修改执行Job Key任务的Cron表达式
apiBootQuartzService.updateJobCron(jobKey, "0/5 * * * * ?");
```



### 删除任务

想要手动释放任务时可以使用如下方式：

```java
// 手动删除指定Job Key任务
apiBootQuartzService.deleteJob(jobKey);
// 手动删除多个任务
apiBootQuartzService.deleteJobs(jobKey,jobKey,jobKey);
```

删除任务的顺序如下：

1. 暂停触发器
2. 移除触发器
3. 删除任务
