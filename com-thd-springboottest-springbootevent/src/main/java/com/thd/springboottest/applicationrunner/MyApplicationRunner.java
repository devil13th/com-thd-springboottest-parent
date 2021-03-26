package com.thd.springboottest.applicationrunner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.applicationrunner.MyApplicationRunner
 *
 * @author: wanglei62
 * @DATE: 2021/3/24 9:44
 **/
@Component
@Order(2)
public class MyApplicationRunner implements ApplicationRunner {
    @Autowired
    private StartIocBean iocBean;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("------------- 启动后在这里做一些其他事情,例如清除临时文件夹,加载缓存 -------------");
        System.out.println(iocBean);
        System.out.println("------------- 启动后在这里做一些其他事情,例如清除临时文件夹,加载缓存 -------------");
    }


}
