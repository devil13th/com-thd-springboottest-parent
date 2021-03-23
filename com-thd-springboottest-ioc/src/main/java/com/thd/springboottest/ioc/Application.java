package com.thd.springboottest.ioc;

import com.thd.springboottest.ioc.bean.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.stream.Stream;


@SpringBootApplication(scanBasePackages = "com.thd.springboottest")
@Import(value={MyImportSelector.class, BeanForAnnotationImport.class})

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

        // 通过@Component注册的bean -- 参见 BeanForAnnotationComponent.java
        BeanForAnnotationComponent u = (BeanForAnnotationComponent)ctx.getBean("beanForAnnotationComponent");
        System.out.println(" ======= 通过@Component注册 =======");
        System.out.println(u);

        // 通过@Bean注册的bean  -- 参见 MyConfig.java
        BeanForAnnotationBean hw = (BeanForAnnotationBean)ctx.getBean("beanForAnnotationBean");
        System.out.println(" ======= 通过@Bean注册 =======");
        System.out.println(hw.getUser());
        System.out.println(hw.getMessage());

        // 通过FactoryBean注册的bean  -- 参见MyFactoryBean.java
        BeanForFactoryBean mfb = (BeanForFactoryBean)ctx.getBean("myFactoryBean");
        System.out.println(" ======= 通过FactoryBean注册 =======");
        System.out.println(mfb);

        // 通过Register注册的bean -- 参见 MyBeanRegister.java
        BeanForBeanRegister mfbr =  (BeanForBeanRegister)ctx.getBean("beanForBeanRegister");
        System.out.println(" ======= 通过BeanRegister注册 =======");
        System.out.println(mfbr);




        // 通过Import注释注册的bean -- 参见 BeanForAnnotationImport.java
        BeanForAnnotationImport bfai =  (BeanForAnnotationImport)ctx.getBean("com.thd.springboottest.ioc.bean.BeanForAnnotationImport");
        System.out.println(" ======= 通过Import注册的bean =======");
        System.out.println(bfai);

        // 通过ImportSelector注册的bean -- 参见 BeanForImportSelector.java
        BeanForImportSelector bfis =  (BeanForImportSelector)ctx.getBean("com.thd.springboottest.ioc.bean.BeanForImportSelector");
        System.out.println(" ======= 通过ImportSelector注册的bean =======");
        System.out.println(bfis);
    }


}
