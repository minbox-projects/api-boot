package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_balance_type - 用户账户类型 - 动态查询实体</p>
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
public class DUserBalanceTypeEntity extends TableExpression<UserBalanceTypeEntity> {
    public DUserBalanceTypeEntity(String root) {
        super(root);
    }
    public static final DUserBalanceTypeEntity DSL(){return new DUserBalanceTypeEntity("kl_user_balance_type");}
    public ColumnExpression ubtId = new ColumnExpression("UBT_ID",this);
    public ColumnExpression ubtName = new ColumnExpression("UBT_NAME",this);
    public ColumnExpression ubtFlag = new ColumnExpression("UBT_FLAG",this);
    public ColumnExpression ubtMark = new ColumnExpression("UBT_MARK",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            ubtId
            ,
            ubtName
            ,
            ubtFlag
            ,
            ubtMark
            
        };
    }
}
