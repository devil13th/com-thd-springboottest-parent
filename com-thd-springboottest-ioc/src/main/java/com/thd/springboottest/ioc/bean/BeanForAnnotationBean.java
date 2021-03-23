package com.thd.springboottest.ioc.bean;

/**
 * com.thd.springboottest.ioc.bean.HelloWorld
 *
 * @author: wanglei62
 * @DATE: 2021/3/22 13:54
 **/
public class BeanForAnnotationBean {
    private BeanForAnnotationComponent user;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BeanForAnnotationComponent getUser() {
        return user;
    }

    public void setUser(BeanForAnnotationComponent user) {
        this.user = user;
    }
}
