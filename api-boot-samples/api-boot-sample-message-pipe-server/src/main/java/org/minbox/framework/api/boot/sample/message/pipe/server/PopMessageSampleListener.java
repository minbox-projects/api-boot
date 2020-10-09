package org.minbox.framework.api.boot.sample.message.pipe.server;

import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.message.pipe.server.processing.pop.PopMessageEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * The {@link PopMessageEvent} sample
 *
 * @author 恒宇少年
 */
@Component
@Slf4j
public class PopMessageSampleListener implements ApplicationListener<PopMessageEvent> {
    @Override
    public void onApplicationEvent(PopMessageEvent event) {
        log.info("消息管道：{}，获取左面第一个消息.", event.getPipeName());
    }
}
