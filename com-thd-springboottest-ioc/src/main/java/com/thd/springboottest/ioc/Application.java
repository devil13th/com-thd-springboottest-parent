package com.thd.springboottest.ioc;

import com.thd.springboottest.ioc.bean.BeanForAnnotationBean;
import com.thd.springboottest.ioc.bean.BeanForBeanRegister;
import com.thd.springboottest.ioc.bean.BeanForComponent;
import com.thd.springboottest.ioc.bean.BeanForFactoryBean;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.stream.Stream;


@SpringBootApplication(scanBasePackages = "com.thd.springboottest")
public class Application extends SpringBootServletInitializer {

    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(this.getClass());
    }



    public static void main(String[] args) {
        //System.setProperty("spring.devtools.restart.enabled", "false");
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);


        String[] names = ctx.getBeanDefinitionNames();
        System.out.println("========Regist Beans:======");
        Stream.of(names).forEach(System.out::println);
        System.out.println("===========================");

        // 通过@Bean注册的bean  -- 参见 MyConfig.java
        BeanForAnnotationBean hw = (BeanForAnnotationBean)ctx.getBean("beanForAnnotationBean");
        System.out.println(hw.getUser());
        System.out.println(hw.getMessage());

        // 通过FactoryBean注册的bean  -- 参见MyFactoryBean.java
        BeanForFactoryBean mfb = (BeanForFactoryBean)ctx.getBean("myFactoryBean");
        System.out.println(mfb);

        // 通过Register注册的bean -- 参见 MyBeanRegister.java
        BeanForBeanRegister mfbr =  (BeanForBeanRegister)ctx.getBean("beanForBeanRegister");
        System.out.println(mfbr);

        // 通过@Component注册的bean -- 参见 BeanForComponent.java
        BeanForComponent u = (BeanForComponent)ctx.getBean("beanForComponent");
        System.out.println(u);

    }


}
