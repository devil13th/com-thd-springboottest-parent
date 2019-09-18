package com.thd.springboottest.mybatis.controller;

import com.thd.springboottest.mybatis.entity.MyUser;
import com.thd.springboottest.mybatis.entity.SysUser;
import com.thd.springboottest.mybatis.service.MyUserService;
import com.thd.springboottest.mybatis.service.SysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/mybatis")
public class MybatisController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private MyUserService myUserService;
    @ResponseBody
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(){
        this.log.info("index");
        return "index";
    }

    @ResponseBody
    @RequestMapping(value="/queryAll")
    //http://127.0.0.1:8899/thd/mybatis/queryAll
    public List queryAll(){
        return sysUserService.getAll();
    }

    @ResponseBody
    @RequestMapping(value="/insert")
    //http://127.0.0.1:8899/thd/mybatis/insert
    public String insert(){
        SysUser user = new SysUser();
        String id = UUID.randomUUID().toString();
        user.setUserId(id);
        user.setUserName("n_" + id);
        user.setUserMail("tt@163.com");
        user.setUserBirthday(new Date());
        user.setUserSex(1);
        sysUserService.insert(user);
        return "success";
    }


    @ResponseBody
    @RequestMapping(value="/saveMyUser")
    //http://127.0.0.1:8899/thd/mybatis/saveMyUser
    public String saveMyUser(){
        MyUser user = new MyUser();
        String id = UUID.randomUUID().toString();
        user.setId(id);
        user.setName("ZHANGSAN");
        user.setAge(new Random().nextInt(50));
        user.setBirthday(new Date());
        user.setCreateTime(new Date());
        myUserService.save(user);
        return "success";
    }


    @ResponseBody
    @RequestMapping(value="/updateMyUser")
    //http://127.0.0.1:8899/thd/mybatis/updateMyUser
    public String updateMyUser(){
        MyUser user = new MyUser();
        user.setId("03030d3c-79b9-4c79-a6fd-8728e5e87e00");
        user.setName("ZHANGSAN1");
        user.setAge(new Random().nextInt(50));
        user.setBirthday(new Date());
        user.setCreateTime(new Date());
        myUserService.update(user);
        return "success";
    }


    @ResponseBody
    @RequestMapping(value="/queryMyUserByName")
    //http://127.0.0.1:8899/thd/mybatis/queryMyUserByName
    public List queryMyUserByName(){
        MyUser user = new MyUser();
        user.setName("%Z%");
        return myUserService.queryByName(user);
    }

    @ResponseBody
    @RequestMapping(value="/queryMyUserById")
    //http://127.0.0.1:8899/thd/mybatis/queryMyUserById
    public MyUser queryMyUserById(){
        return myUserService.queryById("03030d3c-79b9-4c79-a6fd-8728e5e87e00");
    }

}
