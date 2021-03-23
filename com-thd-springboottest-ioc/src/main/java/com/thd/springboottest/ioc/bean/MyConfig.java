package com.thd.springboottest.ioc.bean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.thd.springboottest.ioc.bean.MyConfig
 *
 * @author: wanglei62
 * @DATE: 2021/3/22 13:53
 **/
@Configuration
public class MyConfig {
    @Value("${message}")
    private String message;

    @Autowired
    private BeanForAnnotationComponent user;

    @Bean
    public BeanForAnnotationBean beanForAnnotationBean(){
        BeanForAnnotationBean hw = new BeanForAnnotationBean();
        hw.setUser(user);
        hw.setMessage(message);
        return hw;
    }
}
