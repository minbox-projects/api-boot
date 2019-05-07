## ApiBoot Mybatis Enhance

`Enhance`是对于原生的`MyBatis`的增强编写，不影响任何原生的使用，使用后完全替代`mybatis-core`、`mybatis-spring`以及`mybatis-spring-boot-starter`，可以使用`SpringBoot`配置文件的形式进行配置相关的内容，尽可能强大的方便快速的集成`MyBatis`。

- 增强CRUD

`Mybatis Enhance`提供了`单表基础数据`的`CRUD`操作以及部分`批量数据`的操作，可以不再使用`MyBatis`提供的自动生成的方式对单个数据表进行数据操作，当然如果你想使用也是可以的。

- 动态查询、更新、删除

`Mybatis Enhance`还规划了多个数据表之间的动态查询方式，这种方式可以让你体验到你在使用`Java代码`编写`SQL语句`，极大方便的关联、聚合、多表查询字段等常用数据动作。

### 添加依赖

```xml
<!--ApiBoot Mybatis Enhance-->
<dependency>
  <groupId>org.minbox.framework</groupId>
  <artifactId>api-boot-starter-mybatis-enhance</artifactId>
</dependency>
<dependencyManagement>
  <dependencies>
    <!--ApiBoot 版本依赖-->
    <dependency>
      <groupId>org.minbox.framework</groupId>
      <artifactId>api-boot-dependencies</artifactId>
      <version>2.0.7.RELEASE</version>
      <scope>import</scope>
      <type>pom</type>
    </dependency>
  </dependencies>
</dependencyManagement>
```



### 该怎么使用呢？

#### 实体的创建

根据对应数据库内的表来创建实体，`Enhance`采用的是`Spring Data JPA`的形式来管理实体类，并且已经预先提供的一些`Annotation`，`数据实体(Entity)`对应数据库内的`数据表(Table)`，下面是一个简单的实体代码：
```java
/**
 * 用户数据实体
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/5/13
 * Time：8:53
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
@Data
@Table(name = "test_user_info")
public class UserInfoEntity implements Serializable {
    /**
     * 用户编号
     */
    @Id(generatorType = KeyGeneratorTypeEnum.AUTO)
    @Column(name = "TUI_ID")
    private Integer userId;
    /**
     * 用户名
     */
    @Column(name = "TUI_NAME")
    private String userName;
    /**
     * 年龄
     */
    @Column(name = "tui_age")
    private Integer age;
    /**
     * 地址
     */
    @Column(name = "tui_address")
    private String address;
}
```
我采用了跟`Spring Data JPA`相同命名方式的注解，这样也方便大家在使用`Enhance`时可以快速的转换注解的使用。
#### Mapper的创建

创建`Mapper`跟我们使用原生`MyBatis`创建方式一样，不过使用`Enhance`后不需要添加`@Mapper`注解，你创建的`Mapper`只需要继承`EnhanceMapper<T,PK>`接口就可以被扫描到，并且同时可以获取内部提供的`CRUD`方法！！！
如下所示：
```java
/**
 * 用户基本信息数据接口
 *
 * @author：于起宇 <br/>
 * ===============================
 * Created with IDEA.
 * Date：2018/5/13
 * Time：9:00
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * ================================
 */
public interface UserInfoMapper extends EnhanceMapper<UserInfoEntity, Integer> {
}
```
`EnhanceMapper`需要两个泛型，第一个是实体类的类型，第二个则是实体类主键的类型，这样方便我们在传参或者返回值时做到统一，否则还需要进行`Object`类型的转换，那样不仅麻烦还会提高运行成本。

#### 暂时内置的方法

```java
// 统计数据
Long countAll() throws EnhanceFrameworkException;
// 清空数据
void deleteAll() throws EnhanceFrameworkException;
// 根据主键数组删除指定数据
void deleteArray(Id... ids) throws EnhanceFrameworkException;
// 根据自定义sql删除数据
void deleteBySql(String sql, Map<String, Object> params) throws EnhanceFrameworkException;
// 根据主键集合删除指定数据
void deleteCollection(Collection<Id> collection) throws EnhanceFrameworkException;
// 删除一条数据
void deleteOne(Id id) throws EnhanceFrameworkException;
// 数据保存
void insert(T t) throws EnhanceFrameworkException;
// 保存数组内的所有数据
void insertArray(T... array) throws EnhanceFrameworkException;
// 保存集合内的所有数据
void insertCollection(Collection<T> collection) throws EnhanceFrameworkException;
// 查询全部数据
List<T> selectAll() throws EnhanceFrameworkException;
// 根据主键数组查询指定数据
List<T> selectArray(Id... ids) throws EnhanceFrameworkException;
// 分页查询数据
List<T> selectByPageable(Pageable pageable) throws EnhanceFrameworkException;
// 自定义sql查询数据
List<Map> selectBySql(String sql, Map<String, Object> params) throws EnhanceFrameworkException;
// 根据主键集合查询指定数据
List<T> selectCollection(Collection<Id> ids) throws EnhanceFrameworkException;
// 根据主键查询单条数据
T selectOne(Id id) throws EnhanceFrameworkException;
// 根据主键更新数据实体
void update(T t) throws EnhanceFrameworkException;
// 自定义sql更新数据
void updateBySql(String sql, Map<String, Object> params) throws EnhanceFrameworkException;
```
以上是`1.0.3.RELEASE`版本提供的内置方法列表，都是在平时开发中比较常用到对单表数据操作的方法。
#### 方法命名规则的使用

`方法命名规则`是`Spring Data JPA`中的提供的一种数据操作的方式，主要适用于`查询`、`统计`、`删除`等数据操作动作，其主要原理是根据方法的名称来自动生成`SQL`，使用正则表达式来进行方法匹配。

#### 方法规则查询

方法规则查询简单示例如下所示：
```java
public interface UserInfoMapper extends EnhanceMapper<UserInfoEntity, Integer> {
    /**
     * 只根据一个字段查询
     * findBy userName
     * @param name 查询条件的值
     * @return
     */
    UserInfoEntity findByUserName(@Param("userName") String name);

    /**
     * 可以根据多个查询条件进行查询
     * 中间使用And进行连接
     * findBy userName and age
     * @param name 第一个查询条件的值
     * @param age  第二个查询条件的值
     * @return
     */
    UserInfoEntity findByUserNameAndAge(@Param("userName") String name, @Param("age") Integer age);
}
```
#### 方法规则统计

方法规则统计简单示例如下所示：
```java
public interface UserInfoMapper extends EnhanceMapper<UserInfoEntity, Integer> {
    /**
     * 只根据一个字段统计数据
     * 语法分析：countBy userName
     * @param name 统计条件的值
     * @return
     */
    Long countByUserName(@Param("userName") String name);
    /**
     * 根据多个条件进行统计数据
     * 语法分析：countBy userName and age
     * @param name 第一个统计条件的值
     * @param age  第二个统计条件的值
     * @return
     */
    Long countByUserNameAndAge(@Param("userName") String name, @Param("age") Integer age);
}    
```
#### 方法规则删除

方法规则删除简单示例如下所示：
```java
public interface UserInfoMapper extends EnhanceMapper<UserInfoEntity, Integer> {
    /**
     * 只根据一个字段删除
     * 语法分析：removeBy userName
     * @param name 查询条件的值
     */
    void removeByUserName(@Param("userName") String name);

    /**
     * 根据多个条件进行删除数据
     * 中间使用And进行连接
     * 语法分析：removeBy userName and userId
     * @param name 第一个删除条件的值
     * @param id   第二个删除条件的值
     */
    void removeByUserNameAndUserId(@Param("userName") String name, @Param("userId") String id);
}   
```

### 动态查询

`Mybatis Enhance`支持动态查询，可以将返回结果映射到任何可对应的类型内，比如：基础数据类型、集合、实体类等，编写动态查询时与`SQL`语法几乎一致。

如下简单示例：

```java
/**
  * Mybatis Enhance Dsl Factory
  */
@Autowired
private EnhanceDslFactory dslFactory;

/**
  * 示例：动态条件查询用户信息
  *
  * @param userId 用户编号
  * @return 用户基本信息
  */
public UserEntity dynamicSelectOne(String userId) {

  DUserEntity dUserEntity = DUserEntity.DSL();

  return dslFactory.createSearchable()
    .selectFrom(dUserEntity)
    .where(dUserEntity.uiId.eq(userId))
    .resultType(UserEntity.class)
    .fetchOne();
}
```

> 在上面示例中，`DUserEntity`是`MybatisEnhance`所需要的动态查询实体，该实体会由专门的代码生成工具生成。

#### 动态实体

`DUserEntity`实体类内容如下所示：

```java
public class DUserEntity extends TableExpression<UserEntity> {
    public DUserEntity(String root) {
        super(root);
    }

    public static final DUserEntity DSL() {
        return new DUserEntity("local_user_info");
    }

    public ColumnExpression uiId = new ColumnExpression("ui_id", this);
    public ColumnExpression uiPhone = new ColumnExpression("ui_phone", this);
    public ColumnExpression uiPassword = new ColumnExpression("ui_password", this);
    public ColumnExpression uiStatus = new ColumnExpression("ui_status", this);

    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
                uiId,
                uiPhone,
                uiPassword,
                uiStatus
        };
    }
}
```



### 动态更新

`Mybatis Enhance`支持动态更新，可以根据条件进行更新任意一个、多个字段，如下所示：

```java
/**
  * 示例：动态更新手机号
  *
  * @param userId 用户编号
  * @param phone  手机号码
  */
public void dynamicUpdateAge(String userId, String phone) {
  DUserEntity dUserEntity = DUserEntity.DSL();
  dslFactory.createUpdateable()
    .update(dUserEntity)
    .set(SetFilter.set(dUserEntity.uiPhone, phone))
    .where(dUserEntity.uiId.eq(userId))
    .execute();
}
```

动态更新时的条件可以是一个、也可以是多个，多个可以使用`and`、`or`进行连接。

### 动态删除

`Mybatis Enhance`支持动态删除，可以根据单个、多个条件进行筛选删除，如下所示：

```java
/**
  * 实例：动态根据手机号删除
  *
  * @param phone 手机号
  */
public void dynamicDeleteAge(String phone) {
  DUserEntity dUserEntity = DUserEntity.DSL();
  dslFactory.createDeleteable()
    .delete(dUserEntity)
    .where(dUserEntity.uiPhone.eq(phone))
    .execute();
}
```



### 多条件 And

`Mybatis Enhance`支持动态组装查询条件，比如我现在根据用户名、手机号进行定位删除用户，如下所示：

```java
/**
  * 实例：动态根据手机号、用户编号删除
  *
  * @param userId 用户编号
  * @param phone  手机号
  */
public void dynamicDeleteUser(String userId, String phone) {
  DUserEntity dUserEntity = DUserEntity.DSL();
  dslFactory.createDeleteable()
    .delete(dUserEntity)
    // 手机号条件
    .where(dUserEntity.uiPhone.eq(phone))
    // and 用户编号
    .and(dUserEntity.uiId.eq(userId))
    .execute();
}
```

