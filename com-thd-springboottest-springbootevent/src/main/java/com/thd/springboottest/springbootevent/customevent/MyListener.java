package com.thd.springboottest.springbootevent.customevent;

import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.springbootevent.customevent.MyListener
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 13:39
 **/
@Component
public class MyListener implements ApplicationListener<MyEvent> {
    @Override
    public void onApplicationEvent(MyEvent myEvent) {
        myEvent.output();
        System.out.println(myEvent.receiver + "received msg : " + myEvent.content );
    }
}
