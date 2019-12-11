package com.thd.springboottest.jsonmessageconverters.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.thd.springboottest.jsonmessageconverters.vo.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;

/**
 * com.thd.springboottest.jsonmessageconverters.controller.TestController
 *
 * @author: wanglei62
 * @DATE: 2019/12/9 23:39
 **/
@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/test")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/test/test
    public ResponseEntity test(){

        System.out.println("test");
        return ResponseEntity.ok("SUCCESS");
    }


    @RequestMapping("/format")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/test/format
    public ResponseEntity format(){
        User u = new User();
        u.setCreateTime(new Date());
        u.setName("thd");
        u.setAge(5);
        String jsonString = JSON.toJSONString(u);
        System.out.println(jsonString);
        return ResponseEntity.ok(u);
    }


    @RequestMapping("/parse")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/test/parse?name=thd&age=5&birthday=2019/12/10
    public ResponseEntity test(User u){

        String jsonString = JSON.toJSONString(u);
        System.out.println(jsonString);
        return ResponseEntity.ok(u);
    }


    @PostMapping("/parseRequestBody")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/test/parseRequestBody
    /**
     {
      'name':'thd',
      'age':5,
      'createTime':1575907200001
     }
     */
    public ResponseEntity parseRequestBody(@RequestBody User u){

        String jsonString = JSON.toJSONString(u);
        System.out.println(jsonString);
        return ResponseEntity.ok(u);
    }
}
