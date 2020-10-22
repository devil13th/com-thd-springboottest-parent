package com.thd.springboottest.quartz.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * com.thd.springbbottest.quartz.job.TestJob
 *
 * @author: wanglei62
 * @DATE: 2020/10/22 9:16
 **/
public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        System.err.println(jobExecutionContext.getJobDetail().getJobDataMap().get("name"));
        System.err.println(jobExecutionContext.getJobDetail().getJobDataMap().get("age"));
        System.err.println(jobExecutionContext.getTrigger().getJobDataMap().get("orderNo"));

        LocalDateTime ldt = LocalDateTime.now();
        System.err.println("定时任务执行，当前时间："+ ldt);
    }
}
