package com.thd.springboottest.beanpostprocess;

import com.thd.springboottest.annotation.MyAnnotation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.bean.SysUser
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 16:12
 **/
@Component
public class MyBeanPostProcess implements BeanPostProcessor {
    public Object postProcessBeforeInitialization(Object bean, String beanName)
            throws BeansException {
        //System.out.println( beanName + " postProcessBeforeInitialization 方法执行 -> " + bean);

        MyAnnotation[] m = bean.getClass().getAnnotationsByType(MyAnnotation.class);
        if(m!= null && m.length > 0){
            System.out.println(beanName + " 带有注释 MyAnnotation") ;
        }
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName)
            throws BeansException {
        //System.out.println( beanName + " postProcessAfterInitialization 方法执行 -> " + bean);

        return bean;
    }
}
