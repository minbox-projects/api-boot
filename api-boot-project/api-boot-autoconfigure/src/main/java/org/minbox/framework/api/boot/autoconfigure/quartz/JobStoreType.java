package org.minbox.framework.api.boot.autoconfigure.quartz;

/**
 * Define the supported Quartz {@code JobStore}.
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-30 13:17
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public enum JobStoreType {

    /**
     * Store jobs in memory.
     */
    MEMORY,

    /**
     * Store jobs in the database.
     */
    JDBC

}
