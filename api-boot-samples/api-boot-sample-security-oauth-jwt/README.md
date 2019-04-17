## ApiBoot Security Oauth

### 引入 ApiBoot Security Oauth

在`pom.xml`配置文件内添加如下：

```xml
<!--ApiBoot Security Oauth-->
<dependency>
	<groupId>org.minbox.framework</groupId>
	<artifactId>api-boot-starter-security-oauth-jwt</artifactId>
</dependency>
```

> `ApiBoot`所提供的依赖都不需要添加版本号，但是需要添加版本依赖，具体查看[ApiBoot版本依赖](https://github.com/hengboy/api-boot/blob/master/README.md#%E6%B7%BB%E5%8A%A0%E7%89%88%E6%9C%AC%E4%BE%9D%E8%B5%96)

### 配置参数列表

`ApiBoot`在整合`SpringSecurity`、`Oauth2`时把配置参数进行了分离，配置列表如下所示：

#### 整合SpringSecurity配置列表

| 配置名称                                          | 介绍                                                         | 默认值                                                       | 生效方式    |
| ------------------------------------------------- | ------------------------------------------------------------ | ------------------------------------------------------------ | ----------- |
| `api.boot.security.away`                          | SpringSecurity读取用户的方式，默认为内存方式                 | memory                                                       | all         |
| `api.boot.security.auth-prefix`                   | 拦截的接口路径前缀，如：/api/users就会被默认拦截             | /api/**                                                      | memory/jdbc |
| `api.boot.security.users`                         | 配置用户列表，具体使用查看[内存方式介绍](https://github.com/hengboy/api-boot/tree/master/api-boot-samples/api-boot-sample-security-oauth-jwt#%E5%86%85%E5%AD%98%E6%96%B9%E5%BC%8F%E9%BB%98%E8%AE%A4%E6%96%B9%E5%BC%8F) | 无                                                           | memory      |
| `api.boot.security.ignoring-urls`                 | `Spring Security`所排除的路径，默认排除Swagger、Actuator相关路径前缀 | /v2/api-docs<br />/swagger-ui.html<br />/swagger-resources/configuration/security<br />/META-INF/resources/webjars/**<br />/swagger-resources<br />/swagger-resources/configuration/ui<br />/actuator/** | memory/jdbc |
| `api.boot.security.enable-default-store-delegate` | 仅在Jdbc方式生效                                             | true                                                         | jdbc        |
| `api.boot.security.disable-http-basic`            | 禁用basic http                                               | true                                                         | memory/jdbc |
| `api.boot.security.disable-csrf`                  | 禁用csrf                                                     | true                                                         | memory/jdbc |



#### 整合Oauth2配置列表

| 配置名称                       | 介绍                               | 默认值                | 绑定away    |
| ------------------------------ | ---------------------------------- | --------------------- | ----------- |
| `api.boot.oauth.away`          | Oauth存储Token、读取Client信息方式 | memory                | all         |
| `api.boot.oauth.cleint-id`     | Oauth2 Client ID                   | ApiBoot               | memory      |
| `api.boot.oauth.client-secret` | Oauth2 Client Secret               | ApiBootSecret         | memory      |
| `api.boot.oauth.resource-id`   | Oauth2接口资源编号                 | api                   | memory      |
| `api.boot.oauth.grant-types`   | 客户端授权方式                     | Srtring[]{"password"} | memory      |
| `api.boot.oauth.scopes`        | 客户端作用域                       | String[]{"api"}       | memory      |
| `api.boot.oauth.jwt.enable`    | 是否启用JWT格式化AccessToken       | false                 | memory/jdbc |
| `api.boot.oauth.jwt.sign-key`  | 使用JWT格式化AccessToken时的签名   | ApiBoot               | memory/jdbc |

`ApiBoot`在整合`SpringSecurity`、`Oauth2`时配置进行了分离，也就意味着我们可以让`SpringSecurity`读取内存用户、`Oauth2`将生成的`AccessToken`存放到`数据库`，当然反过来也是可以的，相互不影响！！！

### 内存方式(默认方式)

#### Spring Security

`ApiBoot`在整合`Spring Security`的内存方式时，仅仅需要配置`api.boot.security.users`用户列表参数即可，就是这么的简单，

配置用户示例如下所示：

```yaml
api:
  boot:
    security:
      # Spring Security 内存方式用户列表示例
      users:
        - username: hengboy
          password: 123456
        - username: apiboot
          password: abc321
```

`api.boot.security.users`是一个`List<SecurityUser>`类型的集合，所以这里可以配置多个用户。

#### Oauth2

如果全部使用默认值的情况话不需要做任何配置！！！

### Jdbc方式

>  前提：项目需要添加数据源依赖。

#### Spring Security

**默认用户表**

`ApiBoot`在整合`Spring Security`的Jdbc方式时，在使用`ApiBoot`提供的默认结构用户表时只需要修改`api.boot.security.away: jdbc`即可，`ApiBoot`提供的用户表结构如下所示：

```sql
CREATE TABLE `api_boot_user_info` (
  `UI_ID` int(11) NOT NULL AUTO_INCREMENT COMMENT '用户编号，主键自增',
  `UI_USER_NAME` varchar(30) DEFAULT NULL COMMENT '用户名',
  `UI_NICK_NAME` varchar(50) DEFAULT NULL COMMENT '用户昵称',
  `UI_PASSWORD` varchar(255) DEFAULT NULL COMMENT '用户密码',
  `UI_EMAIL` varchar(30) DEFAULT NULL COMMENT '用户邮箱地址',
  `UI_AGE` int(11) DEFAULT NULL COMMENT '用户年龄',
  `UI_ADDRESS` varchar(200) DEFAULT NULL COMMENT '用户地址',
  `UI_IS_LOCKED` char(1) DEFAULT 'N' COMMENT '是否锁定',
  `UI_IS_ENABLED` char(1) DEFAULT 'Y' COMMENT '是否启用',
  `UI_STATUS` char(1) DEFAULT 'O' COMMENT 'O：正常，D：已删除',
  `UI_CREATE_TIME` timestamp NULL DEFAULT current_timestamp() COMMENT '用户创建时间',
  PRIMARY KEY (`UI_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8 COMMENT='ApiBoot默认的用户信息表';
```

**自定义用户表**

如果你的系统已经存在了自定义用户表结构，`ApiBoot`是支持的，而且很简单就可以完成整合，我们需要先修改`api.boot.security.enable-default-store-delegate`参数为`false`，如下所示：

```yaml
api:
  boot:
    security:
      # Spring Security jdbc方式用户列表示例
      enable-default-store-delegate: false
      away: jdbc
```

添加`ApiBootStoreDelegate`接口实现类，如下所示：

```java
@Component
public class DisableDefaultUserTableStoreDelegate implements ApiBootStoreDelegate {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户列表示例
     * 从该集合内读取用户信息
     * 可以使用集合内的用户获取access_token
     */
    static List<String> users = new ArrayList() {
        {
            add("api-boot");
            add("hengboy");
            add("yuqiyu");
        }
    };

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!users.contains(username)) {
            throw new UsernameNotFoundException("用户：" + username + "不存在");
        }
        return new DisableDefaultUserDetails(username);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class DisableDefaultUserDetails implements UserDetails {
        private String username;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new ArrayList() {
                {
                    add((GrantedAuthority) () -> "ROLE_USER");
                }
            };
        }

        /**
         * 示例密码使用123456
         *
         * @return
         */
        @Override
        public String getPassword() {
            return passwordEncoder.encode("123456");
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
```

根据上面代码示例，我们可以通过`users`用户列表进行访问获取`access_token`。



#### Oauth2

**创建Oauth所需表结构**

`Oauth2`如果使用`Jdbc`方式进行存储`access_token`、`client_details`时，需要在数据库内初始化`Oauth2`所需相关表结构，[oauth-mysql.sql](https://github.com/hengboy/api-boot/tree/master/api-boot-project/api-boot-starters/api-boot-starter-security-oauth-jwt/oauth-mysql.sql)

**添加客户端数据**

初始化`Oauth2`表结构后，需要向`oauth_client_details`表内添加一个客户端信息，下面是对应`ApiBoot Security Oauth`配置信息的数据初始化，如下所示：

```sql
INSERT INTO `oauth_client_details` VALUES ('ApiBoot','api','$2a$10$M5t8t1fHatAj949RCHHB/.j1mrNAbxIz.mOYJQbMCcSPwnBMJLmMK','api','password',NULL,NULL,7200,7200,NULL,NULL);
```

>  `AppSecret`加密方式统一使用`BCryptPasswordEncoder`，数据初始化时需要注意。



在上面`memory/jdbc`两种方式已经配置完成，接下来我们就可以获取`access_token`。

### 获取AccessToken

#### 通过CURL获取

```sh
➜  ~ curl ApiBoot:ApiBootSecret@localhost:8080/oauth/token -d "grant_type=password&username=api-boot&password=123456"

{"access_token":"eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHAiOjE1NTMxMDk1MjMsInVzZXJfbmFtZSI6ImFwaS1ib290IiwiYXV0aG9yaXRpZXMiOlsiUk9MRV9VU0VSIl0sImp0aSI6IjBmZTUyY2RlLTBhZjctNDI1YS04Njc2LTFkYTUyZTA0YzUxYiIsImNsaWVudF9pZCI6IkFwaUJvb3QiLCJzY29wZSI6WyJhcGkiXX0.ImqGZssbDEOmpf2lQZjLQsch4ukE0C4SCYJsutfwfx0","token_type":"bearer","expires_in":42821,"scope":"api","jti":"0fe52cde-0af7-425a-8676-1da52e04c51b"}
```



### 启用JWT

`ApiBoot Security Oauth`在使用`JWT`格式化`access_token`时非常简单的，配置如下所示：

```yaml
api:
  boot:
    oauth:
      jwt:
        # 开启Jwt转换AccessToken
        enable: true
        # 转换Jwt时所需加密key，默认为ApiBoot
        sign-key: 恒宇少年 - 于起宇
```

默认不启用`JWT`，`sign-key`签名建议进行更换。
