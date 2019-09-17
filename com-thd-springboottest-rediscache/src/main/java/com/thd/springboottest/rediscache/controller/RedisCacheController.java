package com.thd.springboottest.rediscache.controller;

import com.thd.springboottest.rediscache.bean.TestBean;
import com.thd.springboottest.rediscache.service.RedisCacheService;
import com.thd.springboottest.rediscache.service.RedisCacheServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/redisCache")
public class RedisCacheController {

    @Autowired
    private RedisCacheService redisCacheServiceImpl;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value="/addBean/{id}/{name}")
    // http://127.0.0.1:8899/thd/redisCache/addBean/1/zhangsan
    public TestBean addBean(@PathVariable String id,@PathVariable String name){
        TestBean tb = new TestBean();
        tb.setId(id);
        tb.setName(name);
        this.redisCacheServiceImpl.addBean(tb);
        return tb;
    }



    @ResponseBody
    @RequestMapping(value="/modiBean/{id}/{name}")
    public TestBean modiBean(@PathVariable String id,@PathVariable String name){
        TestBean tb = new TestBean();
        tb.setId(id);
        tb.setName(name);
        TestBean r = this.redisCacheServiceImpl.modiBean(tb);
        return r;
    }


    @ResponseBody
    @RequestMapping(value="/removeBean/{id}")
    public TestBean removeBean(@PathVariable String id){
        TestBean tb = this.redisCacheServiceImpl.removeBean(id);
        return tb;
    }

    @ResponseBody
    @RequestMapping(value="/queryBean/{id}")
    public TestBean queryBean(@PathVariable String id){
        TestBean tb = this.redisCacheServiceImpl.queryBean(id);
        return tb;
    }






    @ResponseBody
    @RequestMapping(value="/cacheInit")
    public Map<String,TestBean> cacheInit(){
        Map<String,TestBean> r = new HashMap<String,TestBean>();
        r.put("a",new TestBean("a","1"));
        r.put("b",new TestBean("b","2"));
        r.put("c",new TestBean("c","3"));
        r.put("d",new TestBean("d","4"));
        RedisCacheServiceImpl.list = r;
        return r;
    }


    @ResponseBody
    @RequestMapping(value="/addBeanAndRefreshCache/{id}/{name}")
    // http://127.0.0.1:8899/thd/redisCache/addBeanAndRefreshCache/1/zhangsan
    public Map<String,TestBean> addBeanAndRefreshCache(@PathVariable String id,@PathVariable String name){
        TestBean tb = new TestBean();
        tb.setId(id);
        tb.setName(name);
        Map<String,TestBean> r = this.redisCacheServiceImpl.addBeanAndRefreshCache(tb);
        return r;
    }



    @ResponseBody
    @RequestMapping(value="/modiBeanAndRefreshCache/{id}/{name}")
    // http://127.0.0.1:8899/thd/redisCache/modiBeanAndRefreshCache/1/zhangsan
    public Map<String,TestBean> modiBeanAndRefreshCache(@PathVariable String id,@PathVariable String name){
        TestBean tb = new TestBean();
        tb.setId(id);
        tb.setName(name);
        Map<String,TestBean> r = this.redisCacheServiceImpl.modiBeanAndRefreshCache(tb);
        return r;
    }


    @ResponseBody
    @RequestMapping(value="/removeBeanAndRefreshCache/{id}")
    public Map<String,TestBean> removeBeanAndRefreshCache(@PathVariable String id){
        Map<String,TestBean> r  = this.redisCacheServiceImpl.removeBeanAndRefreshCache(id);
        return r;
    }

    @ResponseBody
    @RequestMapping(value="/queryBeanForCache")
    public Map<String,TestBean> queryBeanForCache(){
        Map<String,TestBean> r = this.redisCacheServiceImpl.queryBeanForCache();
        return r;
    }



}
