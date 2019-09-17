package com.thd.springboottest.rediscache.service;

import com.thd.springboottest.rediscache.bean.TestBean;

import java.util.Map;

/**
 * @author devil13th
 **/
public interface RedisCacheService {
    public TestBean addBean(TestBean redisCacheBean);
    public TestBean removeBean(String id);
    public TestBean modiBean(TestBean redisCacheBean);
    public TestBean queryBean(String id);

    public Map<String,TestBean> addBeanAndRefreshCache(TestBean redisCacheBean);
    public Map<String,TestBean> removeBeanAndRefreshCache(String id);
    public Map<String,TestBean> modiBeanAndRefreshCache(TestBean redisCacheBean);
    public Map<String,TestBean> queryBeanForCache();

}
