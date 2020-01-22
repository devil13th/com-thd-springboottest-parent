package com.thd.springboottest.springbootevent.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.springbootevent.controller.TestController
 *
 * @author: wanglei62
 * @DATE: 2020/1/21 11:15
 **/
@Controller
@RequestMapping("/test")
public class TestController {
    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity test(){
        System.out.println("test");
        return ResponseEntity.ok("SUCCESS");
    }
}
