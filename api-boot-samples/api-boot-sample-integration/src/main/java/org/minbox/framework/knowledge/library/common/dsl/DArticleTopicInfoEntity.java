package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_article_topic_info - 文章专题信息表 - 动态查询实体</p>
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
public class DArticleTopicInfoEntity extends TableExpression<ArticleTopicInfoEntity> {
    public DArticleTopicInfoEntity(String root) {
        super(root);
    }
    public static final DArticleTopicInfoEntity DSL(){return new DArticleTopicInfoEntity("kl_article_topic_info");}
    public ColumnExpression atiId = new ColumnExpression("ATI_ID",this);
    public ColumnExpression atiName = new ColumnExpression("ATI_NAME",this);
    public ColumnExpression atiSort = new ColumnExpression("ATI_SORT",this);
    public ColumnExpression atiStatus = new ColumnExpression("ATI_STATUS",this);
    public ColumnExpression atiCreateTime = new ColumnExpression("ATI_CREATE_TIME",this);
    public ColumnExpression atiMark = new ColumnExpression("ATI_MARK",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            atiId
            ,
            atiName
            ,
            atiSort
            ,
            atiStatus
            ,
            atiCreateTime
            ,
            atiMark
            
        };
    }
}
