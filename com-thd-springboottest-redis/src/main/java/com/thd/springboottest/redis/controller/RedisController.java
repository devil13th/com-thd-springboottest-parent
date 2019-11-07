package com.thd.springboottest.redis.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/redis")
public class RedisController {
    private Logger log = LoggerFactory.getLogger(this.getClass());



    @Autowired
    //redis连接池
    private JedisPool jedisPool;

    @ResponseBody
    @RequestMapping(value="/jedisPoolInfo",method= RequestMethod.GET)
    public String jedisPoolInfo(){
        this.log.info("" + jedisPool.getNumWaiters());
        this.log.info("" + jedisPool.getNumIdle());
        this.log.info("" + jedisPool.getNumActive());
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        this.log.info("index");
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/set/{k}/{v}",method= RequestMethod.GET)
    // http://127.0.0.1:8899/thd/redis/set/a/1
    public String set(@PathVariable String k,@PathVariable String v){
        this.log.info(v);
        Jedis jedis = jedisPool.getResource();
        jedis.set(k,v);
        jedis.close();
        return v;
    }

    @ResponseBody
    @RequestMapping(value="get/{k}",method= RequestMethod.GET)
    // http://127.0.0.1:8899/thd/redis/get/a
    public String set(@PathVariable String k){
        this.log.info(k);
        Jedis jedis = jedisPool.getResource();
        String v = jedis.get(k);
        jedis.close();
        return v;
    }




}
