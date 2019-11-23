package com.thd.springboottest.standardcode.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.thd.springboottest.standardcode.controller.BasicController
 *
 * @author: wanglei62
 * @DATE: 2019/11/22 9:08
 **/
public class BasicController {
    private Logger log = LoggerFactory.getLogger(this.getClass());
    @Autowired
    protected HttpServletRequest request;
    @Autowired
    protected HttpServletResponse response;
    public Logger getLog() {
        return log;
    }

    public void setLog(Logger log) {
        this.log = log;
    }
}
