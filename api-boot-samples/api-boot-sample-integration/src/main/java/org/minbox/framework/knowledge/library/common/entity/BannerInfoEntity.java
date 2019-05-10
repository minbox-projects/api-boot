/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.minbox.framework.knowledge.library.common.entity;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Column;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Id;
import com.gitee.hengboy.mybatis.enhance.common.annotation.Table;
import com.gitee.hengboy.mybatis.enhance.common.enums.KeyGeneratorTypeEnum;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.sql.Timestamp;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_banner_info - 轮播图信息表 - 数据实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：Apr 9, 2019 1:08:01 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
@Data
@Table(name = "kl_banner_info")
@ApiModel
public class BannerInfoEntity {
    /**
     * BI_ID - 主键自增
     */
    @Id(generatorType = KeyGeneratorTypeEnum.AUTO)
    @Column(name="BI_ID")
    @ApiModelProperty("主键自增")
    private Integer biId;
    /**
     * BI_JUMP_ARTICLE_ID - 点击跳转文章编号，关联kl_article_info主键
     */
    @Column(name="BI_JUMP_ARTICLE_ID")
    @ApiModelProperty("点击跳转文章编号，关联kl_article_info主键")
    private String biJumpArticleId;
    /**
     * BI_START_TIME - 展示开始时间
     */
    @Column(name="BI_START_TIME")
    @ApiModelProperty("展示开始时间")
    private Timestamp biStartTime;
    /**
     * BI_END_TIME - 展示结束时间
     */
    @Column(name="BI_END_TIME")
    @ApiModelProperty("展示结束时间")
    private Timestamp biEndTime;
    /**
     * BI_STATUS - 轮播图状态，O：正常，D：已删除
     */
    @Column(name="BI_STATUS",insertable = false)
    @ApiModelProperty("轮播图状态，O：正常，D：已删除")
    private String biStatus;
    /**
     * BI_CREATE_TIME - 创建时间
     */
    @Column(name="BI_CREATE_TIME",insertable = false)
    @ApiModelProperty("创建时间")
    private Timestamp biCreateTime;
    /**
     * BI_MARK - 备注信息
     */
    @Column(name="BI_MARK")
    @ApiModelProperty("备注信息")
    private String biMark;
}
