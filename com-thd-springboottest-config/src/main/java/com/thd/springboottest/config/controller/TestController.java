package com.thd.springboottest.config.controller;

import com.thd.springboottest.config.config.Cfg;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.config.controller.TestController
 *
 * @author: wanglei62
 * @DATE: 2020/1/1 16:41
 **/
@Controller
@RequestMapping(value="/config")
public class TestController {
    @Autowired
    private Cfg cfg ;

    @ResponseBody
    @RequestMapping(value="/test")
    public ResponseEntity test(){
        System.out.println("test()");
        System.out.println(cfg);
        return ResponseEntity.ok(cfg);
    }
}
