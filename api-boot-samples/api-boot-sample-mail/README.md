`ApiBoot`的邮件组件使用`阿里云`的邮件服务作为基础支持，提供发送文本、HTML内容的邮件。

> 前提：需要到阿里云控制台开通邮件服务。



## 配置参数

| 参数名称                         | 默认值      | 是否必填 | 描述                                                   |
| -------------------------------- | ----------- | -------- | ------------------------------------------------------ |
| `api.boot.mail.access-key`       | 无          | 是       | 阿里云控制台提供的AccessKey                            |
| `api.boot.mail.access-secret`    | 无          | 是       | 阿里云控制台提供的AccessSecret                         |
| `api.boot.mail.account-name`     | 无          | 是       | 发送邮件时显示的邮箱账号                               |
| `api.boot.mail.reply-to-address` | true        | 否       | 使用阿里云管理控制台中配置的回复地址（必须验证状态）。 |
| `api.boot.mail.address-type`     | 1           | 否       | 取值范围 0~1: 0 为随机账号；1 为发信地址。             |
| `api.boot.mail.from-alias`       | ApiBootMail | 否       | 发送邮件时显示的邮箱昵称                               |
| `api.boot.mail.region`           | 无          | 是       | 阿里云的邮箱服务区域，根据购买区域配置                 |



## 发送邮件

`ApiBoot`提供两种内容的邮件发送，分别是：`Text`、`Html`，可应对多种发送场景使用，也可以一次性向多个邮箱发送相同的邮件内容。

#### ApiBootMailRequest

`ApiBootMailRequest`是发送邮件所需的请求对象，对象内的方法描述如下所示：

- `contentType`：邮件内容类型，`TEXT`(文本邮件)、`HTML`(网页邮件)，默认为`TEXT`
- `content`：邮件内容
- `toAddress`：接收邮件的邮箱列表
- `subject`：邮件主题
- `fromAlias`：发送人昵称，如果不传递则使用`api.boot.mail.from-alias`配置内容。

#### ApiBootMailResponse

- `success`：true：邮件发送成功，false：邮件发送失败

### 发送文本内容邮件

`ApiBoot`默认的内容类型为`Text`文本内容，所以我们在发送时可以不配置`内容类型`，只需要传递`邮件内容`、`收件邮箱`、`主题`即可，发送示例代码如下所示：

```java
// 构建发送对象
ApiBootMailRequest request = ApiBootMailRequest.builder()
  .content("ApiBoot Mail 发版啦~~")
  .toAddress(Arrays.asList("yuqiyu@vip.qq.com"))
  .subject("ApiBoot 新版本发布啦！")
  .build();
// 执行发送 & 获取相应对象
ApiBootMailResponse response = apiBootMailService.sendMail(request);
// 判断是否发送成功
if (response.isSuccess()) {
  System.out.println("邮件发送成功.");
}
```

### 发送HTML内容邮件

```java
// 构建发送对象
ApiBootMailRequest request = ApiBootMailRequest.builder()
  .contentType(ContentType.HTML)
  .content("<a href='http://blog.yuqiyu.com'>点击访问博客</a>")
  .toAddress(Arrays.asList("yuqiyu@vip.qq.com"))
  .subject("ApiBoot 新版本发布啦！")
  .build();
// 执行发送 & 获取相应对象
ApiBootMailResponse response = apiBootMailService.sendMail(request);
// 判断是否发送成功
if (response.isSuccess()) {
  System.out.println("邮件发送成功.");
}
```

