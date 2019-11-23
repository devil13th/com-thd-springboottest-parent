package com.thd.springboottest.standardcode.configuration;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;

import javax.sql.DataSource;

/**
 * com.thd.springboottest.standardcode.configuration.DruidConfiguration
 *
 * @author: wanglei62
 * @DATE: 2019/11/22 9:47
 **/
public class DruidConfiguration {
    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource druid(){
        return new DruidDataSource();
    }
}
