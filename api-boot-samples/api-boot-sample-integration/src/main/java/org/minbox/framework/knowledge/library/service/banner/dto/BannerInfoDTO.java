package org.minbox.framework.knowledge.library.service.banner.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.minbox.framework.knowledge.library.common.entity.BannerInfoEntity;

/**
 * 轮播图数据转换实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 10:58
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@ApiModel
public class BannerInfoDTO extends BannerInfoEntity {
    /**
     * 图片地址
     */
    @ApiModelProperty("轮播图图片地址")
    private String bannerImage;
}
