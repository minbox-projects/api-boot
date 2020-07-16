package org.minbox.framework.api.boot.storage.exception;

import lombok.NoArgsConstructor;

/**
 * Object storage exception
 * extend from {@link RuntimeException}
 *
 * @author 恒宇少年
 */
@NoArgsConstructor
public class ApiBootObjectStorageException extends RuntimeException {
    public ApiBootObjectStorageException(String message, Throwable cause) {
        super(message, cause);
    }
}
