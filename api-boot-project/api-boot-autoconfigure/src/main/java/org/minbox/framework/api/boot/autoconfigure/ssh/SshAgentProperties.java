package org.minbox.framework.api.boot.autoconfigure.ssh;

import lombok.Data;
import org.minbox.framework.ssh.agent.config.AgentConfig;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.ssh.SshAgentProperties.SSH_AGENT_PREFIX;

/**
 * Ssh agent config properties
 *
 * @author 恒宇少年
 */
@Data
@Configuration
@ConfigurationProperties(prefix = SSH_AGENT_PREFIX)
public class SshAgentProperties {
    /**
     * The config prefix of ssh-agent
     */
    public static final String SSH_AGENT_PREFIX = "api.boot.ssh-agent";
    /**
     * The config collection of {@link AgentConfig}
     * <p>
     * Use this parameter to configure proxy multiple remote server port forwarding information
     */
    private List<AgentConfig> configs;
}
