package com.thd.springboottest.defaultadvisorautoproxycreator;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.defaultadvisorautoproxycreator.MyMethodInterceptor
 * User: devil13th
 * Date: 2020/1/23
 * Time: 23:54
 * Description: No Description
 */
@Component
public class MyMethodInterceptor implements MethodInterceptor {
    @Override
    public Object invoke(MethodInvocation methodInvocation) throws Throwable {
        System.out.println(getClass()+"调用方法前");
        Object ret= methodInvocation.proceed();
        System.out.println(getClass()+"调用方法后");
        return ret;

    }
}
