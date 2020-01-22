package com.thd.springboottest.springbootevent.customevent;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.springbootevent.customevent.Publisher
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 13:42
 **/
@Component
public class Publisher {
    @Autowired
    ApplicationContext applicationContext;

    public void publish(Object source, String receiver, String content){
        applicationContext.publishEvent(new MyEvent(source, receiver, content));
    }
}
