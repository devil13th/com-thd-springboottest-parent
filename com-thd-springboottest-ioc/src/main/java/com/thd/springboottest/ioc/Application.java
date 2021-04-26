package com.thd.springboottest.ioc;

import com.thd.springboottest.ioc.bean.*;
import com.thd.springboottest.ioc.beanpostprocessor.MyInterface;
import com.thd.springboottest.ioc.registbean.MyImportBeanDefinitionRegistrar;
import com.thd.springboottest.ioc.registbean.MyImportSelector;
import com.thd.springboottest.ioc.staticmethodioc.SpringBeanUtil;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Import;

import java.util.stream.Stream;


@SpringBootApplication(scanBasePackages = "com.thd.springboottest")
@Import(value={MyImportSelector.class, BeanForAnnotationImport.class, MyImportBeanDefinitionRegistrar.class})

public class Application  {

//    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
//        return builder.sources(this.getClass());
//    }



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




        // 通过Import注释注册的bean -- 参见 BeanForAnnotationImport.java  Application.java(@Import注解)
        BeanForAnnotationImport bfai =  (BeanForAnnotationImport)ctx.getBean("com.thd.springboottest.ioc.bean.BeanForAnnotationImport");
        System.out.println(" ======= 通过Import注册的bean =======");
        System.out.println(bfai);

        // 通过ImportSelector注册的bean -- 参见 BeanForImportSelector.java  Application.java(@Import注解)
        BeanForImportSelector bfis =  (BeanForImportSelector)ctx.getBean("com.thd.springboottest.ioc.bean.BeanForImportSelector");
        System.out.println(" ======= 通过ImportSelector注册的bean =======");
        System.out.println(bfis);

        // 通过ImportBeanDefinitionRegistrar 注册的bean  -- 参见MyImportBeanDefinitionRegistrar.java  Application.java(@Import注解)
        BeanForImportBeanDefinitionRegistrar bfibdr = (BeanForImportBeanDefinitionRegistrar)ctx.getBean("com.thd.springboottest.ioc.bean.BeanForImportBeanDefinitionRegistrar");
        System.out.println(" ======= 通过ImportBeanDefinitionRegistrar 注册的bean ======= ");
        System.out.println(bfibdr);


        // 注册bean过程中替换已有bean -- 参见 MyBeanPostProcess.java  将已注册的bean进行增强
        MyInterface echoBean = (MyInterface) ctx.getBean("myBean");
        System.out.println(" ======= 注册bean过程中替换已有bean ======= ");
        String str = echoBean.echoSomething();
        System.out.println(str);


        // 静态方法注入bean -- 参见 StaticClassMethod.java
        System.out.println(" ======= 静态方法注入bean ======= ");
        System.out.println(SpringBeanUtil.getBean("beanForStaticClassMethod"));
    }


}
