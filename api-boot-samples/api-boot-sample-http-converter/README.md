## ApiBoot Http Converter

`FastJson`是阿里巴巴提供的一款`Json`格式化插件。

`ApiBoot`提供了`FastJson`驱动转换接口请求的`Json`字符串数据，添加该依赖后会自动格式化时间(格式：YYYY-MM-DD HH:mm:ss)、空对象转换为空字符串返回、空Number转换为0等，还会自动装载`ValueFilter`接口的实现类来完成自定义的数据格式转换。

### 引入Http Converter

`ApiBoot Http Converter`使用非常简单，只需要在`pom.xml`添加如下依赖：

```xml
<!--ApiBoot Http Converter-->
<dependency>
	<groupId>org.minbox.framework</groupId>
	<artifactId>api-boot-starter-http-converter</artifactId>
</dependency>
```

`ApiBoot`所提供的依赖都不需要添加版本号，具体查看[ApiBoot版本依赖](https://github.com/hengboy/api-boot/blob/master/README.md#%E6%B7%BB%E5%8A%A0%E7%89%88%E6%9C%AC%E4%BE%9D%E8%B5%96)

### 相关配置

`ApiBoot Http Converter`通过使用`SpringBoot`内置的配置参数名来确定是否开启，在`SpringBoot`内可以通过`spring.http.converters.preferred-json-mapper`来修改首选的`Json`格式化插件，`SpringBoot`已经提供了三种，分别是：`gson`、`jackson`、`jsonb`，当我们配置该参数为`fastJson`或`不进行配置`就会使用`ApiBoot Http Converter`提供的`fastJson`来格式化转换`Json`返回数据。

如下所示：

```yaml
spring:
  http:
    converters:
      # 不配置默认使用fastJson
      preferred-json-mapper: fastJson

```

### 内置ValueFilter注解

| 注解名称                  | ValueFilter实现类       | 作用                                             |
| ------------------------- | ----------------------- | ------------------------------------------------ |
| `@ApiBootValueHide`       | `ValueHideFilter`       | 用于格式化隐藏标注的字符串字段的值               |
| `@ApiBootDecimalAccuracy` | `DecimalAccuracyFilter` | 用于格式化BigDecimal类型字段的精度、小数点位数等 |

##### @ApiBootValueHide

可配置参数：

- `length`：隐藏的字节长度
- `start`：开始隐藏的字节索引位置
- `position`：位置类型，具体查看`ValueHidePositionEnum`枚举
- `placeholder`：隐藏后被替换的字符，默认为：`*`

##### @ApiBootDecimalAccuracy

可配置参数：

- `scale`：小数点位数，默认为`2`
- `roundingMode`：小数点精度模式，默认为`BigDecimal.ROUND_DOWN`，具体查看`BigDecimal`的rounding mode

### 自定义ValueFilter

`ValueFilter`是`FastJson`的概念，用于自定义转换实现，比如：自定义格式化日期、自动截取小数点等。

下面提供一个`ValueFilter`的简单示例，具体的使用请参考`FastJson`官方文档。

#### ValueFilter示例

在使用`ValueFilter`时一般都会搭配一个对应的自定义`@Annotation`来进行组合使用，保留自定义小数点位数的示例如下所示：

**创建 BigDecimalFormatter Annotation**

```java
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface BigDecimalFormatter {
    /**
     * 小数位数，默认保留两位
     * @return
     */
    int scale() default 2;
}
```

**创建 BigDecimal ValueFilter**

```java
public class BigDecimalValueFilter
        implements ValueFilter {
    /**
     * logback
     */
    Logger logger = LoggerFactory.getLogger(BigDecimalValueFilter.class);

    /**
     * @param object 对象
     * @param name   对象的字段的名称
     * @param value  对象的字段的值
     */
    @Override
    public Object process(Object object, String name, Object value) {
        if (ValidateTools.isEmpty(value) || !(value instanceof BigDecimal)) {
            return value;
        }
        return convertValue(object, name, value);
    }

    /**
     * 转换值
     *
     * @param object 字段所属对象实例
     * @param name   字段名称
     * @param value  字段的值
     * @return
     */
    Object convertValue(Object object, String name, Object value) {
        try {
            /**
             * 反射获取field
             */
            Field field = object.getClass().getDeclaredField(name);
            /**
             *判断字段是否存在@BigDecimalFormatter注解
             */
            if (field.isAnnotationPresent(BigDecimalFormatter.class)) {
                BigDecimalFormatter bigDecimalFormatter = field.getAnnotation(BigDecimalFormatter.class);
                // 执行格式化
                BigDecimal decimal = (BigDecimal) value;
                System.out.println(bigDecimalFormatter.scale());
                // 保留小数位数，删除多余
                value = decimal.setScale(bigDecimalFormatter.scale(), BigDecimal.ROUND_DOWN).doubleValue();
            }
        } catch (Exception e) {
            logger.error("格式化BigDecimal字段出现异常：{}", e.getMessage());
        }
        return value;
    }
}
```

**使用 BigDecimalFormatter Annotation**

```java
@BigDecimalFormatter
private BigDecimal decimalValue;
```

