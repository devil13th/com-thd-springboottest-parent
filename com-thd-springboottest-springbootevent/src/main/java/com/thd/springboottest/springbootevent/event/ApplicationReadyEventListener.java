package com.thd.springboottest.springbootevent.event;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.annotation.Bean;

import org.springframework.core.env.ConfigurableEnvironment;

import org.springframework.core.env.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Iterator;

/**
 * com.thd.springboottest.springbootevent.event.ApplicationReadyEventListener
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 14:32
 **/
public class ApplicationReadyEventListener implements ApplicationListener<ApplicationReadyEvent> {
    @Override
    public void onApplicationEvent(ApplicationReadyEvent applicationReadyEvent) {

//        System.out.println("--------------------- beans ----------------------");
//        // 获取已加载的带有@Component注释的类
//        String[] beans = applicationReadyEvent.getApplicationContext().getBeanNamesForAnnotation(Component.class);
//        for (int i = 0; i < beans.length; i++) {
//            System.out.println(beans[i]);
//        }
//        System.out.println("-------------------------------------------");
//
//
//        System.out.println("--------------------- profiles ----------------------");
//        ConfigurableEnvironment env = applicationReadyEvent.getApplicationContext().getEnvironment();
//        System.out.println(env.getProperty("server.port"));
//        System.out.println("--------------------- propertySource ----------------------");
//        Iterator iter = env.getPropertySources().iterator();
//        while(iter.hasNext()){
//            PropertySource propertySource = (PropertySource)iter.next();
//            System.out.println(propertySource.getName());
//        }
//        System.out.println("--------------------- Active Profiles ----------------------");
//        String[] profiles = env.getActiveProfiles();
//        for (int i = 0; i < profiles.length; i++) {
//            System.out.println(profiles[i]);
//        }
//        System.out.println("-------------------------------------------");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
        System.out.println("applicationReadyEvent");
        System.out.println("++++++++++++++++++++++++++++++++++++++++++++++++++++");
    }
}
