package com.thd.springboottest.redistemplate.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.thd.springboottest.redistemplate.bean.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * com.thd.springboottest.redistemplate.controller.RedistemplateController
 *
 * @author: wanglei62
 * @DATE: 2021/4/21 15:48
 **/
@RequestMapping("/redisTemplate")
@Controller
public class RedistemplateController {

    @Resource
    private RedisTemplate myRedisTemplate;


    @RequestMapping("/test")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/redisTemplate/test
    public ResponseEntity test() throws Exception{
        System.out.println("test ...");
        System.out.println(myRedisTemplate.getValueSerializer());



        User u = new User();
        u.setUserAge(5);
        u.setUserBirthday(new Date());
        u.setUserId("1");
        u.setUserName("devil13th");

        ObjectMapper mapper=new ObjectMapper();
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
        mapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
        // 序列化
        String str = mapper.writeValueAsString(u);
        System.out.println(String.format("TestBean03:%s",u));
        byte[] s = this.myRedisTemplate.getValueSerializer().serialize(u);

        System.out.println(new String(s));
        return ResponseEntity.ok("SUCCESS");
    }


    @RequestMapping("/saveKeyValueForString")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/redisTemplate/saveKeyValueForString
    public ResponseEntity saveKeyValueForString(){
        System.out.println("saveKeyValueForString ...");
        this.myRedisTemplate.opsForValue().set("name","devil13th");
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/getKeyValueForString")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/redisTemplate/getKeyValueForString
    public ResponseEntity getKeyValueForString(){
        System.out.println("getKeyValueForString ...");
        Object r = this.myRedisTemplate.opsForValue().get("name");
        return ResponseEntity.ok(r);
    }

    @RequestMapping("/saveKeyValueForObject")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/redisTemplate/saveKeyValueForObject
    public ResponseEntity saveKeyValueForObject(){
        System.out.println("saveKeyValueForObject ...");
        User u = new User();
        u.setUserAge(5);
        u.setUserBirthday(new Date());
        u.setUserId("1");
        u.setUserName("devil13th");
        this.myRedisTemplate.opsForValue().set("user",u);
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/getKeyValueForObject")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/redisTemplate/getKeyValueForObject
    public ResponseEntity getKeyValueForObject(){
        System.out.println("getKeyValueForObject ...");
        Object u = this.myRedisTemplate.opsForValue().get("user");

        return ResponseEntity.ok(u);
    }



    @RequestMapping("/saveHash")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/redisTemplate/saveHash
    public ResponseEntity saveHash(){
        System.out.println("saveHash ...");

        this.myRedisTemplate.opsForHash().put("hx","name","devil13th");

        Map m = new HashMap();
        User u = new User();
        u.setUserAge(5);
        u.setUserBirthday(new Date());
        u.setUserId("1");
        u.setUserName("devil13th");
        m.put("user",u);
        this.myRedisTemplate.opsForHash().putAll("hx",m);
        return ResponseEntity.ok("SUCCESS");
    }


    @RequestMapping("/getHashKey")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/redisTemplate/getHashKey
    public ResponseEntity getHashKey(){
        System.out.println("getHashKey ...");

        Set k = this.myRedisTemplate.opsForHash().keys("hx");
        return ResponseEntity.ok(k);
    }

    @RequestMapping("/getHashValue")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/redisTemplate/getHashValue
    public ResponseEntity getHashValue(){
        System.out.println("getHashValue ...");

        Object k = this.myRedisTemplate.opsForHash().get("hx","user");
        return ResponseEntity.ok(k);
    }


}
