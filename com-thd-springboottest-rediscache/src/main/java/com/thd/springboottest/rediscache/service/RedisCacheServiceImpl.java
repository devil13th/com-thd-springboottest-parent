package com.thd.springboottest.rediscache.service;

import com.thd.springboottest.rediscache.bean.TestBean;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author devil13th
 **/

@Service
public class RedisCacheServiceImpl implements RedisCacheService {
    static public Map<String, TestBean> list = new HashMap<String, TestBean>();
    @Override
    /**
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     */
    @CachePut(value = "person", key = "#root.targetClass + #result.id", unless = "#redisCacheBean eq null")
    public TestBean addBean(TestBean redisCacheBean) {
        if(RedisCacheServiceImpl.list.containsKey(redisCacheBean.getId())){
            return null;
        }
        RedisCacheServiceImpl.list.put(redisCacheBean.getId(),redisCacheBean);
        return redisCacheBean;
    }

    @Override
    /**
     * @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
     */
    @CacheEvict(value = "person", key = "#root.targetClass + #id", condition = "#result eq null")
    public TestBean removeBean(String id) {
        TestBean redisCacheBean = RedisCacheServiceImpl.list.get(id);
        RedisCacheServiceImpl.list.remove(id);
        return redisCacheBean;
    }

    @Override
    /**
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     */
    @CachePut(value = "person", key = "#root.targetClass + #p.id", unless = "#p eq null")
    public TestBean modiBean(TestBean redisCacheBean) {
        RedisCacheServiceImpl.list.put(redisCacheBean.getId(),redisCacheBean);
        return redisCacheBean;
    }

    @Override
    //@Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有则执行方法，然后把方法执行的结果添加到缓存中
    //unless 表示条件表达式成立的话不放入缓存
    @Cacheable(value = "person", key = "#root.targetClass + #id", unless = "#result eq null")
    public TestBean queryBean(String id) {
        System.out.println("查询id为" + id + " 没有走缓存");
        return RedisCacheServiceImpl.list.get(id);
    }



    @Override
    /**
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把返回的数据放入缓存
     */
    @CachePut(value = "personList", key = "'personList'", unless = "#redisCacheBean eq null")
    public Map<String,TestBean> addBeanAndRefreshCache(TestBean redisCacheBean) {
        if(!RedisCacheServiceImpl.list.containsKey(redisCacheBean.getId())){
            RedisCacheServiceImpl.list.put(redisCacheBean.getId(),redisCacheBean);
        }
        return RedisCacheServiceImpl.list;
    }

    @Override
    /**
     * @CacheEvict 应用到删除数据的方法上，调用方法时会从缓存中删除对应key的数据
     */
    @CachePut(value = "personList", key = "'personList'",  unless = "#id eq null")
    public Map<String,TestBean> removeBeanAndRefreshCache(String id) {
        TestBean redisCacheBean = RedisCacheServiceImpl.list.get(id);
        if(redisCacheBean != null){
            RedisCacheServiceImpl.list.remove(id);
        }
        return RedisCacheServiceImpl.list;
    }

    @Override
    /**
     * @CachePut 应用到写数据的方法上，如新增/修改方法，调用方法时会自动把相应的数据放入缓存
     */
    @CachePut(value = "personList", key = "'personList'",unless = "#redisCacheBean eq null")
    public Map<String,TestBean> modiBeanAndRefreshCache(TestBean redisCacheBean) {
        TestBean redisCacheBeanSource = RedisCacheServiceImpl.list.get(redisCacheBean.getId());
        if(redisCacheBeanSource != null){
            RedisCacheServiceImpl.list.put(redisCacheBean.getId(),redisCacheBean);
        }
        return RedisCacheServiceImpl.list;
    }

    @Override
    //@Cacheable 应用到读取数据的方法上，先从缓存中读取，如果没有则执行方法，然后把方法执行的结果添加到缓存中
    //unless 表示条件表达式成立的话不放入缓存
    @Cacheable(value = "personList", key = "'personList'",  unless = "#result eq null")
    public Map<String,TestBean> queryBeanForCache() {
        System.out.println("查询 没有走缓存");
        return RedisCacheServiceImpl.list;
    }
}
