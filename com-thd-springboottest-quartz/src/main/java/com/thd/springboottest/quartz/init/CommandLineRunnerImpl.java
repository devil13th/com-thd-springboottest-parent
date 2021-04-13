package com.thd.springboottest.quartz.init;

import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * com.thd.springbbottest.quartz.init.CommandLineRunnerImpl
 *
 * @author: wanglei62
 * @DATE: 2020/10/22 9:29
 **/
@Component
@Order(value=1)
public class CommandLineRunnerImpl implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        System.out.println("通过实现CommandLineRunner接口，在spring boot项目启动后打印参数");
        for (String arg : args) {
            System.out.print(arg + " ");
        }
        System.out.println();
    }
}
