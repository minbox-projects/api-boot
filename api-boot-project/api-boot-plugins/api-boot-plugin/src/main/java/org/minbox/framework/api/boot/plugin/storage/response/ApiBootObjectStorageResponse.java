package org.minbox.framework.api.boot.plugin.storage.response;

import lombok.Builder;
import lombok.Data;

/**
 * ApiBoot object storage response entity
 *
 * @author 恒宇少年
 */
@Data
@Builder
public class ApiBootObjectStorageResponse {
    /**
     * file name
     */
    private String objectName;
    /**
     * file request url
     */
    private String objectUrl;
}
