package org.minbox.framework.api.boot.plugin.storage.response;

import lombok.Builder;
import lombok.Data;

/**
 * ApiBoot 对象存储响应实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-21 14:21
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@Data
@Builder
public class ApiBootObjectStorageResponse {
    /**
     * 对象名称
     */
    private String objectName;
    /**
     * 对象路径
     */
    private String objectUrl;
}
