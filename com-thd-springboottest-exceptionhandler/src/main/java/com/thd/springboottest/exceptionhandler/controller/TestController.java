package com.thd.springboottest.exceptionhandler.controller;

import com.thd.springboottest.exceptionhandler.bean.Result;
import com.thd.springboottest.exceptionhandler.exceptionbean.CustomException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.exceptionhandler.controller.TestController
 * User: devil13th
 * Date: 2019/11/24
 * Time: 0:56
 * Description: No Description
 */
@Controller
@RequestMapping("/exceptionhandler")
public class TestController {

    @RequestMapping("/test/{s}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/exceptionhandler/test/error
    public Result test(@PathVariable String s){

        if("error".equals(s)){
            throw new CustomException();
        }

        return Result.success(s);
    }

}
