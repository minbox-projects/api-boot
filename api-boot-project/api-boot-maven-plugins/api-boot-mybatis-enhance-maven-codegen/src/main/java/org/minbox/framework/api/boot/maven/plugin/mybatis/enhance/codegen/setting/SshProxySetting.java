package org.minbox.framework.api.boot.maven.plugin.mybatis.enhance.codegen.setting;

import lombok.Data;
import org.minbox.framework.ssh.agent.config.AgentConfig;

/**
 * Ssh代理设置参数
 *
 * @author 恒宇少年
 */
@Data
public class SshProxySetting extends AgentConfig {
    public SshProxySetting() {
        // 代理连接本地的端口号，默认为3306
        setLocalPort(3306);
        // 代理连接目标的端口号，默认为3306
        setForwardTargetPort(3306);
    }
}
