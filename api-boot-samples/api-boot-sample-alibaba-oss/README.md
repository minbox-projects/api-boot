## ApiBoot Alibaba Oss

`ApiBoot`添加快速集成`Aliyun`的对象存储服务`Oss`，提供常用的文件操作方法，当然也提供自定义扩展，以致于满足绝大数业务场景，并且通过扩展可以实现上传文件进度条、下载文件进度条、存储空间操作、静态网站托管、访问日志、防盗链、分片上传、追加上传、断点续传等等。

### 引入ApiBoot Alibaba Oss

在`pom.xml`配置文件内添加依赖，如下所示：

```xml
<!--ApiBoot Alibaba Oss-->
<dependency>
	<groupId>org.minbox.framework</groupId>
	<artifactId>api-boot-starter-alibaba-oss</artifactId>
</dependency>
```

`ApiBoot`所提供的依赖都不需要添加版本号，具体查看[ApiBoot版本依赖](https://github.com/hengboy/api-boot/blob/master/README.md#%E6%B7%BB%E5%8A%A0%E7%89%88%E6%9C%AC%E4%BE%9D%E8%B5%96)

### 配置参数列表

| 配置参数                         | 参数介绍                                                     | 默认值 | 是否必填 |
| -------------------------------- | ------------------------------------------------------------ | ------ | -------- |
| `api.boot.oss.region`            | oss所属地域                                                  | 空     | 是       |
| `api.boot.oss.bucket-name`       | oss存储空间名称                                              | 空     | 是       |
| `api.boot.oss.access-key-id`     | 阿里云账户accessKeyId                                        | 空     | 是       |
| `api.boot.oss.access-key-secret` | 阿里云账户accessKeySecret                                    | 空     | 是       |
| `api.boot.oss.domain`            | oss存储空间所绑定的自定义域名，如果不配置，上传文件成功后返回默认格式化的文件访问路径 | 空     | 否       |

### 上传文件

在使用`ApiBoot Oss`时，只需要注入`ApiBootOssService`类就可以完成默认方法的使用，如下所示：

```java
@Autowired
private ApiBootOssService apiBootOssService;
```

#### 流上传

```java
/**
  * 流方式上传
  */
@Test
public void uploadBytes() {
  ApiBootObjectStorageResponse response = apiBootOssService.upload("admin.txt", "admin".getBytes());
  logger.info("文件名称：{}", response.getObjectName());
  logger.info("文件访问路径：{}", response.getObjectUrl());
}
```

#### 本地文件上传

```java
/**
* 本地文件上传
*/
@Test
public void uploadFile() {
	ApiBootObjectStorageResponse response = apiBootOssService.upload("logo.png", "/Users/yuqiyu/Downloads/logo.png");
	logger.info("文件名称：{}", response.getObjectName());
	logger.info("文件访问路径：{}", response.getObjectUrl());
}
```

#### 文件流上传

```java
/**
* 文件流方式上传
*
* @throws Exception
*/
@Test
public void uploadInputStream() throws Exception {
	FileInputStream inputStream = new FileInputStream(new File("/Users/yuqiyu/Downloads/logo.png"));
	ApiBootObjectStorageResponse response = apiBootOssService.upload("测试.png", inputStream);
	logger.info("文件名称：{}", response.getObjectName());
	logger.info("文件访问路径：{}", response.getObjectUrl());
}
```

> 通过文件的输入流完成对象存储文件的上传

#### 下载文件

```java
/**
  * 下载文件
  */
@Test
public void download() {
  apiBootOssOverrideService.download("测试.png", "/Users/yuqiyu/Downloads/测试.png");
}
```

>  在上面的示例中，文件会自动下载到`/Users/yuqiyu/Downloads/`目录下，文件名称为`测试.png`。

#### 删除文件

```java
/**
* 删除文件示例
*/
@Test
public void delete() {
	apiBootOssOverrideService.delete("测试.png");
}
```

>  删除对象存储空间内的文件时只需要传递文件名即可。

#### MultipartFile 上传文件

如果你是通过`SpringMvc`提供的`MultipartFile`对象进行上传文件，可以通过如下示例进行上传：

```java
MultipartFile multipartFile = ..;
// 流方式上传
ApiBootObjectStorageResponse responseByte = apiBootOssService.upload("测试.png", multipartFile.getBytes());
// 文件输入流方式上传
ApiBootObjectStorageResponse responseIs = apiBootOssService.upload("测试.png", multipartFile.getInputStream());
```

### 分片上传

`ApiBoot`集成了分片上传，只需要一个方法就可以把大文件进行分片上传，`ApiBoot`会自动根据`partSize`进行整理分片数量，如下所示：

```java
ApiBootObjectStorageResponse response = apiBootOssService
                .multipartUpload(
  "初识ApiBoot.mp4",
  //路径方式："/Users/yuqiyu/Downloads/fa2a664e-f827-cfab-7323-3583b3ffd00c.mp4",
  // 文件对象方式
  new File("/Users/yuqiyu/Downloads/fa2a664e-f827-cfab-7323-3583b3ffd00c.mp4"),
  PartSize.MB);
System.out.println("文件名称：" + response.getObjectName());
System.out.println("文件路径：" + response.getObjectUrl());
```

方法参数描述：

1. 文件名称
2. 本地文件路径
3. 每一个`part`的大小，可以直接使用`ApiBoot`提供的`PartSize`接口常量来进行计算。

### 自定义扩展

`ApiBoot Alibaba Oss`提供的方法毕竟是有限的，因此`ApiBoot`提供了自定义的扩展方式，让使用者可以根据`Oss`官方文档进行扩展，包含上传文件进度条、下载文件进度条、存储空间操作、静态网站托管、访问日志、防盗链、分片上传、追加上传、断点续传等等。

自定义扩展首先需要创建类并继承`ApiBootOssService`，如下所示：

```java
//...
public class ApiBootOssOverrideService extends ApiBootOssService {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootOssOverrideService.class);

    /**
     * 实现父类构造函数
     *
     * @param endpoint        外网节点
     * @param bucketName      存储空间名称
     * @param accessKeyId     阿里云账号授权Id
     * @param accessKeySecret 阿里云账号授权Secret
     * @param domain          自定义域名
     */
    public ApiBootOssOverrideService(String endpoint, String bucketName, String accessKeyId, String accessKeySecret, String domain) {
        super(endpoint, bucketName, accessKeyId, accessKeySecret, domain);
    }

    /**
     * 创建bucket存储
     *
     * @param bucketName 存储名称
     */
    public void createBucket(String bucketName) {
        OSSClient ossClient = getOssClient();
        Bucket bucket = ossClient.createBucket(bucketName);
        logger.info("新创建存储空间名称：{}", bucket.getName());
        logger.info("新创建存储空间所属人：{}", bucket.getOwner().getDisplayName());
        closeOssClient(ossClient);
    }
}
```

如上`createBucket`方法所示`ApiBootOssService`内部提供了获取`OssClient`以及关闭`OssClient`连接的方法，可以直接调用。

**扩展生效**

我们自定义的扩展，需要将实例放入`SpringIOC`容器内，方便我们在使用处进行注入，要注意，由于构造函数参数的原因，无法直接通过`@Service`或者`@Component`注解进行标注，需要通过如下方式：

```java
//...
@Bean
@ConditionalOnMissingBean
ApiBootOssOverrideService apiBootOssOverrideService(ApiBootOssProperties apiBootOssProperties) {
  return new ApiBootOssOverrideService(apiBootOssProperties.getRegion().getEndpoint(), apiBootOssProperties.getBucketName(), apiBootOssProperties.getAccessKeyId(), apiBootOssProperties.getAccessKeySecret(), apiBootOssProperties.getDomain());
}
```

`ApiBootOssProperties`属性配置类，是`ApiBoot`内置的，可以在任意地方进行注入，这里目的只是为了拿到相关配置进行构造参数实例化使用。

