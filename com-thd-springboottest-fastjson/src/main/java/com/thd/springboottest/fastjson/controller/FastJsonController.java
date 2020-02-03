package com.thd.springboottest.fastjson.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.thd.springboottest.fastjson.entity.User;
import com.thd.springboottest.fastjson.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

@Controller
@RequestMapping("/fastjson")
public class FastJsonController {
    @Autowired
    private UserService userService;

    @RequestMapping("/test")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/fastjson/test
    public ResponseEntity test(){
        System.out.println("test");
        return ResponseEntity.ok("SUCCESS");
    }



    @RequestMapping("/testJSONField")
    @ResponseBody
    /**
     * 1. @JSONField 注释属性测试，参见User.java中的注释
     * 2. 序列化api JSONObject.toJSONString()
     */
    // url : http://127.0.0.1:8899/thd/fastjson/testJSONField
    public ResponseEntity testJSONField(){
        System.out.println("testJSONField");
        User u = userService.getUserByName("wsl");
        String json = JSON.toJSONString(u);
        return ResponseEntity.ok(json);
    }


    @RequestMapping("/testWriteNullValue")
    @ResponseBody
    /**
     *
     * 1. 序列化输出空值的属性
     *
     * SerializerFeature	   |    描述
     * WriteNullListAsEmpty	   |    将Collection类型字段的字段空值输出为[]
     * WriteNullStringAsEmpty  |	将字符串类型字段的空值输出为空字符串 ""
     * WriteNullNumberAsZero   |	将数值类型字段的空值输出为0
     * WriteNullBooleanAsFalse |	将Boolean类型字段的空值输出为false
     */
    // url : http://127.0.0.1:8899/thd/fastjson/testWriteNullValue
    public ResponseEntity testWriteNullValue(){
        System.out.println("testWriteNullValue");
        User u = new User();
        u.setId("1");
        u.setUserName("devil13th");

        /**
         *
         * SerializerFeature	   |    描述
         * WriteNullListAsEmpty	   |    将Collection类型字段的字段空值输出为[]
         * WriteNullStringAsEmpty  |	将字符串类型字段的空值输出为空字符串 ""
         * WriteNullNumberAsZero   |	将数值类型字段的空值输出为0
         * WriteNullBooleanAsFalse |	将Boolean类型字段的空值输出为false
         */
        String json = JSON.toJSONString(u,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullBooleanAsFalse,
                SerializerFeature.WriteNullNumberAsZero);
        return ResponseEntity.ok(json);
    }




    @RequestMapping("/parseJsonToJsonObject")
    @ResponseBody
    /**
     * 1. 反序列化成JSONObject对象
     */
    // url : http://127.0.0.1:8899/thd/fastjson/parseJsonToJsonObject
    public ResponseEntity parseJsonToJsonObject(){
        System.out.println("parseJsonToJsonObject");
        String str = "{\"userId\":\"1\",\"userName\":\"devil13th\",\"phone\":\"\",\"birthday\":null,\"age\":0,\"roles\":[]}";
        Object obj = JSON.parseObject(str);
        System.out.println(obj);
        return ResponseEntity.ok(obj);
    }


    @RequestMapping("/parseJsonToUser")
    @ResponseBody
    /**
     * 1. 反序列化成某个指定的类型
     * 参见 https://github.com/alibaba/fastjson/wiki/enable_autotype
     */
    // url : http://127.0.0.1:8899/thd/fastjson/parseJsonToUser
    public ResponseEntity parseJsonToUser(){
        System.out.println("parseJsonToUser");
        //第一点： 开启反序列化自动类型转换 - 如果反序列化成某个指定的类，则必须加这句
        ParserConfig.getGlobalInstance().setAutoTypeSupport(true);

        User u = new User();
        u.setId("1");
        u.setUserName("devil13th");

        /**
         * 如果加入SerializerFeature.WriteClassName 特性
         * 则序列化的JSON字符串会带有@type属性
         *
         * 序列化的时候加入了SerializerFeature.WriteClassName 特性，
         * 才会反序列化成某个指定的类，(或者使用JSONObject.parseObject(json,XXX.class))
         * 否则反序列化成JSONObject类型的对象
         *
         *
         */
        // 第二点： 序列化的时候必须加入SerializerFeature.WriteClassName 特性，否则反序列化会直接转成JSONObject类型，导致不能强转成需要的对象类型
        // 会报异常: com.alibaba.fastjson.JSONObject cannot be cast to com.thd.springboottest.fastjson.entity.User
        String json = JSON.toJSONString(u);
        System.out.println(json);

        User u1  = (User)JSON.parse(json);


        return ResponseEntity.ok(u1);
    }

    /**
     * 反序列化的时候指定目标类 JSON.parseObject(json,User.class);
     * @return
     */
    @RequestMapping("/parseJsonToUser2")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/fastjson/parseJsonToUser2
    public ResponseEntity parseJsonToUser2(){
        System.out.println("parseJsonToUser2");

        User u = new User();
        u.setId("1");
        u.setUserName("devil13th");
        String json = JSON.toJSONString(u);
        System.out.println(json);

        User u2 = (User)JSON.parseObject(json,User.class);
        return ResponseEntity.ok(u2);
    }

    /**
     * 使用 SerializerFeature.WriteClassName 指定序列化目标类
     * 反序列化时就可以不指定目标类型了
     * @return
     */
    @RequestMapping("/parseJsonToUser3")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/fastjson/parseJsonToUser3
    public ResponseEntity parseJsonToUser3(){
        System.out.println("parseJsonToUser3");

        User u = new User();
        u.setId("1");
        u.setUserName("devil13th");
        String json = JSON.toJSONString(u,SerializerFeature.WriteClassName);
        System.out.println(json);

        User u2 = (User)JSON.parse(json);

        JSONObject ss = JSON.parseObject(json);
        // 下面一句报错 因为json字符串中有@type属性
        JSONObject s1 = (JSONObject)JSON.parse(json);
        return ResponseEntity.ok(u2);
    }

    /**
     * 反序列化通用对象JsonObject
     * @return
     */
    @RequestMapping("/parseJsonToUser4")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/fastjson/parseJsonToUser4
    public ResponseEntity parseJsonToUser4(){
        System.out.println("parseJsonToUser4");
        User u = new User();
        u.setId("1");
        u.setUserName("devil13th");
        String json = JSON.toJSONString(u);
        System.out.println(json);

        JSONObject jsonObj = (JSONObject)JSON.parse(json);
        JSONObject jsonObj2 = (JSONObject)JSONObject.parse(json);
        return ResponseEntity.ok(jsonObj);
    }

    /**
     * 使用 TypeReference 指定反序列化目标类
     * @return
     */
    @RequestMapping("/parseJsonToUser5")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/fastjson/parseJsonToUser5
    public ResponseEntity parseJsonToUser5(){
        System.out.println("parseJsonToUser5");
        User u = new User();
        u.setId("1");
        u.setUserName("devil13th");
        String json = JSON.toJSONString(u);
        System.out.println(json);

        User jsonObj = (User)JSON.parseObject(json,new TypeReference<User>() {
        });
        return ResponseEntity.ok(jsonObj);
    }



    /**
     * 反序列化的目标类是List<JSONObject> 泛型
     * @return
     */
    @RequestMapping("/parseJsonToUser6")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/fastjson/parseJsonToUser6
    public ResponseEntity parseJsonToUser6(){
        System.out.println("parseJsonToUser6");
        int i = 0 ;
        List<User> l = new ArrayList<User>();
        while(i < 5){
            User u = new User();
            u.setId(String.valueOf(i));
            u.setUserName("name_" + i);
            u.setBirthday(new Date());
            u.setAge(i);
            l.add(u);
            i++;
        }

        String json = JSON.toJSONString(l);
        System.out.println(json);

        List<User> l2 = (List)JSON.parseObject(json,List.class);

        return ResponseEntity.ok(l2);
    }


    /**
     * 反序列化的目标类是Map<xx,JSONObject> 泛型
     * @return
     */
    @RequestMapping("/parseJsonToUser7")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/fastjson/parseJsonToUser7
    public ResponseEntity parseJsonToUser7(){
        System.out.println("parseJsonToUser7");
        int i = 0 ;
        Map<String,User> m = new HashMap<String,User>();
        while(i < 5){
            User u = new User();
            u.setId(String.valueOf(i));
            u.setUserName("name_" + i);
            u.setBirthday(new Date());
            u.setAge(i);
            m.put(String.valueOf(i),u);
            i++;
        }

        String json = JSON.toJSONString(m);
        System.out.println(json);

        Map<String,User> l2 = (Map)JSON.parseObject(json,Map.class);

        return ResponseEntity.ok(l2);
    }


    /**
     * 反序列化的目标类是List<JSONObject> 泛型
     * @return
     */
    @RequestMapping("/parseJsonToUser8")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/fastjson/parseJsonToUser8
    public ResponseEntity parseJsonToUser8(){
        System.out.println("parseJsonToUser8");
        int i = 0 ;
        List<User> l = new ArrayList<User>();
        while(i < 5){
            User u = new User();
            u.setId(String.valueOf(i));
            u.setUserName("name_" + i);
            u.setBirthday(new Date());
            u.setAge(i);
            l.add(u);
            i++;
        }

        String json = JSON.toJSONString(l);
        System.out.println(json);

        List<User> l2 = (List)JSON.parseObject(json,new TypeReference<List<User>>() {});

        return ResponseEntity.ok(l2);
    }


    /**
     * 反序列化的目标类是Map<xx,JSONObject> 泛型
     * @return
     */
    @RequestMapping("/parseJsonToUser9")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/fastjson/parseJsonToUser9
    public ResponseEntity parseJsonToUser9(){
        System.out.println("parseJsonToUser9");
        int i = 0 ;
        Map<String,User> m = new HashMap<String,User>();
        while(i < 5){
            User u = new User();
            u.setId(String.valueOf(i));
            u.setUserName("name_" + i);
            u.setBirthday(new Date());
            u.setAge(i);
            m.put(String.valueOf(i),u);
            i++;
        }

        String json = JSON.toJSONString(m);
        System.out.println(json);

        Map<String,User> m2 = (Map)JSON.parseObject(json,new TypeReference<Map<String,User>>() {});


        return ResponseEntity.ok(m2);
    }



}
