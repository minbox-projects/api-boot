package org.minbox.framework.api.boot.quartz.wrapper.support;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-30 09:48
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */

import lombok.Builder;
import lombok.Getter;
import org.minbox.framework.api.boot.quartz.wrapper.ApiBootJobParamWrapper;
import org.minbox.framework.api.boot.quartz.wrapper.ApiBootJobWrapper;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * ONCE任务类型封装
 *
 * @author：于起宇 <p>
 * ================================
 * Created with IDEA.
 * Date：2019-01-23
 * Time：13:43
 * 个人博客：http://blog.yuqiyu.com
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 * </p>
 */
@Getter
public class ApiBootOnceJobWrapper extends ApiBootJobWrapper {
    /**
     * 构造函数初始化父类的相关字段
     *
     * @param jobKey      任务key
     * @param jobClass    任务执行类
     * @param param       参数集合
     * @param startAtTime 开始时间
     */
    @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
    public ApiBootOnceJobWrapper(String jobKey, Class<? extends QuartzJobBean> jobClass, Date startAtTime, ApiBootJobParamWrapper param) {
        super(jobKey, jobClass, startAtTime, param);
    }
}
