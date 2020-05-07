package org.minbox.framework.api.boot.autoconfigure.sequence;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import static org.minbox.framework.api.boot.autoconfigure.sequence.ApiBootSequenceProperties.API_BOOT_SEQUENCE_PREFIX;

/**
 * 整合Sequence分布式高效ID的属性相关配置
 *
 * @author 恒宇少年
 */
@Data
@Configuration
@ConfigurationProperties(prefix = API_BOOT_SEQUENCE_PREFIX)
public class ApiBootSequenceProperties {
    /**
     * 分布式ID配置前缀
     */
    public static final String API_BOOT_SEQUENCE_PREFIX = "api.boot.sequence";
    /**
     * 数据中心编号，取值范围为0 ~ 3
     **/
    private int dataCenterId = 1;
    /**
     * 工作机器编号，取值范围为0 ~ 255
     */
    private int workerId = 1;
    /**
     * 是否解决高并发下获取时间戳的性能问题
     */
    private boolean clock;
    /**
     * 允许时间回拨的毫秒量
     */
    private long timeOffsetMilliseconds = 5L;
    /**
     * 毫秒内的随机序列
     */
    private boolean randomSequence;
}
