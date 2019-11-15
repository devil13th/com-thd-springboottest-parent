package com.thd.springboottest.logback.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.logback.service.LogBackTestService
 *
 * @author: wanglei62
 * @DATE: 2019/11/15 0:07
 **/
@Component
public class LogBackTestService {
    Logger log = LoggerFactory.getLogger(this.getClass());
    public void test(){
        log.info(" 指定日志组件 ");
        log.error(" 指定日志组件 ");
    }
}
