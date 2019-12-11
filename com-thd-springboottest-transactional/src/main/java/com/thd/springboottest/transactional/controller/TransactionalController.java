package com.thd.springboottest.transactional.controller;

import com.thd.springboottest.transactional.service.MyUserService;
import com.thd.springboottest.transactional.service.SysUserService;
import com.thd.springboottest.transactional.service.TestService;
import com.thd.springboottest.transactional.service.impl.TestServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.transactional.controller.TransactionalController
 *
 * @author: wanglei62
 * @DATE: 2019/11/28 9:53
 **/
@RequestMapping("/transactional")
@Controller
public class TransactionalController {
    @Autowired
    private MyUserService myUserService;
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private TestService testService;


    private Logger log = LoggerFactory.getLogger(this.getClass());
    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity test(){
        this.log.info("test()");
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/deleteData")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/transactional/deleteData
    public ResponseEntity deleteData(){
        this.log.info("deleteData()");
        this.testService.deleteAll();
        return ResponseEntity.ok("SUCCESS");
    }



    /**
     * testService 带有required类型的事务
     *    |- myUserService 带有required类型的事务
     *    |- 抛出异常
     *    |- sysUserService带有required类型的事务
     *
     *    全部没有插入成功,事务达到预期
     */
    @RequestMapping("/testa/{hasEx}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/transactional/testa/true
    public ResponseEntity testa(@PathVariable boolean hasEx){
        this.log.info("testa()");
        this.testService.insertA(hasEx);
        return ResponseEntity.ok("SUCCESS");
    }


    /**
     * testService 没有事务
     *     |- myUserService 带有required类型的事务
     *     |- 抛出异常
     *     |- sysUserService带有required类型的事务
     *
     *     myUserService插入成功 sysUserService插入失败  事务未达到预期
     */
    @RequestMapping("/testb/{hasEx}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/transactional/testb/true
    public ResponseEntity testb(@PathVariable boolean hasEx){
        this.log.info("testb()");
        this.testService.insertB(hasEx);
        return ResponseEntity.ok("SUCCESS");
    }


    /**
     * testService 带有required类型的事务
     *     |- myUserService 没有事务
     *     |- 抛出异常
     *     |- sysUserService 没有事务
     *     全部没有插入成功，事务达到预期
     */
    @RequestMapping("/testc/{hasEx}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/transactional/testc/true
    public ResponseEntity testc(@PathVariable boolean hasEx){
        this.log.info("testc()");
        this.testService.insertC(hasEx);
        return ResponseEntity.ok("SUCCESS");
    }


    /**
     * testService 带有required类型的事务
     *     |- myUserService 带有required类型的事务
     *     |- sysUserService 带有requires_new类型的事务
     *     |- 抛出异常
     *     myUserService插入失败 sysUserService插入成功，事务达到预期
     */
    @RequestMapping("/testd/{hasEx}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/transactional/testd/true
    public ResponseEntity testd(@PathVariable boolean hasEx){
        this.log.info("testd()");
        this.testService.insertD(hasEx);
        return ResponseEntity.ok("SUCCESS");
    }


    @RequestMapping("/teste/{hasEx}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/transactional/teste/true
    public ResponseEntity teste(@PathVariable boolean hasEx){
        this.log.info("teste()");
        this.testService.insertE(hasEx);
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/testf/{hasEx}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/transactional/testf/true
    public ResponseEntity testf(@PathVariable boolean hasEx){
        this.log.info("testf()");
        this.testService.insertF(hasEx);
        return ResponseEntity.ok("SUCCESS");
    }



}
