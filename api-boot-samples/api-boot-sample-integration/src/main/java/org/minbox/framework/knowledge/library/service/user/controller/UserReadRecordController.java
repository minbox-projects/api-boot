package org.minbox.framework.knowledge.library.service.user.controller;

import io.swagger.annotations.*;
import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.minbox.framework.knowledge.library.common.entity.UserInfoEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.article.dto.ArticleInfoDTO;
import org.minbox.framework.knowledge.library.service.constants.UrlPrefix;
import org.minbox.framework.knowledge.library.service.model.PageParam;
import org.minbox.framework.knowledge.library.service.security.SecurityUserTools;
import org.minbox.framework.knowledge.library.service.user.service.UserReadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户阅读记录控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-09 17:31
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
@RequestMapping(value = UrlPrefix.USER + "/read-record")
@Api(tags = "用户阅读记录")
public class UserReadRecordController {
    /**
     * 用户阅读记录业务逻辑
     */
    @Autowired
    private UserReadRecordService userReadRecordService;

    /**
     * 查询登录用户的阅读记录
     * 根据时间倒叙排列
     *
     * @return
     * @throws KnowledgeException
     */
    @GetMapping(value = "/")
    @ApiOperation(value = "查询阅读记录列表", response = ArticleInfoDTO.class)
    @ApiResponse(code = 200, message = "查询成功", response = ArticleInfoDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "分页参数：当前页码", dataType = "integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页条数", dataType = "integer", defaultValue = "20")
    })
    @PreAuthorize("isAuthenticated()")
    public ApiBootResult getUserReadRecordList(PageParam param)
            throws KnowledgeException {
        // 登录用户
        UserInfoEntity user = SecurityUserTools.getCurrentUser().getUser();
        // 返回阅读记录
        return ApiBootResult.builder().data(userReadRecordService.pageableOfReadRecordByUserId(user.getUiId(), param.getPageIndex(), param.getPageSize())).build();
    }
}
