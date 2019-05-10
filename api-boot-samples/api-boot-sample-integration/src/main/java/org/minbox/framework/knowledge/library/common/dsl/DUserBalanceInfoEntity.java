package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_balance_info - 用户账户信息表 - 动态查询实体</p>
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
public class DUserBalanceInfoEntity extends TableExpression<UserBalanceInfoEntity> {
    public DUserBalanceInfoEntity(String root) {
        super(root);
    }
    public static final DUserBalanceInfoEntity DSL(){return new DUserBalanceInfoEntity("kl_user_balance_info");}
    public ColumnExpression ubiId = new ColumnExpression("UBI_ID",this);
    public ColumnExpression ubiUserId = new ColumnExpression("UBI_USER_ID",this);
    public ColumnExpression ubiTypeId = new ColumnExpression("UBI_TYPE_ID",this);
    public ColumnExpression ubiBalance = new ColumnExpression("UBI_BALANCE",this);
    public ColumnExpression ubiIsLock = new ColumnExpression("UBI_IS_LOCK",this);
    public ColumnExpression ubiCreateTime = new ColumnExpression("UBI_CREATE_TIME",this);
    public ColumnExpression ubiMark = new ColumnExpression("UBI_MARK",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            ubiId
            ,
            ubiUserId
            ,
            ubiTypeId
            ,
            ubiBalance
            ,
            ubiIsLock
            ,
            ubiCreateTime
            ,
            ubiMark
            
        };
    }
}
