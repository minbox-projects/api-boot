package org.minbox.framework.knowledge.library.service.constants;

/**
 * 地址前缀
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-09 17:19
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface UrlPrefix {
    /**
     * 版本号
     */
    String VERSION = "/api/v1";
    /**
     * 用户前缀
     */
    String USER = VERSION + "/user";
    /**
     * 文章前缀
     */
    String ARTICLE = VERSION + "/article";
    /**
     * 账户前缀
     */
    String BALANCE = USER + "/balance";
    /**
     * 留言前缀
     */
    String COMMENT = VERSION + "/comment";
    /**
     * 轮播图前缀
     */
    String BANNER = VERSION + "/banner";
    /**
     * 小程序登录
     */
    String SMALL_PROGRAM_LOGIN = VERSION + "/sm/login";
    /**
     * 意见反馈
     */
    String FREEDBACK = VERSION + "/freedback";
}
