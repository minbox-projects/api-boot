package org.minbox.framework.knowledge.library.service.article.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.minbox.framework.knowledge.library.common.entity.ArticleInfoEntity;

/**
 * 文件数据转换实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-09 16:59
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@ApiModel
public class ArticleInfoDTO extends ArticleInfoEntity {
    /**
     * 文章封面图
     */
    @ApiModelProperty("文章封面")
    private String convertImage;
    /**
     * 文章留言总数
     */
    @ApiModelProperty("留言总数")
    private Long commentCount;
    /**
     * 是否喜欢该文章
     */
    @ApiModelProperty("是否喜欢该文章")
    private Boolean isLike;
}
