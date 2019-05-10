package org.minbox.framework.knowledge.library.service.model;

import lombok.Data;

/**
 * 查询分页参数
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 09:58
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
public class PageParam {
    /**
     * 分页：当前页码
     */
    private int pageIndex = 1;
    /**
     * 分页：每页条数
     */
    private int pageSize = 20;
}
