package com.thd.springboottest.standardcode.controller;

import com.thd.springboottest.standardcode.controller.parent.BasicController;
import com.thd.springboottest.standardcode.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;

/**
 * com.thd.springboottest.standardcode.controller.RedisController
 * User: devil13th
 * Date: 2019/11/24
 * Time: 21:46
 * Description: No Description
 */

@RequestMapping("/redis")
@Controller
public class RedisController extends BasicController {
    @Resource
    private RedisTemplate myRedisTemplate;


    @Autowired
    //redis连接池
    private JedisPool jedisPool;

    @ResponseBody
    @RequestMapping(value="/jedisPoolInfo",method= RequestMethod.GET)
    public String jedisPoolInfo(){
        this.getLog().debug("" + jedisPool.getNumWaiters());
        this.getLog().debug("" + jedisPool.getNumIdle());
        this.getLog().debug("" + jedisPool.getNumActive());
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        this.getLog().debug("index");
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/set/{k}/{v}",method= RequestMethod.GET)
    // http://127.0.0.1:8899/thd/redis/set/a/1
    public String set(@PathVariable String k, @PathVariable String v){
        this.getLog().debug(v);
        Jedis jedis = jedisPool.getResource();
        jedis.set(k,v);
        jedis.close();
        return v;
    }

    @ResponseBody
    @RequestMapping(value="get/{k}",method= RequestMethod.GET)
    // http://127.0.0.1:8899/thd/redis/get/a
    public String get(@PathVariable String k){
        this.getLog().debug(k);
        Jedis jedis = jedisPool.getResource();
        String v = jedis.get(k);
        jedis.close();
        return v;
    }





    @ResponseBody
    @RequestMapping(value="/testRedisTemplateSet",method= RequestMethod.GET)
    // http://127.0.0.1:8899/thd/redis/testRedisTemplateSet
    public MyUser testRedisTemplateSet(){
        MyUser user = new MyUser();
        user.setUserId("1");
        user.setUserBirthday(new Date());
        user.setUserName("devil13th");
        user.setUserAge(5);
        user.setUserCreateTime(new Timestamp(new Date().getTime()));

        this.myRedisTemplate.opsForValue().set(user.getUserId(),user);
        return user;
    }

    @ResponseBody
    @RequestMapping(value="/testRedisTemplateGet",method= RequestMethod.GET)
    // http://127.0.0.1:8899/thd/redis/testRedisTemplateGet
    public MyUser testRedisTemplateGet(){
        MyUser user = (MyUser)this.myRedisTemplate.opsForValue().get("1");
        return user;
    }
}
