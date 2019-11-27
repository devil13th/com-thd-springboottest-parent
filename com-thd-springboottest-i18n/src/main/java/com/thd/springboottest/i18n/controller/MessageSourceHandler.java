package com.thd.springboottest.i18n.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * com.thd.springboottest.i18n.controller.MessageSourceHandler
 *
 * @author: wanglei62
 * @DATE: 2019/11/27 0:17
 **/
@Component
public class MessageSourceHandler {
    @Autowired
    private HttpServletRequest request;

    @Autowired
    private MessageSource messageSource;

    public String getMessage(String messageKey) {
        String message = messageSource.getMessage(messageKey, null, RequestContextUtils.getLocale(request));
        return message;
    }
}
