package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_read_record - 文章阅读记录 - 动态查询实体</p>
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
public class DUserReadRecordEntity extends TableExpression<UserReadRecordEntity> {
    public DUserReadRecordEntity(String root) {
        super(root);
    }
    public static final DUserReadRecordEntity DSL(){return new DUserReadRecordEntity("kl_user_read_record");}
    public ColumnExpression urrId = new ColumnExpression("URR_ID",this);
    public ColumnExpression urrUserId = new ColumnExpression("URR_USER_ID",this);
    public ColumnExpression urrArticleId = new ColumnExpression("URR_ARTICLE_ID",this);
    public ColumnExpression urrCreateTime = new ColumnExpression("URR_CREATE_TIME",this);
    public ColumnExpression urrMark = new ColumnExpression("URR_MARK",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            urrId
            ,
            urrUserId
            ,
            urrArticleId
            ,
            urrCreateTime
            ,
            urrMark
            
        };
    }
}
