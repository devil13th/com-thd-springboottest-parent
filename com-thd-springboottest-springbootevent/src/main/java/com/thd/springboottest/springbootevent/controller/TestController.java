package com.thd.springboottest.springbootevent.controller;

import com.thd.springboottest.defaultadvisorautoproxycreator.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    @Autowired
    private UserService userService;
    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity test(){
        System.out.println("test");
        return ResponseEntity.ok("SUCCESS");
    }

    /**
     * 测试defaultAdvisorAutoProxyCreator包下的内容 - aop
     * @return
     */
    @RequestMapping("/test01")
    @ResponseBody
    public ResponseEntity test01(){
        System.out.println("test01");
        this.userService.print();
        return ResponseEntity.ok("SUCCESS");
    }




}
