package org.minbox.framework.api.boot.plugin.storage.exception;

import lombok.NoArgsConstructor;

/**
 * 对象存储异常
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-21 14:22
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@NoArgsConstructor
public class ApiBootObjectStorageException extends RuntimeException {
    public ApiBootObjectStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
