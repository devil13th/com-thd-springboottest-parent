package com.thd.springboottest.springbootevent.event;

import org.springframework.boot.context.event.ApplicationContextInitializedEvent;
import org.springframework.context.ApplicationListener;

/**
 * com.thd.springboottest.springbootevent.event.ApplicationContextInitializedEventListener
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 15:51
 **/
public class ApplicationContextInitializedEventListener implements ApplicationListener<ApplicationContextInitializedEvent> {
    @Override
    public void onApplicationEvent(ApplicationContextInitializedEvent applicationContextInitializedEvent) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("applicationContextInitializedEvent");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
