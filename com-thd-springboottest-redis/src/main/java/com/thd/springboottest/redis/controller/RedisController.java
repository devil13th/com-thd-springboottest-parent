package com.thd.springboottest.redis.controller;

import com.thd.springboottest.redis.vo.MyUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import javax.naming.StringRefAddr;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/redis")
public class RedisController {
    private Logger log = LoggerFactory.getLogger(this.getClass());

    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }


    @Resource
    private RedisTemplate myRedisTemplate;

    @Resource
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    //redis连接池
    private JedisPool jedisPool;

    @ResponseBody
    @RequestMapping(value="/jedisPoolInfo",method= RequestMethod.GET)
    // url : http://127.0.0.1:8899/thd/redis/jedisPoolInfo
    public String jedisPoolInfo(){
        this.getLog().debug("" + jedisPool.getNumWaiters());
        this.getLog().debug("" + jedisPool.getNumIdle());
        this.getLog().debug("" + jedisPool.getNumActive());
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/index",method= RequestMethod.GET)
    // url : http://127.0.0.1:8899/thd/redis/index
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
        System.out.println(123);
        MyUser user = new MyUser();
        user.setUserId("1");
        user.setUserBirthday(new Date());
        user.setUserName("devil13th");
        user.setUserAge(5);
        user.setUserCreateTime(new Timestamp(new Date().getTime()));

        this.myRedisTemplate.opsForValue().set(user.getUserId(),user);

        this.myRedisTemplate.opsForValue().set("a/b","xxxxx");
        return user;
    }

    @ResponseBody
    @RequestMapping(value="/testRedisTemplateGet",method= RequestMethod.GET)
    // http://127.0.0.1:8899/thd/redis/testRedisTemplateGet
    public MyUser testRedisTemplateGet(){

        MyUser user = (MyUser)this.myRedisTemplate.opsForValue().get("1");
        return user;
    }


    @ResponseBody
    @RequestMapping(value="/testRedisTemplate",method= RequestMethod.GET)
    // http://127.0.0.1:8899/thd/redis/testRedisTemplate
    public MyUser testRedisTemplate(){
        MyUser user = new MyUser();
        user.setUserId("1");
        user.setUserBirthday(new Date());
        user.setUserName("devil13th");
        user.setUserAge(5);
        user.setUserCreateTime(new Timestamp(new Date().getTime()));

        this.myRedisTemplate.opsForValue().set(user.getUserId(),user);

        this.myRedisTemplate.opsForValue().set("devil13th","123456");
        this.myRedisTemplate.opsForHash().put("xx","name","devil13th");
        this.myRedisTemplate.opsForHash().put("xx","value",user);

        List l = new ArrayList();
        l.add(1);
        l.add(2);
        l.add(3);
        l.add(4);
        this.myRedisTemplate.opsForList().leftPush("list:tttt",l);


        MyUser user2 = new MyUser();
        user2.setUserId("2");
        user2.setUserBirthday(new Date());
        user2.setUserName("devil13th2");
        user2.setUserAge(5);
        user2.setUserCreateTime(new Timestamp(new Date().getTime()));


        MyUser user3 = new MyUser();
        user3.setUserId("3");
        user3.setUserBirthday(new Date());
        user3.setUserName("devil13th3");
        user3.setUserAge(5);
        user3.setUserCreateTime(new Timestamp(new Date().getTime()));


        List l2 = new ArrayList();
        l2.add(user);
        l2.add(user2);
        l2.add(user3);
        l2.add(user);
        this.myRedisTemplate.opsForList().leftPush("list:2",l2);



        System.out.println(this.myRedisTemplate.opsForValue().get(user.getUserId()));
        MyUser users = (MyUser)this.myRedisTemplate.opsForValue().get(user.getUserId());
        System.out.println(users);

        return user;
    }

    @ResponseBody
    @RequestMapping("testSerializer")
    // http://127.0.0.1:8899/thd/redis/testSerializer
    public String testSerializer(){
        Jackson2JsonRedisSerializer jackson2JsonRedisSerializer = new Jackson2JsonRedisSerializer(Object.class);
        JdkSerializationRedisSerializer jdkSerializationRedisSerializer = new JdkSerializationRedisSerializer();
        StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();

        String str = "string";
        byte[] a = jackson2JsonRedisSerializer.serialize(str);
        byte[] b = jdkSerializationRedisSerializer.serialize(str);
        byte[] c = stringRedisSerializer.serialize(str);

        System.out.println(new String(a));
        System.out.println(new String(b));
        System.out.println(new String(c));

        System.out.println(jackson2JsonRedisSerializer.deserialize(jackson2JsonRedisSerializer.serialize(str)));
        System.out.println(jdkSerializationRedisSerializer.deserialize(jdkSerializationRedisSerializer.serialize(str)));
        System.out.println(stringRedisSerializer.deserialize(stringRedisSerializer.serialize(str)));

        System.out.println(new RedisTemplate<>().getDefaultSerializer());
        System.out.println(this.redisTemplate.getKeySerializer());
        System.out.println(this.redisTemplate.getValueSerializer());
        System.out.println(this.redisTemplate.getHashKeySerializer());
        System.out.println(this.redisTemplate.getKeySerializer());

        System.out.println(this.stringRedisTemplate.getKeySerializer());
        System.out.println(this.stringRedisTemplate.getValueSerializer());
        System.out.println(this.stringRedisTemplate.getHashKeySerializer());
        System.out.println(this.stringRedisTemplate.getHashValueSerializer());


        System.out.println(this.myRedisTemplate.getDefaultSerializer());
        return "SUCCESS";
    }
}
