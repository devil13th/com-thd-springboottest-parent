package com.thd.springboottest.ioc.bean;

/**
 * com.thd.springboottest.ioc.bean.BeanForBeanRegister
 *
 * @author: wanglei62
 * @DATE: 2021/3/22 14:22
 **/
public class BeanForBeanRegister {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return "BeanForBeanRegister{" +
                "message='" + message + '\'' +
                '}';
    }
}
