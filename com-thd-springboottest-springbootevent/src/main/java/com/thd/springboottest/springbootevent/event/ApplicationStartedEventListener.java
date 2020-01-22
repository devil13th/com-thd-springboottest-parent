package com.thd.springboottest.springbootevent.event;

import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.ApplicationListener;

/**
 * com.thd.springboottest.springbootevent.event.ApplicationStartedEventListener
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 14:33
 **/
public class ApplicationStartedEventListener implements ApplicationListener<ApplicationStartedEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartedEvent applicationStartedEvent) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("applicationStartedEvent");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
