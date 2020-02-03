package com.thd.springboottest.webmvcconfigurer.controller;

import com.alibaba.fastjson.JSONObject;
import com.thd.springboottest.webmvcconfigurer.bean.User;
import com.thd.springboottest.webmvcconfigurer.formatter.BoolFormatterDemoDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/test")
public class TestController {

    private Logger log = LoggerFactory.getLogger(this.getClass());

    // 静态资源路径设置 参见 MvcConfig.addResourceHandlers 方法
    // http://127.0.0.1:8899/thd/st/staticresource.txt
    // http://127.0.0.1:8899/thd/st/a.jpg


    @RequestMapping("/test")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/test/test
    public ResponseEntity test(){
        System.out.println("test");
        return ResponseEntity.ok("SUCCESS");
    }




    @RequestMapping("/testFastJsonSerializer")
    @ResponseBody
    /**
     * 序列化工具 参见 参见 MvcConfig.configureMessageConverters 方法
     */
    // url : http://127.0.0.1:8899/thd/test/testFastJsonSerializer
    public ResponseEntity testFastJsonSerializer(){
        System.out.println("testFastJsonSerializer");
        User u = new User();
        u.setAge(5);
        u.setBirthday(new Date());
        u.setId("1");
        u.setPassword("123456");
        u.setPhone("13800138000");
        u.setUserName("devil13th");
        return ResponseEntity.ok(u);
    }

    @RequestMapping(value="/testFastJsonUnSerializer",method= RequestMethod.POST)
    @ResponseBody
    /**
     * 序列化工具 参见 参见 MvcConfig.configureMessageConverters 方法
     */
    // static index.html url : http://127.0.0.1:8899/thd/index.html
    // url : http://127.0.0.1:8899/thd/test/testFastJsonUnSerializer
    public ResponseEntity testFastJsonUnSerializer(@RequestBody User user){
        System.out.println("testFastJsonUnSerializer");
        log.info(JSONObject.toJSONString(user));
        return ResponseEntity.ok(user);
    }


    @RequestMapping(value="/boolFormatter",method= RequestMethod.POST)
    @ResponseBody
    /**
     * formatter 参见 参见 MvcConfig.addFormatters 方法
     */
    // static index.html url : http://127.0.0.1:8899/thd/index.html
    // url : http://127.0.0.1:8899/thd/test/boolFormatter
    public ResponseEntity boolFormatter(BoolFormatterDemoDto dto){
        System.out.println("boolFormatter");
        log.info(JSONObject.toJSONString(dto));
        return ResponseEntity.ok(dto);
    }
}
