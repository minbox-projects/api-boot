## ApiBoot Alibaba Sms

`ApiBoot`的短信服务模块是由`阿里云`的国际短信服务提供的，支持国内和国际快速发送验证码、短信通知和推广短信。

> 前提：需要到阿里云控制台申请开通短信服务。

### 引入ApiBoot Alibaba Sms

在`pom.xml`配置文件内添加如下：

```xml
<!--ApiBoot Alibaba Sms-->
<dependency>
  <groupId>org.minbox.framework</groupId>
  <artifactId>api-boot-starter-alibaba-sms</artifactId>
</dependency>
```

`ApiBoot`所提供的依赖都不需要添加版本号，具体查看[ApiBoot版本依赖](https://github.com/hengboy/api-boot/blob/master/README.md#%E6%B7%BB%E5%8A%A0%E7%89%88%E6%9C%AC%E4%BE%9D%E8%B5%96)

### 配置参数列表

| 配置参数                          | 参数介绍                 | 默认值  | 是否必填 |
| --------------------------------- | ------------------------ | ------- | -------- |
| `api.boot.sms.access-key-id`      | RAM账号的AccessKey ID    | 空      | 是       |
| `api.boot.sms.access-key-secret`  | RAM账号Access Key Secret | 空      | 是       |
| `api.boot.sms.sign-name`          | 短信签名                 | 空      | 是       |
| `api.boot.sms.connection-timeout` | 短信发送连接超时时长     | 10000   | 否       |
| `api.boot.sms.read-timeout`       | 短信接收消息连接超时时长 | 10000   | 否       |
| `api.boot.sms.profile`            | 短信区域环境             | default | 否       |

### 发送短信

在`ApiBoot Alibaba Sms`模块内置了`ApiBootSmsService`接口实现类，通过`send`方法即可完成短信发送，如下所示：

```java
		/**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootSmsTest.class);

    @Autowired
    private ApiBootSmsService apiBootSmsService;

    @Test
    public void sendSms() {

        // 参数
        ApiBootSmsRequestParam param = new ApiBootSmsRequestParam();
        param.put("code", "192369");

        // 请求对象
        ApiBootSmsRequest request = ApiBootSmsRequest.builder().phone("171xxxxx").templateCode("SMS_150761253").param(param).build();

        // 发送短信
        ApiBootSmsResponse response = apiBootSmsService.send(request);
        logger.info("短信发送反馈，是否成功：{}", response.isSuccess());
    }
```

>  短信模板code自行从阿里云控制台获取。

如果在阿里云控制台定义的短信模板存在多个参数，可以通过`ApiBootSmsRequestParam#put`方法来进行挨个添加，该方法返回值为`ApiBootSmsRequestParam`本对象。

#### 多参数

多参数调用如下所示：

```java
// 参数
ApiBootSmsRequestParam param = new ApiBootSmsRequestParam();
param.put("code", "192369").put("name", "测试名称");
```

#### 发送结果反馈

执行短信发送后会返回`ApiBootSmsResponse`实例，通过该实例即可判断短信是否发送成功。