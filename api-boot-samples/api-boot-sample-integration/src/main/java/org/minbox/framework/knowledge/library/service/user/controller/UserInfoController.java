package org.minbox.framework.knowledge.library.service.user.controller;

import io.swagger.annotations.*;
import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.minbox.framework.knowledge.library.common.entity.UserInfoEntity;
import org.minbox.framework.knowledge.library.service.constants.UrlPrefix;
import org.minbox.framework.knowledge.library.service.security.SecurityUserTools;
import org.minbox.framework.knowledge.library.service.user.dto.CheckUserRegisterDTO;
import org.minbox.framework.knowledge.library.service.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户信息控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-11 15:11
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
@RequestMapping(value = UrlPrefix.USER)
@Api(tags = "用户信息")
public class UserInfoController {
    /**
     * 用户基本信息业务逻辑
     */
    @Autowired
    private UserInfoService userInfoService;

    /**
     * 获取登录用户的信息
     *
     * @return
     */
    @GetMapping(value = "/login")
    @ApiOperation(value = "登录获取信息", response = UserInfoEntity.class)
    @ApiResponse(code = 200, message = "查询成功", response = UserInfoEntity.class)
    public ApiBootResult login() {
        UserInfoEntity userInfoEntity = SecurityUserTools.getCurrentUser().getUser();
        return ApiBootResult.builder().data(userInfoEntity).build();
    }

    /**
     * 用户注册
     *
     * @param nickName 用户昵称
     * @param openId   openId
     * @return
     */
    @PostMapping(value = "/register")
    @ApiOperation(value = "用户注册", response = UserInfoEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "nickName", value = "昵称", dataType = "String", required = true),
            @ApiImplicitParam(name = "openId", value = "OpenID", dataType = "String", required = true)
    })
    @ApiResponse(code = 200, message = "注册成功", response = UserInfoEntity.class)
    public ApiBootResult register(String nickName, String openId) {
        // 执行注册
        userInfoService.register(nickName, openId);
        // 返回新注册用户信息
        UserInfoEntity userInfoEntity = userInfoService.loadByOpenId(openId);
        return ApiBootResult.builder().data(userInfoEntity).build();
    }

    /**
     * 判断用户是否已经注册
     *
     * @param openId 用户openId
     * @return
     */
    @ApiOperation(value = "判断用户是否注册", response = CheckUserRegisterDTO.class)
    @ApiImplicitParam(name = "openId", value = "OpenID", dataType = "String", required = true)
    @ApiResponse(code = 200, message = "查询成功", response = CheckUserRegisterDTO.class)
    @GetMapping(value = "/is-register")
    public ApiBootResult isRegister(String openId) {
        // 检查是否存在
        UserInfoEntity userInfoEntity = userInfoService.loadByOpenId(openId);
        
        // 返回检查结果
        CheckUserRegisterDTO checkUserRegisterDTO = new CheckUserRegisterDTO();
        checkUserRegisterDTO.setIsRegister(!ObjectUtils.isEmpty(userInfoEntity));
        return ApiBootResult.builder().data(checkUserRegisterDTO).build();
    }

}
