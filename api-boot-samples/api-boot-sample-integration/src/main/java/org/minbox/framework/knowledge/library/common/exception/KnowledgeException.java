package org.minbox.framework.knowledge.library.common.exception;

import lombok.NoArgsConstructor;

/**
 * 知识库异常定义
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-09 17:06
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@NoArgsConstructor
public class KnowledgeException extends RuntimeException {
    /**
     * 消息内容构造函数
     *
     * @param message
     */
    public KnowledgeException(String message) {
        super(message);
    }

    /**
     * 消息内容、异常堆栈构造函数
     *
     * @param message 消息内容
     * @param cause   堆栈信息
     */
    public KnowledgeException(String message, Throwable cause) {
        super(message, cause);
    }
}
