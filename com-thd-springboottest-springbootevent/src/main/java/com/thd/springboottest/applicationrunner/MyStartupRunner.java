package com.thd.springboottest.applicationrunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.applicationrunner.MyStartupRunner
 *
 * @author: wanglei62
 * @DATE: 2021/3/24 9:51
 **/
@Order(1)
@Component
public class MyStartupRunner implements CommandLineRunner {
    @Autowired
    private StartIocBean iocBean;
    @Override
    public void run(String... args) throws Exception {

        System.out.println("------------- 可以在这里进行一些启动后的额外操作 ------------- ");
        System.out.println(iocBean);
        System.out.println("------------- 可以在这里进行一些启动后的额外操作 ------------- ");
    }
}
