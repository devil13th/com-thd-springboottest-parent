package com.thd.springboottest.mybatis.interceptor;

import com.alibaba.fastjson.JSONObject;
import org.apache.ibatis.cache.CacheKey;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Properties;

/**
 * com.thd.springboottest.mybatis.interceptor.MyInterceptor
 *
 * @author: wanglei62
 * @DATE: 2019/10/25 9:08
 **/
@Intercepts({
        @Signature(type = Executor.class,method = "query",args = {MappedStatement.class,Object.class, RowBounds.class, ResultHandler.class}),
        @Signature(type = Executor.class, method = "query", args = {MappedStatement.class, Object.class,RowBounds.class, ResultHandler.class, CacheKey.class, BoundSql.class}),
        @Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class}),
        @Signature(type = Executor.class, method = "createCacheKey", args = {MappedStatement.class, Object.class, RowBounds.class, BoundSql.class})
})
public class MyInterceptor implements Interceptor {

    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public Object intercept(Invocation invocation) throws Throwable {

        System.out.println("-------------- intercept start ---------------------");

        logger.info(" 拦截目标： " +  invocation.getTarget());
        logger.info(" 拦截方法： " + invocation.getMethod().getName());
        logger.info(" 方法参数：" );
        Arrays.asList(invocation.getArgs()).stream().forEach(arg -> {logger.info(arg == null ? "null":arg.toString());});



        System.out.println("-----------------------------------");



        MappedStatement ms = (MappedStatement) invocation.getArgs()[0];
        Object parameter = invocation.getArgs()[1];
        BoundSql boundSql = ms.getBoundSql(parameter);
        logger.info("SQL语句：" + boundSql.getSql());
        logger.info("SQL参数：" + parameter);
        SqlCommandType commandType = ms.getSqlCommandType();
        logger.info("SQL类型：" + commandType);
        // 2. 获取当前正在被操作的类, 有可能是Java Bean, 也可能是普通的操作对象, 比如普通的参数传递
        // 普通参数, 即是 @Param 包装或者原始 Map 对象, 普通参数会被 Mybatis 包装成 Map 对象
        // 即是 org.apache.ibatis.binding.MapperMethod$ParamMap

        // 获取拦截器指定的方法类型, 通常需要拦截 update
        String methodName = invocation.getMethod().getName();
        logger.info("MyInterceptor, methodName; {}, commandType: {}", methodName, commandType);

        Object result = invocation.proceed();
        System.out.println("Invocation.proceed()");
        System.out.println("-------------- intercept end ---------------------");
        return result;
    }

    @Override
    public Object plugin(Object target) {
        System.out.println("-------------- plugin ---------------------");
        return Plugin.wrap(target, this);
    }

    @Override
    public void setProperties(Properties properties) {
        System.out.println("-------------- setProperties ---------------------");
        String prop1 = properties.getProperty("prop1");
        String prop2 = properties.getProperty("prop2");
        System.out.println(prop1 + "------" + prop2);
    }
}
