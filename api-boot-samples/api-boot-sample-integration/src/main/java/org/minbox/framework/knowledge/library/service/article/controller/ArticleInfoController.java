package org.minbox.framework.knowledge.library.service.article.controller;

import io.swagger.annotations.*;
import org.minbox.framework.api.boot.common.model.ApiBootResult;
import org.minbox.framework.knowledge.library.common.entity.ArticleInfoEntity;
import org.minbox.framework.knowledge.library.common.entity.UserInfoEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.article.dto.ArticleInfoDTO;
import org.minbox.framework.knowledge.library.service.article.service.ArticleInfoService;
import org.minbox.framework.knowledge.library.service.constants.UrlPrefix;
import org.minbox.framework.knowledge.library.service.security.SecurityUserTools;
import org.minbox.framework.knowledge.library.service.user.service.UserLikeRecordService;
import org.minbox.framework.knowledge.library.service.user.service.UserReadRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 文章相关控制器
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 11:24
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RestController
@RequestMapping(value = UrlPrefix.ARTICLE)
@Api(tags = "文章")
public class ArticleInfoController {
    /**
     * 文章业务逻辑
     */
    @Autowired
    private ArticleInfoService articleInfoService;
    /**
     * 喜欢文章记录
     */
    @Autowired
    private UserLikeRecordService userLikeRecordService;
    /**
     * 用户阅读记录
     */
    @Autowired
    private UserReadRecordService userReadRecordService;

    /**
     * 查询推荐文章列表
     *
     * @return
     * @throws KnowledgeException
     */
    @GetMapping(value = "/recommend/{pageIndex}/{pageSize}")
    @ApiOperation(value = "查询推荐文章列表", response = ArticleInfoDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "分页参数：当前页码", dataType = "integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页条数", dataType = "integer", defaultValue = "20")
    })
    @ApiResponse(code = 200, message = "查询成功", response = ArticleInfoDTO.class)
    public ApiBootResult getRecommendArticleList(@PathVariable int pageIndex, @PathVariable int pageSize)
            throws KnowledgeException {
        return ApiBootResult.builder().data(articleInfoService.pageableOfRecommendArticleList(pageIndex, pageSize)).build();
    }

    /**
     * 查询热门文章列表
     *
     * @return
     * @throws KnowledgeException
     */
    @GetMapping(value = "/hot/{pageIndex}/{pageSize}")
    @ApiOperation(value = "查询热门文章列表", response = ArticleInfoDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "分页参数：当前页码", dataType = "integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页条数", dataType = "integer", defaultValue = "20")
    })
    @ApiResponse(code = 200, message = "查询成功", response = ArticleInfoDTO.class)
    public ApiBootResult getHotArticleList(@PathVariable int pageIndex, @PathVariable int pageSize)
            throws KnowledgeException {
        return ApiBootResult.builder().data(articleInfoService.pageableOfHotArticleList(pageIndex, pageSize)).build();
    }

    /**
     * 查询专题下的文章列表
     *
     * @return
     * @throws KnowledgeException
     */
    @GetMapping(value = "/topic/{topicId}/{pageIndex}/{pageSize}")
    @ApiOperation(value = "查询专题下的文章列表", response = ArticleInfoDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "topicId", value = "专题编号", dataType = "String"),
            @ApiImplicitParam(name = "pageIndex", value = "分页参数：当前页码", dataType = "integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页条数", dataType = "integer", defaultValue = "20")
    })
    @ApiResponse(code = 200, message = "查询成功", response = ArticleInfoDTO.class)
    public ApiBootResult getTopicArticleList(@PathVariable String topicId, @PathVariable int pageIndex, @PathVariable int pageSize)
            throws KnowledgeException {
        return ApiBootResult.builder().data(articleInfoService.pageableOfTopicArticleList(pageIndex, pageSize, topicId)).build();
    }

    /**
     * 查询最新文章列表
     *
     * @return
     * @throws KnowledgeException
     */
    @GetMapping(value = "/new/{pageIndex}/{pageSize}")
    @ApiOperation(value = "查询最新文章列表", response = ArticleInfoDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageIndex", value = "分页参数：当前页码", dataType = "integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页条数", dataType = "integer", defaultValue = "5")
    })
    @ApiResponse(code = 200, message = "查询成功", response = ArticleInfoDTO.class)
    public ApiBootResult getNewArticleList(@PathVariable int pageIndex, @PathVariable int pageSize)
            throws KnowledgeException {
        return ApiBootResult.builder().data(articleInfoService.pageableOfNewArticleList(pageIndex, pageSize)).build();
    }

    /**
     * 关键字查询文章列表
     *
     * @param keyword   关键字
     * @param pageIndex 分页参数：当前页码
     * @param pageSize  分页参数：每页条数
     * @return
     * @throws KnowledgeException
     */
    @GetMapping(value = "/search")
    @ApiOperation(value = "关键字查询文章", response = ArticleInfoDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "keyword", value = "查询关键字", dataType = "String", required = true),
            @ApiImplicitParam(name = "pageIndex", value = "分页参数：当前页码", dataType = "integer", defaultValue = "1"),
            @ApiImplicitParam(name = "pageSize", value = "分页参数：每页条数", dataType = "integer", defaultValue = "5")
    })
    @ApiResponse(code = 200, message = "查询成功", response = ArticleInfoDTO.class)
    public ApiBootResult searchWithKeyword(String keyword, int pageIndex, int pageSize) throws KnowledgeException {
        return ApiBootResult.builder().data(articleInfoService.pageableOfKeyWordArticleList(keyword, pageIndex, pageSize)).build();
    }

    /**
     * 同文推荐
     * 目前推荐文章专题内的热门文章
     *
     * @param articleId 类似文章的编号
     * @return
     * @throws KnowledgeException
     */
    @GetMapping(value = "/recommend/similar")
    @ApiOperation(value = "同文推荐", response = ArticleInfoDTO.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章编号", dataType = "String", required = true)
    })
    @ApiResponse(code = 200, message = "查询成功", response = ArticleInfoDTO.class)
    public ApiBootResult similarRecommend(String articleId) throws KnowledgeException {
        return ApiBootResult.builder().data(articleInfoService.similarRecommend(articleId)).build();
    }

    /**
     * 文章详情
     *
     * @param articleId 类似文章的编号
     * @return
     * @throws KnowledgeException
     */
    @GetMapping(value = "/{articleId}")
    @ApiOperation(value = "文章详情", response = ArticleInfoEntity.class)
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章编号", dataType = "String", required = true)
    })
    @ApiResponse(code = 200, message = "查询成功", response = ArticleInfoEntity.class)
    public ApiBootResult selectDetail(@PathVariable String articleId) throws KnowledgeException {
        // 获取登录用户信息
        UserInfoEntity userInfoEntity = SecurityUserTools.getCurrentUser().getUser();

        // 查询文章详情
        ArticleInfoDTO article = articleInfoService.selectArticleDetail(articleId);

        // 设置是否喜欢该文章
        boolean isLike = userLikeRecordService.isLikeArticle(userInfoEntity.getUiId(), articleId);
        article.setIsLike(isLike);

        // 写入阅读记录
        userReadRecordService.insertReadRecord(userInfoEntity.getUiId(), articleId);

        // 更新文章阅读次数
        articleInfoService.updateArticleReadCount(articleId, article.getAiReadCount(), 1);

        return ApiBootResult.builder().data(article).build();
    }

    /**
     * 喜欢文章
     *
     * @param articleId 文章编号
     * @return
     * @throws KnowledgeException
     */
    @ApiOperation(value = "喜欢文章")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章编号", dataType = "String", required = true)
    })
    @PostMapping(value = "/like/{articleId}")
    public ApiBootResult likeArticle(@PathVariable String articleId) throws KnowledgeException {
        // 获取登录用户信息
        UserInfoEntity userInfoEntity = SecurityUserTools.getCurrentUser().getUser();
        // 喜欢文章
        int articleLikeCount = articleInfoService.likeArticle(userInfoEntity.getUiId(), articleId);
        return ApiBootResult.builder().data(articleLikeCount).build();
    }

    /**
     * 分享文章后回调
     *
     * @param articleId 文章编号
     * @return
     * @throws KnowledgeException
     */
    @ApiOperation(value = "分享回调")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "articleId", value = "文章编号", dataType = "String", required = true)
    })
    @PostMapping(value = "/share/callback/{articleId}")
    public ApiBootResult shareCallBack(@PathVariable String articleId) throws KnowledgeException {
        // 执行分享回调
        articleInfoService.shareCallBack(articleId);
        return ApiBootResult.builder().build();
    }
}
