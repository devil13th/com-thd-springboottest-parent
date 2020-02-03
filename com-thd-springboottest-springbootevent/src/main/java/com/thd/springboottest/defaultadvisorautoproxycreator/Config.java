package com.thd.springboottest.defaultadvisorautoproxycreator;

import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.aop.support.NameMatchMethodPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * com.thd.springboottest.defaultadvisorautoproxycreator.Configuration
 * User: devil13th
 * Date: 2020/1/23
 * Time: 23:56
 * Description: No Description
 */
@Configuration
public class Config {

    @Autowired
    private MyMethodInterceptor myMethodInterceptor;
    @Bean
    // 织入过程  凡是pri开头的方法 都会被增强,增强内容是myMethodInterceptor
    public NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor(){
        NameMatchMethodPointcutAdvisor nameMatchMethodPointcutAdvisor=new NameMatchMethodPointcutAdvisor();
        nameMatchMethodPointcutAdvisor.setMappedName("pri*");
        nameMatchMethodPointcutAdvisor.setAdvice(myMethodInterceptor);
        return nameMatchMethodPointcutAdvisor;
    }
    @Bean
    public DefaultAdvisorAutoProxyCreator defaultAdvisorAutoProxyCreator(){
        return new DefaultAdvisorAutoProxyCreator();
    }

}
