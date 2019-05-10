package org.minbox.framework.knowledge.library.service.article.param;

import lombok.Builder;
import lombok.Data;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 13:37
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@Builder
public class ArticleQueryParam {
    /**
     * 当前页码
     */
    private int pageIndex;
    /**
     * 每页条数
     */
    private int pageSize;
    /**
     * 是否热门
     */
    private String isHot;
    /**
     * 是否推荐
     */
    private String isRecommend;
    /**
     * 是否新文章
     */
    private String isNew;
    /**
     * 查询关键字
     */
    private String keyword;
    /**
     * 专题编号
     */
    private String topicId;
}
