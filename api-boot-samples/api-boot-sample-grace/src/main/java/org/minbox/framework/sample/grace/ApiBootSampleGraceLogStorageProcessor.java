package org.minbox.framework.sample.grace;

import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.grace.processor.GraceLogObject;
import org.minbox.framework.grace.processor.GraceLogStorageProcessor;
import org.springframework.stereotype.Service;

/**
 * 日志持久化处理类
 *
 * @author 恒宇少年
 */
@Service
@Slf4j
public class ApiBootSampleGraceLogStorageProcessor implements GraceLogStorageProcessor {
    @Override
    public void storage(GraceLogObject graceLogObject) {
        log.info("位置：{}，日志内容：{}.", graceLogObject.getGeneratedLocation(), graceLogObject.getContent());
        // 更多字段参考GraceLogObject
    }
}
