package com.thd.springboottest.standardcode.controller;

import com.thd.springboottest.standardcode.bean.Result;
import com.thd.springboottest.standardcode.controller.parent.BasicController;
import com.thd.springboottest.standardcode.exceptiondefine.CustomException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.standardcode.controller.ExceptionHandlerController
 * User: devil13th
 * Date: 2019/11/24
 * Time: 9:56
 * Description: No Description
 */
@Controller
@RequestMapping("/exceptionhandler")
public class ExceptionHandlerController extends BasicController {
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
