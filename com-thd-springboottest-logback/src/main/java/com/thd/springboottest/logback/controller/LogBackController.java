package com.thd.springboottest.logback.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.logback.controller.LogBackController
 * User: devil13th
 * Date: 2019/9/18
 * Time: 22:50
 * Description: No Description
 */
@Controller
@RequestMapping("/logBack")
public class LogBackController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        log.info(" index() ");
        log.debug(" index() ");
        log.error(" index() ");
        return "SUCCESS";
    }
}
