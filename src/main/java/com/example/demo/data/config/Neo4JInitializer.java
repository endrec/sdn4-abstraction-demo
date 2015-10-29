package com.example.demo.data.config;

import lombok.extern.slf4j.Slf4j;
import org.neo4j.ogm.session.Session;
import org.neo4j.ogm.session.SessionFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.data.neo4j.repository.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.server.Neo4jServer;
import org.springframework.data.neo4j.server.RemoteServer;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author Endre Czirbesz <endre@czirbesz.hu>
 */
@Slf4j
@Configuration
@EnableNeo4jRepositories(basePackages = "com.example.demo.data.repository")
@EnableTransactionManagement
public class Neo4JInitializer extends Neo4jConfiguration {
    @Value("${neo4j.url:}")
    private String url;

    @Value("${neo4j.username:}")
    private String username;

    @Value("${neo4j.password:}")
    private String password;

    @Override
    public Neo4jServer neo4jServer() {
        if (url != null) {
            final Neo4jServer remoteServer;
            if (username != null && password != null) {
                remoteServer = new RemoteServer(url, username, password);

            } else {
                remoteServer = new RemoteServer(url);
            }
            log.debug("Neo4j server: {}", remoteServer.url());
            return remoteServer;
        }
        throw new UnsupportedOperationException("Unable to set up Neo4j Server connection. Please check your configuration.");
    }

    @Override
    public SessionFactory getSessionFactory() {
        return new SessionFactory("com.example.demo.domain.node");
    }

    @Bean
    @Override
    @Scope(value = "session", proxyMode = ScopedProxyMode.TARGET_CLASS)
    public Session getSession() throws Exception {
        return super.getSession();
    }
}
