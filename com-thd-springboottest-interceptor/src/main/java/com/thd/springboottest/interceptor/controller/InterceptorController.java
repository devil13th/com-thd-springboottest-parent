package com.thd.springboottest.interceptor.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * com.thd.springboottest.interceptor.controller.InterceptorController
 *
 * @author: wanglei62
 * @DATE: 2019/9/30 17:36
 **/
@RestController
@RequestMapping("/interceptor")
public class InterceptorController {
    @RequestMapping("/test")
    public String test(){
        return "SUCCESS";
    }

    @RequestMapping("/test01")
    // url: http://127.0.0.1:8899/thd/aop/aopTest01?usr=admin&pwd=123
    public String test01(@RequestParam String usr, @RequestParam int pwd){
        System.out.println(usr + ":" + pwd);
        if(pwd > 100){
            throw new RuntimeException("错误：密码不能大于100");
        }
        return (usr + ":" + pwd);
    }
}
