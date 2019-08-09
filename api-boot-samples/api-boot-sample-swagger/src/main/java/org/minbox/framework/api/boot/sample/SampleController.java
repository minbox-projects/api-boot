package org.minbox.framework.api.boot.sample;

import org.minbox.framework.api.boot.common.model.ApiBootResult;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * swagger 示例 controller
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-18 13:53
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
@RequestMapping(value = "/api")
@Api(tags = "Swagger示例")
public class SampleController {
    /**
     * 简单swagger文档示例
     *
     * @param userName 用户名
     * @return @{link ApiBootResult} ApiBootResult
     */
    @GetMapping(value = "/echo")
    @ApiOperation(value = "输出用户名", response = ApiBootResult.class)
    @ApiResponse(code = 200, message = "处理成功", response = ApiBootResult.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "userName", value = "用户名", dataType = "String", required = true),
    })
    public ApiBootResult echo(String userName) {
        return ApiBootResult.builder()
                .data("你好呀 : " + userName)
                .build();
    }

}
