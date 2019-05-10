package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_info - 用户基本信息 - 动态查询实体</p>
 *
 * @author Code-Builder
 * @since 恒宇少年
 * ===============================
 * Created with Code-Builder.
 * User：
 * Date：Apr 9, 2019 1:27:51 PM
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 */
public class DUserInfoEntity extends TableExpression<UserInfoEntity> {
    public DUserInfoEntity(String root) {
        super(root);
    }
    public static final DUserInfoEntity DSL(){return new DUserInfoEntity("kl_user_info");}
    public ColumnExpression uiId = new ColumnExpression("UI_ID",this);
    public ColumnExpression uiNickName = new ColumnExpression("UI_NICK_NAME",this);
    public ColumnExpression uiOpenId = new ColumnExpression("UI_OPEN_ID",this);
    public ColumnExpression uiPassword = new ColumnExpression("UI_PASSWORD",this);
    public ColumnExpression uiLastLoginTime = new ColumnExpression("UI_LAST_LOGIN_TIME",this);
    public ColumnExpression uiStatus = new ColumnExpression("UI_STATUS",this);
    public ColumnExpression uiIsLock = new ColumnExpression("UI_IS_LOCK",this);
    public ColumnExpression uiCreateTime = new ColumnExpression("UI_CREATE_TIME",this);
    public ColumnExpression uiMark = new ColumnExpression("UI_MARK",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            uiId
            ,
            uiNickName
            ,
            uiOpenId
            ,
            uiPassword
            ,
            uiLastLoginTime
            ,
            uiStatus
            ,
            uiIsLock
            ,
            uiCreateTime
            ,
            uiMark
            
        };
    }
}
