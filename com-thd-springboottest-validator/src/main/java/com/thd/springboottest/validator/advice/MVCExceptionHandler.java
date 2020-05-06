package com.thd.springboottest.validator.advice;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * com.thd.springboottest.exceptionhandler.handler.ExceptionHandle
 * User: devil13th
 * Date: 2019/11/24
 * Time: 1:01
 * Description: No Description
 */
@ControllerAdvice
public class MVCExceptionHandler {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandler.class);

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    @ResponseBody
    public ResponseEntity handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        logger.info("进入error");
        // 从异常对象中拿到ObjectError对象
        List<ObjectError> objectErrors = e.getBindingResult().getAllErrors();

        String errorsInfo = "";
        // 如果有参数校验失败，会将错误信息封装成对象组装在BindingResult里
        for (ObjectError error : objectErrors) {
            errorsInfo += error.getDefaultMessage() + ";";
        }


        return ResponseEntity.ok("系统异常:" + errorsInfo);

    }
}
