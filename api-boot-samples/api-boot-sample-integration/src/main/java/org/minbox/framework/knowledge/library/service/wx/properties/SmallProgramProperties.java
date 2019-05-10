package org.minbox.framework.knowledge.library.service.wx.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * 小程序属性配置
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-11 14:27
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@ConfigurationProperties(prefix = "knowledge.library.small.program")
@Configuration
public class SmallProgramProperties {
    /**
     * App Id
     */
    private String appId;
    /**
     * App Secret
     */
    private String appSecret;
    /**
     * jsCode 换取session路径
     */
    private String codeToSessionUrl;
}
