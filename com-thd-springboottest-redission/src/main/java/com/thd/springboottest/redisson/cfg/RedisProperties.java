package com.thd.springboottest.redisson.cfg;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.redisson.cfg.RedissonProperties
 *
 * @author: wanglei62
 * @DATE: 2020/3/13 12:28
 **/

@Component
@ConfigurationProperties(prefix = "spring.redis")
public class RedisProperties {
    private String host;
    private int port;
    private String password;
    private int database;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getDatabase() {
        return database;
    }

    public void setDatabase(int database) {
        this.database = database;
    }
}