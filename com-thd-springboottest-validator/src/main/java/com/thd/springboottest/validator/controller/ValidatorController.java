package com.thd.springboottest.validator.controller;

import com.thd.springboottest.validator.bean.User;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

/**
 * com.thd.springboottest.standardcode.controller.ValidatorController
 *
 * @author: wanglei62
 * @DATE: 2020/5/6 9:16
 **/
@Controller
@RequestMapping("/validator")
public class ValidatorController {
    @RequestMapping("/test")
    // url :http://127.0.0.1:8899/thd/validator/test
    // requestbody:
    /**
     * {
     * 	"id":1,
     * 	"account":"1237",
     * 	"password":"abcdefghti",
     * 	"email":"aaaab"
     * }
     */
    public ResponseEntity test(@RequestBody @Valid User user, BindingResult bindingResult){
        System.out.println("test");
        String errorsInfo = "";
        // 如果有参数校验失败，会将错误信息封装成对象组装在BindingResult里
        for (ObjectError error : bindingResult.getAllErrors()) {
            errorsInfo += error.getDefaultMessage() + ";";
        }

        return ResponseEntity.ok(errorsInfo);
    }


    // 使用ExceptionHandler 来处理验证错误的异常信息 切面类为MVCExceptionHandler,方法是handleMethodArgumentNotValidException
    @RequestMapping("/test02")
    // url :http://127.0.0.1:8899/thd/validator/test02
    // requestbody:
    /**
     * {
     * 	"id":1,
     * 	"account":"1237",
     * 	"password":"abcdefghti",
     * 	"email":"aaaab"
     * }
     */
    public ResponseEntity test02(@RequestBody @Valid User user){
        System.out.println("test02");

        return ResponseEntity.ok(user);
    }

}
