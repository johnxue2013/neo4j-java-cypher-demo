package com.example.demo.config;

import org.neo4j.driver.v1.AuthTokens;
import org.neo4j.driver.v1.Driver;
import org.neo4j.driver.v1.GraphDatabase;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author han.xue
 * @since 2021-08-11 16:49:49
 */
@Configuration
public class Neo4jConfig {

    @Value("${spring.data.neo4j.uri}")
    private String uri;

    @Value("${spring.data.neo4j.username}")
    private String username;

    @Value("${spring.data.neo4j.password}")
    private String password;

    @Bean
    public Driver driver() {
        // A driver maintains a connection pool for each remote Neo4j server. Therefore
        // the most efficient way to make use of a Driver is to use the same instance
        // across the application.
        return GraphDatabase.driver(uri, AuthTokens.basic(username, password));
    }

}
