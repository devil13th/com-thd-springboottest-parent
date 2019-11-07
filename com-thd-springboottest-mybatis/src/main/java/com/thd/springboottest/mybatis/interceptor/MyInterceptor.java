package com.thd.springboottest.mybatis.interceptor;

import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.util.Properties;

/**
 * com.thd.springboottest.mybatis.interceptor.MyInterceptor
 *
 * @author: wanglei62
 * @DATE: 2019/10/25 9:08
 **/
@Intercepts({@Signature(
                type = Executor.class,
                method = "query",
                args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class}
                )
})
public class MyInterceptor implements Interceptor {
    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("-------------- intercept start ---------------------");
        Object result = invocation.proceed();
        System.out.println("Invocation.proceed()");
        System.out.println("-------------- intercept end ---------------------");
        return result;
    }

    @Override
    public Object plugin(Object o) {
        System.out.println("-------------- plugin ---------------------");
        return Plugin.wrap(o, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("-------------- setProperties ---------------------");
        String prop1 = properties.getProperty("prop1");
        String prop2 = properties.getProperty("prop2");
        System.out.println(prop1 + "------" + prop2);
    }
}
