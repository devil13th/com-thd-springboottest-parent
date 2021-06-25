package com.thd.springboottest.ioc.registbean;

import com.thd.springboottest.ioc.bean.BeanForAnnotationBean;
import com.thd.springboottest.ioc.bean.BeanForAnnotationComponent;
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



    @Bean
    public BeanForAnnotationBean beanForAnnotationBeanTwo(BeanForAnnotationComponent u){
        BeanForAnnotationBean hw = new BeanForAnnotationBean();
        hw.setUser(u);
        hw.setMessage(message);
        return hw;
    }
}
