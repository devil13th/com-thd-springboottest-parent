package com.thd.springboottest.quartz.cfg;

import org.quartz.Scheduler;
import org.quartz.SchedulerFactory;
import org.quartz.impl.StdSchedulerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.thd.springbbottest.cfg.QuartzCfg
 *
 * @author: wanglei62
 * @DATE: 2020/10/22 9:22
 **/
@Configuration
public class QuartzCfg {
    @Bean
    public Scheduler scheduler() throws Exception{
        SchedulerFactory schedulerFactory = new StdSchedulerFactory();
        Scheduler scheduler = schedulerFactory.getScheduler();
        return scheduler;
    }
}
