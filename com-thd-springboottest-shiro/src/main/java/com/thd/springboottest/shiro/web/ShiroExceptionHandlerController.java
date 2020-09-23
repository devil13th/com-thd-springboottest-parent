package com.thd.springboottest.shiro.web;

import com.thd.springboottest.shiro.bean.Message;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.UnauthorizedException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * com.thd.springboot.framework.web.ExceptionHandlerController
 *
 * @author: wanglei62
 * @DATE: 2020/9/15 15:42
 **/
@RestControllerAdvice
@Order(1)
public class ShiroExceptionHandlerController {
    private Logger logger = LoggerFactory.getLogger(getClass());


    @ExceptionHandler(UnauthorizedException.class)
    public Message handlUnauthorizedExceptionException(UnauthorizedException e){
        logger.error(e.getMessage(), e);
//        return Message.error("尚未授权");
        return Message.error(e.getMessage());
    }
    @ExceptionHandler(AuthorizationException.class)
    public Message handleAuthorizationException(AuthorizationException e){
        logger.error(e.getMessage(), e);
//        return Message.error("没有权限，请联系管理员授权");
        return Message.error(e.getMessage());
    }

    @ExceptionHandler(AuthenticationException.class)
    public Message handleAuthenticationExceptionException(AuthenticationException e){
        logger.error(e.getMessage(), e);
//        return Message.error("账号或密码错误");
        return Message.error(e.getMessage());
    }

}
