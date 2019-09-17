package com.thd.springboottest.optimize.service;

import com.thd.springboottest.optimize.dto.MyBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.Console;
import java.util.concurrent.TimeUnit;

/**
 * @author devil13th
 **/
@Service
public class MyServiceImpl implements  MyService {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Override
    public void hello() {
        log.info(" void hello() ");
    }

    @Override
    public String hello(String name) {
        log.info(" String hello(String name) ");
        return "hello " + name ;
    }

    @Override
    public String hello(String name, String welcome) {

        log.info(" String hello(String name, String welcome) ");
        return "hello " + name + " : " + welcome;
    }

    @Override
    public MyBean helloSb(String name, String welcome) {
        log.info(" MyBean helloSb(String name, String welcome) ");
        MyBean mb = new MyBean(name,welcome);
        return mb;
    }


    @Override
    public void stackOver(int i) {
        byte[] b = new byte[1024 * 1024];
        try {
            TimeUnit.MILLISECONDS.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + i);
        i++;
        this.stackOver(i);
    }
}
