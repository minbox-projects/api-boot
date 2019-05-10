package org.minbox.framework.knowledge.library.common.dsl;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.ColumnExpression;
import com.gitee.hengboy.mybatis.enhance.dsl.expression.TableExpression;
import org.minbox.framework.knowledge.library.common.entity.*;

/**
 * <p>本类由Code-Builder自动生成</p>
 * <p>表名: kl_banner_info - 轮播图信息表 - 动态查询实体</p>
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
public class DBannerInfoEntity extends TableExpression<BannerInfoEntity> {
    public DBannerInfoEntity(String root) {
        super(root);
    }
    public static final DBannerInfoEntity DSL(){return new DBannerInfoEntity("kl_banner_info");}
    public ColumnExpression biId = new ColumnExpression("BI_ID",this);
    public ColumnExpression biJumpArticleId = new ColumnExpression("BI_JUMP_ARTICLE_ID",this);
    public ColumnExpression biStartTime = new ColumnExpression("BI_START_TIME",this);
    public ColumnExpression biEndTime = new ColumnExpression("BI_END_TIME",this);
    public ColumnExpression biStatus = new ColumnExpression("BI_STATUS",this);
    public ColumnExpression biCreateTime = new ColumnExpression("BI_CREATE_TIME",this);
    public ColumnExpression biMark = new ColumnExpression("BI_MARK",this);
    @Override
    public ColumnExpression[] getColumns() {
        return new ColumnExpression[]{
            biId
            ,
            biJumpArticleId
            ,
            biStartTime
            ,
            biEndTime
            ,
            biStatus
            ,
            biCreateTime
            ,
            biMark
            
        };
    }
}
