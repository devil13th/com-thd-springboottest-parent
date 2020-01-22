package com.thd.springboottest.springbootevent.customevent;

import org.springframework.context.ApplicationEvent;

/**
 * com.thd.springboottest.springbootevent.customevent.MyEvent
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 13:35
 **/
public class MyEvent extends ApplicationEvent {

    // 收件人
    public String receiver;

    // 收件内容
    public String content;

    public MyEvent(Object source) {
        super(source);
    }

    public MyEvent(Object source, String receiver, String content) {
        super(source);
        this.receiver = receiver;
        this.content = content;
    }
    public void output(){
        System.out.println("I had been sand a msg to " + this.receiver);
    }

}
