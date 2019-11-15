package com.thd.springboottest.logback.controller;

import com.thd.springboottest.logback.service.LogBackTestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    private LogBackTestService logBackTestService;

    //url : http://127.0.0.1:8899/thd/logBack/index
    @RequestMapping("/index")
    @ResponseBody
    public String index(){
        log.info(" info() ");
        log.debug(" debug() ..");
        log.warn(" warn() ");
        log.error(" error() ");
        logBackTestService.test();
        return "SUCCESS";
    }
}
