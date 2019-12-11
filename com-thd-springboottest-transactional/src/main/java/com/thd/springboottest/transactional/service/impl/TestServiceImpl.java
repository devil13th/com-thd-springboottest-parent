package com.thd.springboottest.transactional.service.impl;

import com.thd.springboottest.transactional.service.MyUserService;
import com.thd.springboottest.transactional.service.SysUserService;
import com.thd.springboottest.transactional.service.TestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.thd.springboottest.transactional.service.impl.TestServiceImpl
 *
 * @author: wanglei62
 * @DATE: 2019/11/28 10:31
 **/
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private SysUserService sysUserService;




    @Transactional
    public void deleteAll(){
        this.myUserService.deleteAllMyUser();
        this.sysUserService.deleteAllSysUser();
    };

    @Transactional
    public void insertA(boolean hasEx){
        this.myUserService.insertAwithReqTx("张三");
        this.myUserService.insertAwithReqTx("李四");
        this.myUserService.insertAwithReqTx("王五");
        if(hasEx){
            throw new RuntimeException("抛出业务异常!");
        }
        this.sysUserService.insertBwithReqTx("李四");
        this.sysUserService.insertBwithReqTx("张三");
        this.sysUserService.insertBwithReqTx("王五");
    };

    public void insertB(boolean hasEx){
        this.myUserService.insertAwithReqTx("张三");
        this.myUserService.insertAwithReqTx("李四");
        this.myUserService.insertAwithReqTx("王五");
        if(hasEx){
            throw new RuntimeException("抛出业务异常!");
        }
        this.sysUserService.insertBwithReqTx("李四");
        this.sysUserService.insertBwithReqTx("张三");
        this.sysUserService.insertBwithReqTx("王五");
    };

    @Transactional
    public void insertC(boolean hasEx){
        this.myUserService.insertAwithNoTx("张三");
        this.myUserService.insertAwithNoTx("李四");
        this.myUserService.insertAwithNoTx("王五");
        if(hasEx){
            throw new RuntimeException("抛出业务异常!");
        }
        this.sysUserService.insertBwithNoTx("李四");
        this.sysUserService.insertBwithNoTx("张三");
        this.sysUserService.insertBwithNoTx("王五");
    };

    @Transactional
    public void insertD(boolean hasEx){
        this.myUserService.insertAwithNewTx("张三");
        this.myUserService.insertAwithNewTx("李四");
        this.myUserService.insertAwithNewTx("王五");

        this.sysUserService.insertBwithReqTx("李四");
        this.sysUserService.insertBwithReqTx("张三");
        this.sysUserService.insertBwithReqTx("王五");

        if(hasEx){
            throw new RuntimeException("抛出业务异常!");
        }
    };


    @Transactional
    public void insertE(boolean hasEx){
        this.myUserService.insertNestA(hasEx);
    };

    @Transactional
    public void insertF(boolean hasEx){
        this.myUserService.insertNestAGetBean(hasEx);
    };


}
