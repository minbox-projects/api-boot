package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_feedback - 意见反馈信息表 - 动态查询实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：Apr 25, 2019 2:42:31 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
public class DFeedbackEntity extends TableExpression<FeedbackEntity> {
    public DFeedbackEntity(String root) {
        super(root);
    }
    public static final DFeedbackEntity DSL(){return new DFeedbackEntity("kl_feedback");}
    public ColumnExpression kfId = new ColumnExpression("KF_ID",this);
    public ColumnExpression kfUserId = new ColumnExpression("KF_USER_ID",this);
    public ColumnExpression kfContent = new ColumnExpression("KF_CONTENT",this);
    public ColumnExpression kfCreateTime = new ColumnExpression("KF_CREATE_TIME",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            kfId
            ,
            kfUserId
            ,
            kfContent
            ,
            kfCreateTime
            
        };
    }
}
