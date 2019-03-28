package org.minbox.framework.api.boot.sample;

import com.alibaba.fastjson.serializer.ValueFilter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.math.BigDecimal;

/**
 * 对应BigDecimalFormatter注解的ValueFilter实现
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-19 14:13
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
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
        if (StringUtils.isEmpty(value) || !(value instanceof BigDecimal)) {
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
