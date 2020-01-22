package com.thd.springboottest.springbootevent.event;

import org.springframework.boot.context.event.ApplicationStartingEvent;
import org.springframework.context.ApplicationListener;

/**
 * com.thd.springboottest.springbootevent.event.ApplicationStartingEventListener
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 14:35
 **/
public class ApplicationStartingEventListener implements ApplicationListener<ApplicationStartingEvent> {
    @Override
    public void onApplicationEvent(ApplicationStartingEvent applicationStartingEvent) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("applicationStartingEvent");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
