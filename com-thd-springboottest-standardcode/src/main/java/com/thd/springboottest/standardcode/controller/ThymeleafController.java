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

@RequestMapping("/thymeleaf")
@Controller
public class ThymeleafController extends BasicController {
    @RequestMapping("/index")
    //url : http://127.0.0.1:8899/thd/thymeleaf/index
    public String index(){
        this.getLog().info("index()");
        return "index";
    }

}
