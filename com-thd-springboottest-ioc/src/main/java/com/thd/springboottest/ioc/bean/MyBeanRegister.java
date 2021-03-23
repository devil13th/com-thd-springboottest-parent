package com.thd.springboottest.ioc.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.ioc.bean.MyDefinitionRegistryPostProcessor
 *
 * @author: wanglei62
 * @DATE: 2021/3/22 12:04
 **/
@Component
public class MyBeanRegister implements BeanDefinitionRegistryPostProcessor , EnvironmentAware, ApplicationContextAware {

    // 使用BeanDefinitionRegistryPostProcessor接口不能通过@Value注入application.yml中的属性的,要通过EnvironmentAware接口来完成
    @Value("${message}")
    private String message;

    private Environment environment;
    private ApplicationContext applicationContext;
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        RootBeanDefinition beanDefinition  = new RootBeanDefinition();
        beanDefinition.setBeanClass(BeanForBeanRegister.class);
        MutablePropertyValues mpv = beanDefinition.getPropertyValues();
        // 注入属性 (如果有)
        mpv.addPropertyValue("message",environment.getProperty("message"));

        // 设置 init方法 没有就不用设置
//        beanDefinition.setInitMethodName("init");
        // 设置 destory方法 没有就不用设置
//        beanDefinition.setDestroyMethodName("destory");

        // 注册到ioc容器中
        beanDefinitionRegistry.registerBeanDefinition("beanForBeanRegister",beanDefinition);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("----------------");
    }


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment=environment;
    }
}
