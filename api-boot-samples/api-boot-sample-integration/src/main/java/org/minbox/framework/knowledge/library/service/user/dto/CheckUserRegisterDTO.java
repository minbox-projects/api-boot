package org.minbox.framework.knowledge.library.service.user.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 检查用户是否已经注册数据转换实体
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-18 11:18
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@ApiModel
public class CheckUserRegisterDTO {

    @ApiModelProperty("是否已经注册，true：已经注册，false：未注册")
    private Boolean isRegister;
}
