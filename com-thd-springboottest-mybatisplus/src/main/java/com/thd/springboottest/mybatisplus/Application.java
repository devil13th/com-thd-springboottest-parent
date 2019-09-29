package com.thd.springboottest.mybatisplus;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

/**
 * com.thd.springboottest.mybatisplus.Application
 *
 * @author: wanglei62
 * @DATE: 2019/9/20 16:08
 **/
@SpringBootApplication(scanBasePackages="com.thd.springboottest.mybatisplus.*")
@MapperScan("com.thd.springboottest.mybatisplus.mapper")
public class Application {
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
    }
}
