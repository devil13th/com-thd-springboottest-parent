package com.thd.springboottest.ioc.registbean;

import com.thd.springboottest.ioc.bean.BeanForImportBeanDefinitionRegistrar;
import org.springframework.beans.BeansException;
import org.springframework.beans.MutablePropertyValues;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotationMetadata;

/**
 * com.thd.springboottest.ioc.bean.MyImportBeanDefinitionRegistrar
 *
 * @author: wanglei62
 * @DATE: 2021/3/23 10:58
 **/
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar , EnvironmentAware, ApplicationContextAware {

    private Environment environment;
    private ApplicationContext applicationContext;
    /**
     * AnnotationMetadata：当前类的注解信息
     * BeanDefinitionRegistry:BeanDefinition注册类；
     * 		把所有需要添加到容器中的bean；调用
     * 		BeanDefinitionRegistry.registerBeanDefinition手工注册进来
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {

        // 例子：如果ioc容器中有BeanForAnnotationBean的注册才会注册BeanForImportBeanDefinitionRegistrar 这个bean
        boolean definition = registry.containsBeanDefinition("beanForAnnotationBean");
        if (definition) {
            // 指定Bean定义信息；（Bean的类型，Bean。。。）
            RootBeanDefinition beanDefinition = new RootBeanDefinition(BeanForImportBeanDefinitionRegistrar.class);
            MutablePropertyValues mpv = beanDefinition.getPropertyValues();
            mpv.addPropertyValue("message",environment.getProperty("message"));
            // 注册一个Bean，指定bean名
            registry.registerBeanDefinition(BeanForImportBeanDefinitionRegistrar.class.getName(), beanDefinition);
        }
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
