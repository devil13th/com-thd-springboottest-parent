package com.thd.springboottest.ioc.beanpostprocessor;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.bean.SysUser
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 16:12
 **/

/**
 * BeanPostProcessor在spring框架中的功能就是代理真实bean
 */
@Component
public class MyBeanPostProcess implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println( " <<<<<<<<<<<<<<<<< postProcessBeforeInitialization 方法执行 -> " + beanName);

        MyAnnotation[] m = bean.getClass().getAnnotationsByType(MyAnnotation.class);
        if(m!= null && m.length > 0){
            System.out.println(beanName + " 带有注释 MyAnnotation 注释的bean 注册到IOC容器") ;
            MyInterface transferBean = new TransferBean((MyInterface) bean);
            return transferBean;
        }
        // 返回的对象是注册到IOC容器中的真实bean
        // 通常会先生成一个代理对象，然后返回这个代理对象对真实的bean做一些增强


        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        System.out.println(" >>>>>>>>>>>>>>>>>  postProcessAfterInitialization 方法执行 -> " + beanName);

        return bean;
    }
}
