package com.thd.springboottest.standardcode.controller;

import com.thd.springboottest.standardcode.controller.parent.BasicController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.standardcode.controller.TestController
 *
 * @author: wanglei62
 * @DATE: 2019/11/22 9:08
 **/

@RequestMapping("/test")
@Controller
public class TestController extends BasicController {
    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity test(){
        this.getLog().info("test()");
        return ResponseEntity.ok("SUCCESS");
    }

}
