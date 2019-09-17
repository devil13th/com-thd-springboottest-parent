package com.thd.springboottest.jdbc.controller;

import com.thd.springboottest.jdbc.bean.User;
import com.thd.springboottest.jdbc.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/jdbc")
public class JdbcController {
    @Autowired
    private UserService userService;

    Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        this.log.info("index");
        return "index";
    }
    @ResponseBody
    @RequestMapping(value="/saveUser/{userName}")
    //http://127.0.0.1:8899/thd/jdbc/saveUser/1/aaa
    public String  saveUser(@PathVariable String userName){
        User u = new User();
        u.setName(userName);
        this.userService.saveUser(u);
        return "save success";
    }

    @ResponseBody
    @RequestMapping(value="/updateUser/{id}/{userName}")
    //http://127.0.0.1:8899/thd/jdbc/updateUser/402881e846e119dc0146e119e0230001/aaa
    public String  updateUser(@PathVariable String id,@PathVariable String userName){
        User u = new User();
        u.setId(id);
        u.setName(userName);
        this.userService.updateUser(u);
        return "update success";
    }

    @ResponseBody
    @RequestMapping(value="/queryUserById/{id}")
    //http://127.0.0.1:8899/thd/jdbc/queryUserById/402881e846e119dc0146e119e0230001
    public User  queryUserById(@PathVariable String id){
       User u = this.userService.queryUserById(id);
       return u;
    }

    @ResponseBody
    @RequestMapping(value="/saveMoreUser")
    //http://127.0.0.1:8899/thd/jdbc/saveMoreUser
    public String saveMoreUser(){
        User u1 = new User();
        u1.setId("1");
        u1.setName("a");

        User u2 = new User();
        u2.setId("2");
        u2.setName("b");
        this.userService.saveMoreUser(u1,u2);


        return " save more user success";
    }


    @ResponseBody
    @RequestMapping(value="/testTransaction1")
    //http://127.0.0.1:8899/thd/jdbc/testTransaction1
    public String testTransaction1(){
        this.userService.testTransaction1();
        return " testTransaction1 success";
    }

    @ResponseBody
    @RequestMapping(value="/testTransaction2")
    //http://127.0.0.1:8899/thd/jdbc/testTransaction2
    public String testTransaction2(){
        this.userService.testTransaction2();
        return " testTransaction2 success";
    }

    @ResponseBody
    @RequestMapping(value="/testTransaction3")
    //http://127.0.0.1:8899/thd/jdbc/testTransaction3
    public String testTransaction3(){
        this.userService.testTransaction3();
        return " testTransaction3 success";
    }

    @ResponseBody
    @RequestMapping(value="/testTransaction4")
    //http://127.0.0.1:8899/thd/jdbc/testTransaction4
    public String testTransaction4(){
        this.userService.testTransaction4();
        return " testTransaction4 success";
    }



}
