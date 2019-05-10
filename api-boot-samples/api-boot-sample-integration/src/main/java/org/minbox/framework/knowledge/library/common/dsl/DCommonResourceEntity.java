package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_common_resource - 统一资源 - 动态查询实体</p>
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
public class DCommonResourceEntity extends TableExpression<CommonResourceEntity> {
    public DCommonResourceEntity(String root) {
        super(root);
    }
    public static final DCommonResourceEntity DSL(){return new DCommonResourceEntity("kl_common_resource");}
    public ColumnExpression crId = new ColumnExpression("CR_ID",this);
    public ColumnExpression crTypeId = new ColumnExpression("CR_TYPE_ID",this);
    public ColumnExpression crTargetId = new ColumnExpression("CR_TARGET_ID",this);
    public ColumnExpression crUrl = new ColumnExpression("CR_URL",this);
    public ColumnExpression crCreateTime = new ColumnExpression("CR_CREATE_TIME",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            crId
            ,
            crTypeId
            ,
            crTargetId
            ,
            crUrl
            ,
            crCreateTime
            
        };
    }
}
