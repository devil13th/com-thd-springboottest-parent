package com.thd.springboottest.task.service;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.task.service.AlarmTask
 *
 * @author: wanglei62
 * @DATE: 2020/2/14 14:50
 **/
@Component
@EnableScheduling
public class AlarmTask {
    /**每隔5秒钟执行一次*/
    @Scheduled(cron = "0/5 * * * * *")
    public void run() throws InterruptedException {
        //Thread.sleep(6000);
        System.out.println(Thread.currentThread().getName()+"=====>>>>>使用cron  {}"+(System.currentTimeMillis()/1000));
    }


    /**fixedRate:上一次开始执行时间点之后5秒再执行*/
    @Scheduled(fixedRate = 5000)
    public void run1() throws InterruptedException {
        Thread.sleep(6000);
        System.out.println(Thread.currentThread().getName()+"=====>>>>>使用fixedRate  {}"+(System.currentTimeMillis()/1000));
    }


    /**fixedDelay:上一次执行完毕时间点之后5秒再执行*/
    @Scheduled(fixedDelay = 5000)
    public void run2() throws InterruptedException {
        Thread.sleep(7000);
        System.out.println(Thread.currentThread().getName()+"=====>>>>>使用fixedDelay  {}"+(System.currentTimeMillis()/1000));
    }


}
