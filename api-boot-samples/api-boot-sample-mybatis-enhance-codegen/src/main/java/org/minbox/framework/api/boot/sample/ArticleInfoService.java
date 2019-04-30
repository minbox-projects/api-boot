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

package org.minbox.framework.api.boot.sample;

import com.gitee.hengboy.mybatis.enhance.dsl.factory.EnhanceDslFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-30 15:45
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
public class ArticleInfoService {
    /**
     * Mybatis Enhance Dsl Factory
     */
    @Autowired
    private EnhanceDslFactory dslFactory;

    /**
     * 根据文章编号查询示例
     *
     * @param articleId 文章编号
     * @return
     */
    public KlArticleInfo selectById(String articleId) {
        DKlArticleInfo dKlArticleInfo = DKlArticleInfo.DSL();
        return dslFactory.createSearchable()
                .selectFrom(dKlArticleInfo)
                // 文章主键
                .where(dKlArticleInfo.aiId.eq(articleId))
                // and 状态正常
                .and(dKlArticleInfo.aiStatus.eq("O"))
                .resultType(KlArticleInfo.class)
                .fetchOne();
    }
}
