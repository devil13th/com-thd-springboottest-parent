package com.thd.springboottest.ioc.bean;

/**
 * com.thd.springboottest.ioc.bean.Message
 *
 * @author: wanglei62
 * @DATE: 2021/3/22 12:09
 **/
public class BeanForFactoryBean {
    private String message ;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void print() {
        System.out.println("消息是: " + message);
    }

    @Override
    public String toString() {
        return "Message{" +
                "message='" + message + '\'' +
                '}';
    }
}
