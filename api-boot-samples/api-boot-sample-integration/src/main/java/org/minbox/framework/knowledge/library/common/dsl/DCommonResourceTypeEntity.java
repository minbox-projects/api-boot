package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_common_resource_type - 统一资源类型 - 动态查询实体</p>
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
public class DCommonResourceTypeEntity extends TableExpression<CommonResourceTypeEntity> {
    public DCommonResourceTypeEntity(String root) {
        super(root);
    }
    public static final DCommonResourceTypeEntity DSL(){return new DCommonResourceTypeEntity("kl_common_resource_type");}
    public ColumnExpression crtId = new ColumnExpression("CRT_ID",this);
    public ColumnExpression crtName = new ColumnExpression("CRT_NAME",this);
    public ColumnExpression crtFlag = new ColumnExpression("CRT_FLAG",this);
    public ColumnExpression crtCreateTime = new ColumnExpression("CRT_CREATE_TIME",this);
    public ColumnExpression crtMark = new ColumnExpression("CRT_MARK",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            crtId
            ,
            crtName
            ,
            crtFlag
            ,
            crtCreateTime
            ,
            crtMark
            
        };
    }
}
