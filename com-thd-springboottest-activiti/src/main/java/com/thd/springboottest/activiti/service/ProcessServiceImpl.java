package com.thd.springboottest.activiti.service;

import org.springframework.stereotype.Service;

/**
 * com.thd.springboottest.activiti.service.ProcessServiceImpl
 * 用于测试在activiti listener中注入该bean
 * @author: wanglei62
 * @DATE: 2020/7/8 10:34
 **/
@Service
public class ProcessServiceImpl implements ProcessService{
    @Override
    public void testSpringIocInjection() {
        System.out.println(" injection bean ");
    }
}
