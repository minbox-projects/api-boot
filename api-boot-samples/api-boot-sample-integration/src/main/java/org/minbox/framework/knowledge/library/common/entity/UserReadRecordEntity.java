package org.minbox.framework.knowledge.library.common.entity;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_read_record - 文章阅读记录 - 数据实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：Apr 9, 2019 5:26:37 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
@Data
@Table(name = "kl_user_read_record")
@ApiModel
public class UserReadRecordEntity implements Serializable {
    /**
     * URR_ID - 主键自增
     */
    @Id(generatorType = KeyGeneratorTypeEnum.AUTO)
    @Column(name="URR_ID")
    @ApiModelProperty("主键自增")
    private Integer urrId;
    /**
     * URR_USER_ID - 阅读用户编号，关联kl_user_info主键
     */
    @Column(name="URR_USER_ID")
    @ApiModelProperty("阅读用户编号，关联kl_user_info主键")
    private String urrUserId;
    /**
     * URR_ARTICLE_ID - 文章编号，关联kl_article_info主键
     */
    @Column(name="URR_ARTICLE_ID")
    @ApiModelProperty("文章编号，关联kl_article_info主键")
    private String urrArticleId;
    /**
     * URR_CREATE_TIME - 阅读时间
     */
    @Column(name="URR_CREATE_TIME",insertable = false)
    @ApiModelProperty("阅读时间")
    private Timestamp urrCreateTime;
    /**
     * URR_MARK - 备注信息
     */
    @Column(name="URR_MARK")
    @ApiModelProperty("备注信息")
    private String urrMark;
}
