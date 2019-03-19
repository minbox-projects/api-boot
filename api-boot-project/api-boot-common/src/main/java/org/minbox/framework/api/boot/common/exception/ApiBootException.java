package org.minbox.framework.api.boot.common.exception;

import lombok.NoArgsConstructor;

/**
 * ApiBoot自定义异常
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-15 14:11
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@NoArgsConstructor
public class ApiBootException extends RuntimeException {
    /**
     * 构造函数初始化异常对象
     *
     * @param message 异常信息
     */
    public ApiBootException(String message) {
        super(message);
    }

    /**
     * 构造函数初始化异常对象
     *
     * @param message 异常消息
     * @param cause   异常堆栈信息
     */
    public ApiBootException(String message, Throwable cause) {
        super(message, cause);
    }
}
