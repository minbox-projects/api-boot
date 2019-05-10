package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_user_like_record - 用户喜欢文章记录 - 动态查询实体</p>
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
public class DUserLikeRecordEntity extends TableExpression<UserLikeRecordEntity> {
    public DUserLikeRecordEntity(String root) {
        super(root);
    }
    public static final DUserLikeRecordEntity DSL(){return new DUserLikeRecordEntity("kl_user_like_record");}
    public ColumnExpression ulrId = new ColumnExpression("ULR_ID",this);
    public ColumnExpression ulrUserId = new ColumnExpression("ULR_USER_ID",this);
    public ColumnExpression ulrArticleId = new ColumnExpression("ULR_ARTICLE_ID",this);
    public ColumnExpression ulrCreateTime = new ColumnExpression("ULR_CREATE_TIME",this);
    public ColumnExpression urlMark = new ColumnExpression("URL_MARK",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            ulrId
            ,
            ulrUserId
            ,
            ulrArticleId
            ,
            ulrCreateTime
            ,
            urlMark
            
        };
    }
}
