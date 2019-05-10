package org.minbox.framework.knowledge.library.service.banner.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.article.dto.ArticleTopicInfoDTO;
import org.minbox.framework.knowledge.library.service.banner.service.BannerInfoService;
import org.minbox.framework.knowledge.library.service.constants.UrlPrefix;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 轮播图控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 10:54
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
@RequestMapping(value = UrlPrefix.BANNER)
@Api(tags = "轮播图")
public class BannerInfoController {
    /**
     * 轮播图业务逻辑
     */
    @Autowired
    private BannerInfoService bannerInfoService;

    /**
     * 查询全部轮播图
     *
     * @return
     * @throws KnowledgeException
     */
    @GetMapping(value = "/")
    @ApiOperation(value = "查询文章专题列表", response = ArticleTopicInfoDTO.class)
    @ApiResponse(code = 200, message = "查询成功", response = ArticleTopicInfoDTO.class)
    public ApiBootResult getAllBanner()
            throws KnowledgeException {
        return ApiBootResult.builder().data(bannerInfoService.selectAllBanner()).build();
    }
}
