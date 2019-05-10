package org.minbox.framework.knowledge.library.service.article.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.article.dto.ArticleTopicInfoDTO;
import org.minbox.framework.knowledge.library.service.article.service.ArticleTopicInfoService;
import org.minbox.framework.knowledge.library.service.constants.UrlPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 文章专题控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 10:49
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
@RequestMapping(value = UrlPrefix.ARTICLE + "/topic")
@Api(tags = "文章专题")
public class ArticleTopicInfoController {
    /**
     * 文章专题
     */
    @Autowired
    private ArticleTopicInfoService articleTopicInfoService;

    /**
     * 查询全部文章专题
     *
     * @return
     * @throws KnowledgeException
     */
    @GetMapping(value = "/")
    @ApiOperation(value = "查询文章专题列表", response = ArticleTopicInfoDTO.class)
    @ApiResponse(code = 200, message = "查询成功", response = ArticleTopicInfoDTO.class)
    @PreAuthorize("isAuthenticated()")
    public ApiBootResult getAllTopic()
            throws KnowledgeException {
        return ApiBootResult.builder().data(articleTopicInfoService.selectAllTopic()).build();
    }
}
