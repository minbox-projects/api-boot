package org.minbox.framework.api.boot.autoconfigure.sequence;


import org.minbox.framework.sequence.Sequence;

/**
 * 封装{@link Sequence}的上下文
 * <p>
 * 通过该类可以直接获取分布式唯一的ID{@link Sequence#nextId()}
 *
 * @author 恒宇少年
 */
public class ApiBootSequenceContext {
    /**
     * 分布式唯一ID实例
     */
    private final Sequence sequence;

    /**
     * 构造函数实例化{@link Sequence}
     * <p>
     * 根据传递的{@link ApiBootSequenceProperties}属性配置类各个字段的值来实例化{@link Sequence}
     *
     * @param sequenceProperties Sequence属性配置类
     */
    public ApiBootSequenceContext(ApiBootSequenceProperties sequenceProperties) {
        this.sequence = new Sequence(
            sequenceProperties.getDataCenterId(),
            sequenceProperties.getWorkerId(),
            sequenceProperties.isClock(),
            sequenceProperties.getTimeOffsetMilliseconds(),
            sequenceProperties.isRandomSequence()
        );
    }

    /**
     * 获取下一个分布式ID的值
     * <p>
     * 返回{@link String}类型的值
     *
     * @return 下一个ID的值
     */
    public String nextStringId() {
        return String.valueOf(this.nextId());
    }

    /**
     * 获取下一个分布式ID的值
     * <p>
     * 返回{@link Long} 类型的值
     *
     * @return 下一个ID的值
     */
    public Long nextId() {
        return this.sequence.nextId();
    }
}
