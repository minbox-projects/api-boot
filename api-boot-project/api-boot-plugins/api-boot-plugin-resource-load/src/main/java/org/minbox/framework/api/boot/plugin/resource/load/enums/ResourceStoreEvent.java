package org.minbox.framework.api.boot.plugin.resource.load.enums;

import lombok.Getter;

/**
 * 资源存储事件
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-12 09:33
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
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
