## ApiBoot Mybatis Pageable

`MyBatis-Pageable`是一款自动化分页的插件，基于`MyBatis`内部的插件`Interceptor`拦截器编写完成，拦截`Executor.query`的两个重载方法计算出分页的信息以及根据配置的数据库`Dialect`自动执行不同的查询语句完成总数量的统计。

### 添加依赖

```xml
<dependencies>
<!--ApiBoot Mybatis Pageable-->
<dependency>
  <groupId>org.minbox.framework</groupId>
  <artifactId>api-boot-starter-mybatis-pageable</artifactId>
</dependency>
<!--省略其他依赖-->
</dependencies>
<dependencyManagement>
  <dependencies>
    <!--ApiBoot 版本依赖-->
    <dependency>
      <groupId>org.minbox.framework</groupId>
      <artifactId>api-boot-dependencies</artifactId>
      <version>2.0.7.RELEASE</version>
      <type>pom</type>
      <scope>import</scope>
    </dependency>
  </dependencies>
</dependencyManagement>
```



### 支持的数据库
`MyBatis-Pageable`目前支持的主流数据库：
- DB2
- Derby
- DM、
- H2、
- HSQL、
- InforMix、
- Mariadb、
- MySQL、
- Oracle、
- Postgres、
- SqlLite、
- SqlServer2000以上版本

### 怎么使用？
目前`MyBatis-Pageable`使用比较简单，有一个分页请求类`PageableRequest`来完成自动分页操作，我们来看个简单的示例：
```java
Page<UserEntity> page = PageableRequest.of(1, 5).request(() -> userMapper.selectAll());
```

- `of`  配置分页的`当前页码`以及`每页的限制条数`
- `request` 该方法需要传递一个业务逻辑方法，也就是你需要执行分页的方法

#### Page对象详解
在上面简单的一行代码就可以完成自动分页以及读取出分页相关的信息，分页执行后我们通过`Page`对象都可以获取到什么内容呢？
- `data` 分页后的数据列表，具体的返回值可以使用`Page<T>`泛型接收
- `totalPages` 总页数
- `totalElements` 总条数
- `pageIndex` 当前页码
- `pageSize` 每页限制条数
- `offset` 分页开始位置
- `endRow` 分页结束位置
- `hasNext` 是否存在下一页，`true`：存在，`false`：不存在
- `hasPrevious` 是否存在上一页，`true`：存在，`false`：不存在
- `isFirst` 是否为首页，`true`：首页，`false`：非首页
- `isLast` 是否为末页，`true`：末页，`false`：非末页

#### 翻页查询
实际开发过程中存在这种情况，虽然传递的分页页码为`1`，但是种种判断过后我需要查询`上一页`或者`下一页`、`首页`的数据，这时候你就可以`PageableRequest`对象的`next()`、`previous()`、`first()`方法来处理这种事情的发生，如下示例：
```
Pageable pageable = PageableRequest.of(2, 10);
        if (xx = xx) {
            pageable.next();
        }
        Page<UserEntity> page = pageable.request(()->userMapper.selectAll());
```
上面是`翻页到下一页`的查询示例，当然这个功能是为了尽可能的方便分页的使用，同样的`previous()`、`first()`方法都可以这么使用。

### 注解使用

`Mybatis Pageable`内置了两个分页相关的注解：

- `PageSize`：如果实体类内的字段添加了该注解，会自动作为分页每页限制数量作为查询条件
- `PageIndex`：如果实体类内的字段添加了该注解，会自动作为分页当前页码作为查询条件

使用示例：

```java
@Test
public void test() {
  // 构建查询参数类
  QueryUserPageParam pageParam = new QueryUserPageParam();
  pageParam.setPageSize(10);
  pageParam.setPageIndex(1);
  pageParam.setKeyWord("少年");

  PageableRequest.of(pageParam).request(() -> {
    // 模糊查询用户列表(无需添加分页限制)
  });
}

/**
  * 示例：用户分页查询
  * 根据关键字条件
  */
@Data
class QueryUserPageParam extends PageParam {
  private String keyWord;
}

/**
  * 示例：@PageIndex、@PageSize注解使用
  */
@Data
class PageParam {
  @PageIndex
  private int pageIndex;
  @PageSize
  private int pageSize;
}
```

