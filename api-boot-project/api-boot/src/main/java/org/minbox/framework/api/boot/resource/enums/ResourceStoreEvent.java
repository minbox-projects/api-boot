package org.minbox.framework.api.boot.resource.enums;

import lombok.Getter;

/**
 * 资源存储事件
 *
 * @author 恒宇少年
 */
@Getter
public enum ResourceStoreEvent {
    /**
     * 查询资源
     */
    SELECT,
    /**
     * 添加资源
     */
    INSERT,
    /**
     * 更新资源
     */
    UPDATE,
    /**
     * 删除资源
     */
    DELETE
}
