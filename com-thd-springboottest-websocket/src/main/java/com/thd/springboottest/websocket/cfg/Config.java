package com.thd.springboottest.websocket.cfg;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

/**
 * com.thd.springboottest.websocket.cfg.Config
 *
 * @author: wanglei62
 * @DATE: 2021/3/26 15:53
 **/
@Configuration
public class Config {
    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }
}
