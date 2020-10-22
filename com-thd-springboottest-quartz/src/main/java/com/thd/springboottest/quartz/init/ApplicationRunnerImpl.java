package com.thd.springboottest.quartz.init;

import com.thd.springboottest.quartz.job.TestJob;
import org.quartz.JobBuilder;
import org.quartz.JobDetail;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * com.thd.springbbottest.quartz.init.ApplicationRunnerImpl
 *
 * @author: wanglei62
 * @DATE: 2020/10/22 9:31
 **/
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {
    @Override
    public void run(ApplicationArguments args) throws Exception {
        System.out.println("通过实现ApplicationRunner接口，在spring boot项目启动后打印参数");
        String[] sourceArgs = args.getSourceArgs();
        for (String arg : sourceArgs) {
            System.out.print(arg + " ");
        }
        System.out.println();




        JobDetail jobDetail = JobBuilder.newJob(TestJob.class)
        /**给当前JobDetail添加参数，K V形式*/
        .usingJobData("name","zy")
        /**给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，可以通过jobExecutionContext.getJobDetail().getJobDataMap().get("age")获取值*/
        .usingJobData("age",23)
        /**添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种*/
        .withIdentity("我是name","我是group")
        .build();//执行

    }
}
