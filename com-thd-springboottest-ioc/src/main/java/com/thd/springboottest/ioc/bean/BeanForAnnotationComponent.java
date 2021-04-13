package com.thd.springboottest.ioc.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.ioc.bean.User
 *
 * @author: wanglei62
 * @DATE: 2021/3/22 12:02
 **/

// 通过注解@Component来注册一个bean
@Component
public class BeanForAnnotationComponent {
    @Value("${user.nickname}")
    private String name;
    @Value("${user.age}")
    private Integer age = 5;
    @Value("${user.xxx:hello}")
    private String message;
    public BeanForAnnotationComponent(){}

    @Override
    public String toString() {
        return "BeanForComponent{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", message='" + message + '\'' +
                '}';
    }
}
