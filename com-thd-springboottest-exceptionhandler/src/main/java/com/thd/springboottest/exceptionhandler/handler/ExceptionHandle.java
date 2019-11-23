package com.thd.springboottest.exceptionhandler.handler;

import com.thd.springboottest.exceptionhandler.bean.Result;
import com.thd.springboottest.exceptionhandler.exceptionbean.MyParentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.exceptionhandler.handler.ExceptionHandle
 * User: devil13th
 * Date: 2019/11/24
 * Time: 1:01
 * Description: No Description
 */
@ControllerAdvice
public class ExceptionHandle {
    private final static Logger logger = LoggerFactory.getLogger(ExceptionHandle.class);

    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    public Result handle(Exception e) {
        logger.info("进入error");

        // 是否属于自定义异常

        System.out.println(e instanceof MyParentException);
        if (e instanceof MyParentException) {
            MyParentException myException = (MyParentException) e;
            return Result.error(myException.getCode(), myException.getMessage());
        } else {
            logger.error("系统异常 {}", e);

            return Result.error(1000, "系统异常!");
        }
    }
}
