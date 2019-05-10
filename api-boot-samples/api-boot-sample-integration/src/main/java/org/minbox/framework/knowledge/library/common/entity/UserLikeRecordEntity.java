package org.minbox.framework.knowledge.library.common.entity;
import java.sql.Timestamp;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_like_record - 用户喜欢文章记录 - 数据实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：Apr 9, 2019 1:08:02 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
@Data
@Table(name = "kl_user_like_record")
@ApiModel
public class UserLikeRecordEntity {
    /**
     * ULR_ID - 主键自增
     */
    @Id(generatorType = KeyGeneratorTypeEnum.AUTO)
    @Column(name="ULR_ID")
    @ApiModelProperty("主键自增")
    private Integer ulrId;
    /**
     * ULR_USER_ID - 用户编号，关联kl_user_info主键
     */
    @Column(name="ULR_USER_ID")
    @ApiModelProperty("用户编号，关联kl_user_info主键")
    private String ulrUserId;
    /**
     * ULR_ARTICLE_ID - 文章编号，关联kl_article_info主键
     */
    @Column(name="ULR_ARTICLE_ID")
    @ApiModelProperty("文章编号，关联kl_article_info主键")
    private String ulrArticleId;
    /**
     * ULR_CREATE_TIME - 创建时间
     */
    @Column(name="ULR_CREATE_TIME",insertable = false)
    @ApiModelProperty("创建时间")
    private Timestamp ulrCreateTime;
    /**
     * URL_MARK - 备注信息
     */
    @Column(name="URL_MARK")
    @ApiModelProperty("备注信息")
    private String urlMark;
}