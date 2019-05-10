package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_article_topic_uni - 文章专题关联信息表 - 动态查询实体</p>
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
public class DArticleTopicUniEntity extends TableExpression<ArticleTopicUniEntity> {
    public DArticleTopicUniEntity(String root) {
        super(root);
    }
    public static final DArticleTopicUniEntity DSL(){return new DArticleTopicUniEntity("kl_article_topic_uni");}
    public ColumnExpression atuId = new ColumnExpression("ATU_ID",this);
    public ColumnExpression atuArticleId = new ColumnExpression("ATU_ARTICLE_ID",this);
    public ColumnExpression atuTopicId = new ColumnExpression("ATU_TOPIC_ID",this);
    public ColumnExpression atuCreateTime = new ColumnExpression("ATU_CREATE_TIME",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            atuId
            ,
            atuArticleId
            ,
            atuTopicId
            ,
            atuCreateTime
            
        };
    }
}
