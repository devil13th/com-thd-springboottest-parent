package com.thd.springboottest;

import com.thd.springboottest.redis.cfg.CfgBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;
import org.springframework.transaction.annotation.EnableTransactionManagement;


@SpringBootApplication(scanBasePackages = "com.thd.springboottest")
@Import(CfgBean.class)
@EnableTransactionManagement
public class Application extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }



    public static void main(String[] args) {
        //System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] names = ctx.getBeanDefinitionNames();
    }


}
