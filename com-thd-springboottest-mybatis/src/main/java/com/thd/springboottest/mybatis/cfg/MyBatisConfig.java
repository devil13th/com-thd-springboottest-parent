package com.thd.springboottest.mybatis.cfg;

import com.thd.springboottest.mybatis.interceptor.MyInterceptor;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.thd.springboottest.mybatis.cfg.MyBatisConfig
 *
 * @author: wanglei62
 * @DATE: 2019/10/25 9:19
 **/
@Configuration
public class MyBatisConfig {
    @Bean
    ConfigurationCustomizer mybatisConfigurationCustomizer() {
        return new ConfigurationCustomizer() {
            @Override
            public void customize(org.apache.ibatis.session.Configuration configuration) {
                configuration.addInterceptor(new MyInterceptor());
            }
        };
    }
}
