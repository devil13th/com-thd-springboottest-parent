package com.thd.springboottest.profile.controller;

import com.thd.springboottest.profile.cfgbean.CfgBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.xml.ws.RequestWrapper;

/**
 * com.thd.springboot.profile.controller.ProfileController
 *
 * @author: wanglei62
 * @DATE: 2019/10/20 23:29
 **/
@Controller
@RequestMapping("/test")
public class ProfileController {
    @Autowired
    private CfgBean cfgBean;
    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity<CfgBean> test(){
        System.out.println("test()");
        System.out.println(cfgBean);
        return ResponseEntity.ok(cfgBean);
    }

}
