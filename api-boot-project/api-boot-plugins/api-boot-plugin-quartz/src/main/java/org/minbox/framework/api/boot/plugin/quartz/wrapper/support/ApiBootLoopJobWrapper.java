package org.minbox.framework.api.boot.plugin.quartz.wrapper.support;

import lombok.Builder;
import lombok.Getter;
import org.minbox.framework.api.boot.plugin.quartz.wrapper.ApiBootJobParamWrapper;
import org.minbox.framework.api.boot.plugin.quartz.wrapper.ApiBootJobWrapper;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.util.Date;

/**
 * LOOP任务类型封装
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
public class ApiBootLoopJobWrapper extends ApiBootJobWrapper {
    /**
     * 任务执行循环次数
     */
    private int repeatTimes;
    /**
     * 默认执行任务间隔时间为0
     * loopIntervalTime gt 0 and loopTimes gt 0 and jobExecuteAway != CRON_EXPRESSION 根据间隔时间以及循环次数进行创建任务
     * <p>
     * loopIntervalTime lte 0 || loopTimes lte 0 || jobExecuteAway == CRON_EXPRESSION 根据cron表达式创建
     */
    private int loopIntervalTime;

    /**
     * 构造函数初始化父类的相关字段
     *
     * @param jobKey           任务key
     * @param loopIntervalTime 循环间隔次数
     * @param repeatTimes      重复次数
     * @param startAtTime      开始时间
     */
    @Builder(builderMethodName = "Context", buildMethodName = "wrapper")
    public ApiBootLoopJobWrapper(String jobKey, Class<? extends QuartzJobBean> jobClass, int repeatTimes, int loopIntervalTime, Date startAtTime, ApiBootJobParamWrapper param) {
        super(jobKey, jobClass, startAtTime, param);
        this.repeatTimes = repeatTimes;
        this.loopIntervalTime = loopIntervalTime;
    }

    /**
     * 获取每次重复之间的间隔时间
     *
     * @return 间隔时间毫秒值
     */
    public int getLoopIntervalTime() {
        return loopIntervalTime <= 0 ? 1000 : loopIntervalTime;
    }
}
