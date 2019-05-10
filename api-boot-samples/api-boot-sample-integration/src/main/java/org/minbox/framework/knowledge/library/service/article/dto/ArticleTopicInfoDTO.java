package org.minbox.framework.knowledge.library.service.article.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.minbox.framework.knowledge.library.common.entity.ArticleTopicInfoEntity;

/**
 * 文章主题数据转换实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 10:46
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@ApiModel
public class ArticleTopicInfoDTO extends ArticleTopicInfoEntity {
    /**
     * 主题图标地址
     */
    @ApiModelProperty("主题图标")
    private String topicIcon;
}
