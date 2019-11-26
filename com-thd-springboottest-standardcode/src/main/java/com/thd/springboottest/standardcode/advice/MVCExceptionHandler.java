package com.thd.springboottest.standardcode.advice;

import com.thd.springboottest.standardcode.bean.Result;
import com.thd.springboottest.standardcode.exceptiondefine.MyParentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ExceptionHandler;

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

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        logger.info("进入error");

        // 是否属于自定义异常

        //System.out.println(e instanceof MyParentException);
        if (e instanceof MyParentException) {
            MyParentException myException = (MyParentException) e;
            logger.error("系统异常 {}", e);
            return Result.error(myException.getCode(), myException.getMessage());
        } else {
            logger.error("系统异常 {}", e);
            return Result.error(1000, "系统异常:" + e.getMessage());
        }
    }
}
