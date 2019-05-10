package org.minbox.framework.knowledge.library.service.freedback.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.minbox.framework.knowledge.library.common.entity.UserInfoEntity;
import org.minbox.framework.knowledge.library.service.constants.UrlPrefix;
import org.minbox.framework.knowledge.library.service.freedback.service.FreedBackService;
import org.minbox.framework.knowledge.library.service.security.SecurityUserTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 意见反馈控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-22 11:14
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
@RequestMapping(UrlPrefix.FREEDBACK)
@Api(tags = "意见反馈")
public class FreedBackController {
    /**
     * 意见反馈业务逻辑
     */
    @Autowired
    private FreedBackService freedBackService;

    @ApiOperation(value = "提交意见反馈")
    @ApiImplicitParam(name = "content", value = "意见反馈内容", type = "String")
    @PostMapping(value = "/submit")
    public ApiBootResult submit(String content) {
        // 获取登录用户信息
        UserInfoEntity userInfoEntity = SecurityUserTools.getCurrentUser().getUser();
        // 提交意见反馈
        freedBackService.submit(userInfoEntity.getUiId(), content);
        return ApiBootResult.builder().build();
    }
}
