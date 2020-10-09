package com.thd.springboottest;

import com.thd.springboottest.annotation.annotation.MyAnnotation;
import com.thd.springboottest.annotation.entity.Person;
import com.thd.springboottest.annotation.ipt.MyBeanRegister;
import com.thd.springboottest.annotation.ipt.MyImportBeanA;
import com.thd.springboottest.annotation.ipt.MyImportBeanB;
import com.thd.springboottest.annotation.ipt.MyImportSelector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

import java.util.Arrays;


//@SpringBootApplication(scanBasePackages = "com.thd.springboottest")
@MyAnnotation
@Import({MyImportBeanA.class, MyImportBeanB.class,MyImportSelector.class, MyBeanRegister.class})
public class Application extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }



    public static void main(String[] args) {
        //System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        String[] names = ctx.getBeanDefinitionNames();
        Arrays.stream(names).forEach(System.out::println);
    }


    @Bean(name="myPerson")
    public Person person(){
        return new Person();
    }


}
