package org.minbox.framework.api.boot.sample;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 实例实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-19 14:15
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
public class SampleEntity {
    /**
     * 配置格式化保留一位小数点
     */
    @BigDecimalFormatter(scale = 1)
    private BigDecimal decimalValue;
}
