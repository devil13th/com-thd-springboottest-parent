package com.thd.springboottest.standardcode.controller;

import com.thd.springboottest.standardcode.controller.parent.BasicController;
import com.thd.springboottest.standardcode.entity.MyUser;
import com.thd.springboottest.standardcode.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * com.thd.springboottest.standardcode.controller.MyBatisController
 * User: devil13th
 * Date: 2019/11/23
 * Time: 21:40
 * Description: No Description
 */
@Controller
@RequestMapping("/myBatis")
public class MyBatisController extends BasicController {
    @Autowired
    private MyUserService myUserServiceImpl;


    @RequestMapping("/queryUserById/{id}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/myBatis/queryUserById/1
    public ResponseEntity queryUserById(@PathVariable String id){
        this.getLog().info("queryUserById()");
        MyUser user = myUserServiceImpl.queryUserById(id);
        return ResponseEntity.ok(user);
    }

    @RequestMapping("/queryUserByName/{name}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/myBatis/queryUserByName/devil13th
    public ResponseEntity queryUserByName(@PathVariable String name){
        this.getLog().info("queryUserByName()");
        MyUser user = myUserServiceImpl.queryUserByName(name);
        return ResponseEntity.ok(user);
    }
}
