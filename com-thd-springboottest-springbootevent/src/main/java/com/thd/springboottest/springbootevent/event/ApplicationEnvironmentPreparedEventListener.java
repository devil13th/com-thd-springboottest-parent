package com.thd.springboottest.springbootevent.event;

import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.boot.context.event.ApplicationPreparedEvent;
import org.springframework.context.ApplicationListener;

/**
 * com.thd.springboottest.springbootevent.event.ApplicationEnvironmentPreparedEventListener
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 14:30
 **/
public class ApplicationEnvironmentPreparedEventListener implements ApplicationListener<ApplicationEnvironmentPreparedEvent> {
    @Override
    public void onApplicationEvent(ApplicationEnvironmentPreparedEvent applicationEnvironmentPreparedEvent) {
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("applicationEnvironmentPreparedEvent");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
