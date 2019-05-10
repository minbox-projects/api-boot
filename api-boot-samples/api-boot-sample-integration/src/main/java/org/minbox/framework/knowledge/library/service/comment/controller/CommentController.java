package org.minbox.framework.knowledge.library.service.comment.controller;

import io.swagger.annotations.*;
import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.minbox.framework.knowledge.library.common.entity.UserInfoEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.comment.dto.CommentInfoDTO;
import org.minbox.framework.knowledge.library.service.comment.service.CommentService;
import org.minbox.framework.knowledge.library.service.constants.UrlPrefix;
import org.minbox.framework.knowledge.library.service.security.SecurityUserTools;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文章留言控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-11 11:33
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
@RequestMapping(value = UrlPrefix.COMMENT)
@Api(tags = "文章留言")
public class CommentController {
    /**
     * 留言业务逻辑
     */
    @Autowired
    private CommentService commentService;

    /**
     * 读取文章留言列表
     *
     * @param articleId 文章编号
     * @param pageIndex 当前页码
     * @param pageSize  每页条数
     * @return
     */
    @ApiOperation(value = "文章留言列表", response = CommentInfoDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章编号", dataType = "String"),
            @ApiImplicitParam(name = "pageIndex", value = "分页参数：当前页码", dataType = "integer"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页条数", dataType = "integer")
    })
    @ApiResponse(code = 200, message = "查询成功", response = CommentInfoDTO.class)
    @GetMapping(value = "/{articleId}/{pageIndex}/{pageSize}")
    public ApiBootResult list(@PathVariable String articleId, @PathVariable int pageIndex, @PathVariable int pageSize) throws KnowledgeException {

        return ApiBootResult.builder().build();
    }

    /**
     * 提交留言
     *
     * @param articleId      文章编号
     * @param commentId      留言编号
     * @param commentContent 留言内容
     * @return
     * @throws KnowledgeException
     */
    @PostMapping(value = "/submit")
    @ApiOperation(value = "提交留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章编号", dataType = "String", required = true),
            @ApiImplicitParam(name = "commentId", value = "留言编号", dataType = "String", required = true),
            @ApiImplicitParam(name = "commentContent", value = "留言内容", dataType = "String", required = true)
    })
    public ApiBootResult submit(String articleId, String commentId, String commentContent) throws KnowledgeException {
        // 用户信息
        UserInfoEntity userInfoEntity = SecurityUserTools.getCurrentUser().getUser();

        return ApiBootResult.builder().build();
    }

    /**
     * 点赞留言
     *
     * @param commentId 留言编号
     * @return
     * @throws KnowledgeException
     */
    @PostMapping(value = "/like")
    @ApiOperation(value = "点赞留言")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "留言编号", dataType = "String", required = true)
    })
    public ApiBootResult like(String commentId) throws KnowledgeException {
        commentService.likeComment(commentId);
        return ApiBootResult.builder().build();
    }
}
