package com.thd.springboottest.standardcode.controller;

import com.thd.springboottest.standardcode.controller.parent.BasicController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.standardcode.controller.LoggerController
 * User: devil13th
 * Date: 2019/11/23
 * Time: 17:19
 * Description: No Description
 */
@Controller
@RequestMapping("/logback")
public class LogbackController extends BasicController {

    @RequestMapping("/info")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/logback/info
    public ResponseEntity info(){
        this.getLog().info(" info() ");
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/error")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/logback/error
    public ResponseEntity error(){
        this.getLog().error(" error() ");
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/debug")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/logback/debug
    public ResponseEntity debug(){
        this.getLog().debug(" debug() ");
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/warn")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/logback/debug
    public ResponseEntity warn(){
        this.getLog().warn(" warn() ");
        return ResponseEntity.ok("SUCCESS");
    }
}
