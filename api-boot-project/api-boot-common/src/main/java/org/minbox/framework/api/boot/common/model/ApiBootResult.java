package org.minbox.framework.api.boot.common.model;

import lombok.Builder;
import lombok.Data;

/**
 * ApiBoot提供的接口返回对象
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-18 10:57
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Data
@Builder
public class ApiBootResult {
    /**
     * 返回数据内容
     */
    private Object data;
    /**
     * 遇到业务异常或者系统异常时的错误码
     */
    private String errorCode;
    /**
     * 遇到业务异常或者系统异常时的错误消息提示内容
     */
    private String errorMessage;
}
