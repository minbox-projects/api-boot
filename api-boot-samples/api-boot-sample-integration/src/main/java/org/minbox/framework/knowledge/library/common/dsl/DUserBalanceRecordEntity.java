package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_balance_record - 账户变动记录 - 动态查询实体</p>
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
public class DUserBalanceRecordEntity extends TableExpression<UserBalanceRecordEntity> {
    public DUserBalanceRecordEntity(String root) {
        super(root);
    }
    public static final DUserBalanceRecordEntity DSL(){return new DUserBalanceRecordEntity("kl_user_balance_record");}
    public ColumnExpression brId = new ColumnExpression("BR_ID",this);
    public ColumnExpression brBalanceId = new ColumnExpression("BR_BALANCE_ID",this);
    public ColumnExpression brBefore = new ColumnExpression("BR_BEFORE",this);
    public ColumnExpression brCount = new ColumnExpression("BR_COUNT",this);
    public ColumnExpression brAfter = new ColumnExpression("BR_AFTER",this);
    public ColumnExpression brCreateTime = new ColumnExpression("BR_CREATE_TIME",this);
    public ColumnExpression brMark = new ColumnExpression("BR_MARK",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            brId
            ,
            brBalanceId
            ,
            brBefore
            ,
            brCount
            ,
            brAfter
            ,
            brCreateTime
            ,
            brMark
            
        };
    }
}
