package org.minbox.framework.knowledge.library.service.wx.controller;

import com.alibaba.fastjson.JSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.minbox.framework.knowledge.library.common.tools.HttpClientTools;
import org.minbox.framework.knowledge.library.service.constants.UrlPrefix;
import org.minbox.framework.knowledge.library.service.wx.model.SmallProgramLoginResult;
import org.minbox.framework.knowledge.library.service.wx.properties.SmallProgramProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 小程序登录控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-11 14:26
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
@RequestMapping(value = UrlPrefix.SMALL_PROGRAM_LOGIN)
@Api(tags = "小程序登录信息")
public class SmallProgramLoginController {
    /**
     * 小程序属性配置
     */
    @Autowired
    private SmallProgramProperties smallProgramProperties;

    /**
     * 通过JsCode兑换小程序用户信息
     *
     * @param code jsCode
     * @return
     */
    @GetMapping(value = "/{code}")
    @ApiOperation(value = "JsCode兑换OpenID", response = SmallProgramLoginResult.class)
    @ApiImplicitParam(name = "code", value = "JsCode")
    @ApiResponse(code = 200, message = "转换成功", response = SmallProgramLoginResult.class)
    public ApiBootResult getOpenId(@PathVariable String code) {
        // 格式化请求地址
        String toSessionUrl = String.format(smallProgramProperties.getCodeToSessionUrl(), code);

        // 执行发送请求
        String result = HttpClientTools.get(toSessionUrl);

        // 转换请求结果
        SmallProgramLoginResult smallProgramLoginResult = JSON.parseObject(result, SmallProgramLoginResult.class);

        return ApiBootResult.builder().data(smallProgramLoginResult).build();
    }
}
