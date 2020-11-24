package org.minbox.framework.api.boot.autoconfigure.ssh;

import org.minbox.framework.ssh.agent.AgentConnection;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * The ssh agent configuration
 *
 * @author 恒宇少年
 * @see SshAgentServletContextListener
 */
@Configuration
@ConditionalOnClass(AgentConnection.class)
@EnableConfigurationProperties(SshAgentProperties.class)
@Import(SshAgentServletContextListener.class)
public class SshAgentAutoConfiguration {
    // ...
}
