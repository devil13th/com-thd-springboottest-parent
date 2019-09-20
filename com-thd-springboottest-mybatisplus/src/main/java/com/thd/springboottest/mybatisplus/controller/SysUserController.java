package com.thd.springboottest.mybatisplus.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thd.springboottest.mybatisplus.entity.SysUser;
import com.thd.springboottest.mybatisplus.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * com.thd.springboottest.mybatisplus.controller.SysUserController
 *
 * @author: wanglei62
 * @DATE: 2019/9/20 16:16
 **/
@Controller
@RequestMapping("/sysUser")
public class SysUserController {
    @Autowired
    private SysUserService sysUserService;

    //url : http://127.0.0.1:8899/thd/sysUser/queryList
    @RequestMapping("/queryList")
    @ResponseBody
    public List queryList(){
        return this.sysUserService.queryAll();
    }

    //url : http://127.0.0.1:8899/thd/sysUser/queryByCondition
    @RequestMapping("/queryByCondition")
    @ResponseBody
    public List queryByCondition(){
        QueryWrapper<SysUser> qw = new QueryWrapper<SysUser>();
        qw.eq("user_name","n_8d9640b2-7b8e-46b6-99ec-0cb3cf5c0392");
        return this.sysUserService.queryByCondition(qw);
    }

    //url : http://127.0.0.1:8899/thd/sysUser/queryById/1
    @RequestMapping("/queryById/{id}")
    @ResponseBody
    public SysUser queryById(@PathVariable String id){
        return this.sysUserService.queryById(id);
    }

    //url : http://127.0.0.1:8899/thd/sysUser/queryByPage/1/5
    @RequestMapping("/queryByPage/{currentPage}/{pageSize}")
    @ResponseBody
    public List queryByPage(@PathVariable int currentPage,@PathVariable int pageSize){
        QueryWrapper<SysUser> qw = new QueryWrapper<SysUser>();
        qw.eq("user_sex",1);
        return this.sysUserService.queryByPage(qw,currentPage,pageSize);
    }
}
