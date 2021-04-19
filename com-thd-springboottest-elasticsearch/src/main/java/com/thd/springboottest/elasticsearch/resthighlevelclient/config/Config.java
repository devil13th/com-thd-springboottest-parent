package com.thd.springboottest.elasticsearch.resthighlevelclient.config;

import com.thd.springboottest.elasticsearch.resthighlevelclient.util.EsUtils;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.config.Config
 *
 * @author: wanglei62
 * @DATE: 2021/4/19 17:27
 **/
@Configuration
public class Config {
    @Bean
    public RestHighLevelClient esClient(){
        return EsUtils.getEsClient();
    }
}
