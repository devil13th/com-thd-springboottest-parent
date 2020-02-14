package com.thd.springboottest.task.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.task.controller.TaskController
 *
 * @author: wanglei62
 * @DATE: 2020/2/14 14:46
 **/
@RequestMapping("/task")
@Controller
public class TaskController {


    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/test")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/task/test
    public ResponseEntity test(){
        this.logger.info("test");
        return ResponseEntity.ok("SUCCESS");
    }
}
