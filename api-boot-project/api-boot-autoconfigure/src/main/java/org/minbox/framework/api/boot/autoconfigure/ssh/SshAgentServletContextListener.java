package org.minbox.framework.api.boot.autoconfigure.ssh;

import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.ssh.agent.AgentConnection;
import org.minbox.framework.ssh.agent.AgentSupport;
import org.minbox.framework.ssh.agent.apache.ApacheMinaSshdAgentConnection;
import org.minbox.framework.ssh.agent.config.AgentConfig;
import org.minbox.framework.ssh.agent.jsch.JSchAgentConnection;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.util.ArrayList;
import java.util.List;

/**
 * The ssh-agent web listener
 * <p>
 * Establish a proxy forwarding channel after the servlet context is created when the project starts
 *
 * @author 恒宇少年
 */
@Configuration
@Slf4j
@WebListener
public class SshAgentServletContextListener implements ServletContextListener {
    /**
     * The ssh-agent auto config properties
     */
    private final SshAgentProperties sshAgentProperties;
    /**
     * Cache a list of AgentConnection objects
     */
    private final List<AgentConnection> connections = new ArrayList<>();

    public SshAgentServletContextListener(SshAgentProperties sshAgentProperties) {
        this.sshAgentProperties = sshAgentProperties;
    }

    /**
     * {@link ServletContext} initialized method
     * <p>
     * Create an {@link AgentConnection} instance according to each {@link AgentConfig} and perform port forwarding connection
     *
     * @param sce The {@link ServletContextEvent} instance
     * @see JSchAgentConnection
     * @see ApacheMinaSshdAgentConnection
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        List<AgentConfig> configs = sshAgentProperties.getConfigs();
        if (ObjectUtils.isEmpty(configs)) {
            log.warn("ssh-agent agent does not take effect, reason: agent information is not configured.");
            return;
        }
        configs.forEach(config -> {
            try {
                AgentSupport agentSupport = sshAgentProperties.getAgentSupport();
                AgentConnection connection = (AgentConnection) Class.forName(agentSupport.getClassName()).getDeclaredConstructor(AgentConfig.class).newInstance(config);
                this.connections.add(connection);
                connection.connect();
            } catch (Exception e) {
                log.error("Connection：{}:{}，try agent failure.", config.getServerIp(), config.getForwardTargetPort(), e);
            }
        });
    }

    /**
     * {@link ServletContext} destroy method
     * <p>
     * Disconnect all connections in the AgentConnection cache list
     *
     * @param sce The {@link ServletContextEvent} instance
     */
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        connections.forEach(AgentConnection::disconnect);
    }
}
