package com.thd.springboottest.beanpostprocess;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * com.thd.springboottest.bean.MyBean
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 16:16
 **/
@Component
@MyAnnotation
public class MyBean implements InitializingBean, DisposableBean {
    public void init(){
        System.out.println(" MyBean init 方法执行 ");
    }

    public void destory(){
        System.out.println(" MyBean destory 方法执行 ");
    }
    @PostConstruct
    public void postConstruct(){
        System.out.println(" MyBean  postConstruct 方法执行 ");
    }
    @PreDestroy
    public void preDestroy(){
        System.out.println(" MyBean preDestroy 方法执行 ");
    }
    //实现InitializingBean接口
    public void afterPropertiesSet() throws Exception {
        System.out.println(" MyBean afterPropertiesSet 方法执行 ");
    }
    //实现DisposableBean接口
    public void destroy() throws Exception {
        System.out.println(" MyBean destroy 方法执行 ");
    }
}
