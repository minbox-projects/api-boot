## 使用文档
文档请访问`ApiBoot`官网，地址：
<a href="http://apiboot.minbox.io/zh-cn/docs/api-boot-swagger.html" target="_blank">ApiBoot Swagger使用文档</a>

## 使用本示例

本地运行项目，访问：[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

### 获取AccessToken
示例项目中集成了`Spring Security`、`OAuth2`，根据`application.yml`的配置内容可以通过下面的方式获取`AccessToken`。
```shell script
curl -X POST -u 'ApiBoot:ApiBootSecret' -d 'grant_type=password&username=hengboy&password=123456' localhost:8080/oauth/token
...
{
  "additionalInformation": {
    "jti": "e846482b-d6f0-485b-9bd8-24b99735fbca"
  },
  "expiration": "2020-07-06 11:36:34",
  "expired": false,
  "expiresIn": 7194,
  "refreshToken": {
    "expiration": "2020-08-05 09:36:34",
    "value": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpIl0sInVzZXJfbmFtZSI6Imhlbmdib3kiLCJzY29wZSI6WyJhcGkiXSwiYXRpIjoiZTg0NjQ4MmItZDZmMC00ODViLTliZDgtMjRiOTk3MzVmYmNhIiwiZXhwIjoxNTk2NTkxMzk0LCJhdXRob3JpdGllcyI6WyJST0xFX2FwaSJdLCJqdGkiOiJlYThmMjQ0ZS1hZGQwLTRkNjAtYjQ2NC1jNTEyMmEyY2VlZDQiLCJjbGllbnRfaWQiOiJBcGlCb290In0.5IcHIbdPj7Tp_DWydSXWZCkBPK7JR8E03nr5JRzY5d0"
  },
  "scope": [
    "api"
  ],
  "tokenType": "bearer",
  "value": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOlsiYXBpIl0sInVzZXJfbmFtZSI6Imhlbmdib3kiLCJzY29wZSI6WyJhcGkiXSwiZXhwIjoxNTk0MDA2NTk0LCJhdXRob3JpdGllcyI6WyJST0xFX2FwaSJdLCJqdGkiOiJlODQ2NDgyYi1kNmYwLTQ4NWItOWJkOC0yNGI5OTczNWZiY2EiLCJjbGllbnRfaWQiOiJBcGlCb290In0.H9kVh3DMzMLzVM1lVrmVCWUXZoLV22nRwc2bfPSQ3As"
}
```
