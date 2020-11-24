package org.minbox.framework.api.boot.autoconfigure.ssh;

import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.ssh.agent.AgentConnection;
import org.minbox.framework.ssh.agent.DefaultAgentConnection;
import org.minbox.framework.ssh.agent.config.AgentConfig;
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
    private SshAgentProperties sshAgentProperties;
    /**
     * Cache a list of AgentConnection objects
     */
    private List<AgentConnection> connections = new ArrayList<>();

    public SshAgentServletContextListener(SshAgentProperties sshAgentProperties) {
        this.sshAgentProperties = sshAgentProperties;
    }

    /**
     * {@link ServletContext} initialized method
     * <p>
     * Create an {@link AgentConnection} instance according to each {@link AgentConfig} and perform port forwarding connection
     *
     * @param sce The {@link ServletContextEvent} instance
     * @see DefaultAgentConnection
     */
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        List<AgentConfig> configs = sshAgentProperties.getConfigs();
        if (ObjectUtils.isEmpty(configs)) {
            log.warn("ssh-agent agent does not take effect, reason: agent information is not configured.");
            return;
        }
        configs.stream().forEach(config -> {
            AgentConnection connection = new DefaultAgentConnection(config);
            this.connections.add(connection);
            connection.connect();
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
        connections.stream().forEach(connection -> connection.disconnect());
    }
}
