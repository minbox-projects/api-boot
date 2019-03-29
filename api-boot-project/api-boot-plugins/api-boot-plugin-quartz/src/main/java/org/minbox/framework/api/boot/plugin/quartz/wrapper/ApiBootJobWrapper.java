package org.minbox.framework.api.boot.plugin.quartz.wrapper;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.scheduling.quartz.QuartzJobBean;

import java.io.Serializable;
import java.util.Date;

/**
 * Job封装接口定义
 *
 * @author：于起宇 <p>
 * ================================
 * Created with IDEA.
 * Date：2019-01-23
 * Time：13:04
 * 个人博客：http://blog.yuqiyu.com
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 * </p>
 */
@Getter
@AllArgsConstructor
public class ApiBootJobWrapper implements Serializable {
    /**
     * 任务key
     */
    @Setter
    private String jobKey;
    /**
     * 任务Class
     */
    private Class<? extends QuartzJobBean> jobClass;
    /**
     * 开始执行的时间
     */
    private Date startAtTime;
    /**
     * 参数
     */
    private ApiBootJobParamWrapper param;

    /**
     * 不传递开启时间时，使用当前时间
     *
     * @return 获取开始时间
     */
    public Date getStartAtTime() {
        return startAtTime == null ? new Date() : startAtTime;
    }
}
