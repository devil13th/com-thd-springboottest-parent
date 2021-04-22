package com.thd.springboottest.redistemplate.controller;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.thd.springboottest.redistemplate.bean.Item;
import com.thd.springboottest.redistemplate.bean.User;
import com.thd.springboottest.redistemplate.serializer.JsonDateDeserializer;
import com.thd.springboottest.redistemplate.serializer.JsonDateSerializer;
import com.thd.springboottest.redistemplate.serializer.JsonTimestampDeserializer;
import com.thd.springboottest.redistemplate.serializer.JsonTimestampSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;

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
        u.setUserCreateTime(new Timestamp(new Date().getTime()));

        ObjectMapper objectMapper=new ObjectMapper();
//        objectMapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        objectMapper.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);







        objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        objectMapper.disable(DeserializationFeature.ADJUST_DATES_TO_CONTEXT_TIME_ZONE);
        JavaTimeModule javaTimeModule = new JavaTimeModule();
        //Date序列化和反序列化
        javaTimeModule.addSerializer(Date.class,new JsonDateSerializer());
        javaTimeModule.addDeserializer(Date.class,new JsonDateDeserializer());
        // timestamp的序列化和反序列话
        javaTimeModule.addSerializer(Timestamp.class,new JsonTimestampSerializer());
        javaTimeModule.addDeserializer(Timestamp.class,new JsonTimestampDeserializer());
        objectMapper.registerModule(javaTimeModule);





        // 序列化
        String str = objectMapper.writeValueAsString(u);
        System.out.println(String.format("TestBean03:%s",str));
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
        u.setUserCreateTime(new Timestamp(new Date().getTime()));
        u.setLdt(LocalDateTime.now());

        Item item = new Item();
        item.setId(30);
        item.setName("mama");
        item.setType("family");
        u.setItem(item);


        List<Item> l = new ArrayList<Item>();
        for(int i = 0 , j = 3 ; i < j ; i++){
            Item aitem = new Item(i,"name_" + i , "type" + i);
            l.add(aitem);
        }
        u.setItemList(l);


        List<User> userList = new ArrayList<User>();
        for(int i = 0 , j = 3 ; i < j ; i++){
            User aUser = new User();
            aUser.setUserId(String.valueOf(i));
            aUser.setUserName("user_" + i);
            aUser.setUserBirthday(new Date());
            aUser.setUserAge(i);
            userList.add(aUser);
        }
        u.setChildren(userList);

        this.myRedisTemplate.opsForValue().set("user",u);
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/getKeyValueForObject")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/redisTemplate/getKeyValueForObject
    public ResponseEntity getKeyValueForObject(){
        System.out.println("getKeyValueForObject ...");
        User u = (User)this.myRedisTemplate.opsForValue().get("user");
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
