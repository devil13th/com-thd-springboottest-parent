package com.thd.springboottest.ioc.bean;

/**
 * com.thd.springboottest.ioc.bean.HelloWorld
 *
 * @author: wanglei62
 * @DATE: 2021/3/22 13:54
 **/
public class BeanForAnnotationBean {
    private BeanForComponent user;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public BeanForComponent getUser() {
        return user;
    }

    public void setUser(BeanForComponent user) {
        this.user = user;
    }
}
