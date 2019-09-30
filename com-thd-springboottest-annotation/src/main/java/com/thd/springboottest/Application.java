package com.thd.springboottest;

import com.thd.springboottest.annotation.annotation.MyAnnotation;
import com.thd.springboottest.annotation.entity.Person;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;


//@SpringBootApplication(scanBasePackages = "com.thd.springboottest")
@MyAnnotation
public class Application extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }



    public static void main(String[] args) {
        //System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] names = ctx.getBeanDefinitionNames();
    }


    @Bean(name="myPerson")
    public Person person(){
        return new Person();
    }


}
