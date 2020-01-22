package com.thd.springboottest;

import com.thd.springboottest.springbootevent.customevent.MyEvent;
import com.thd.springboottest.springbootevent.customevent.MyListener;
import com.thd.springboottest.springbootevent.event.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;


@SpringBootApplication(scanBasePackages = "com.thd.springboottest")
public class Application extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }



    public static void main(String[] args) {
//        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
//        String[] names = ctx.getBeanDefinitionNames();

        // 添加springboot事件的监听
        SpringApplication springApplication = new SpringApplication(Application.class);
        springApplication.addListeners(new ApplicationPreparedEventListener());
        springApplication.addListeners(new ApplicationEnvironmentPreparedEventListener());
        springApplication.addListeners(new ApplicationReadyEventListener());
        springApplication.addListeners(new ApplicationStartedEventListener());
        springApplication.addListeners(new ApplicationStartingEventListener());


        ConfigurableApplicationContext ctx = springApplication.run( args);
        System.out.println("-----------------");

        // 注册事件的监听
        ctx.addApplicationListener(new MyListener());
        //发布事件
        ctx.publishEvent(new MyEvent("测试事件.","reciver"," send msg "));

        String[] names = ctx.getBeanDefinitionNames();


    }


}
