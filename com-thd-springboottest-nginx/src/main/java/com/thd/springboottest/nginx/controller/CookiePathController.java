package com.thd.springboottest.nginx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 使用nginx 解决js跨域问题
 *
 * @author: wanglei62
 * @DATE: 2019/11/7 23:14
 **/
@Controller
public class CookiePathController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/setCookiePath")
    @ResponseBody
    public ResponseEntity setCookiePath(){
        System.out.println("setCookiePath");
        Cookie ck = new Cookie("rt","rt");
        ck.setMaxAge(6 * 24 * 60 * 60);
        ck.setPath("/");

        Cookie ck1 = new Cookie("rt/a","rt/a");
        ck1.setMaxAge(6 * 24 * 60 * 60);
        ck1.setPath("/a");

        Cookie ck2 = new Cookie("rt/a/b","rt/a/b");
        ck2.setMaxAge(6 * 24 * 60 * 60);
        ck2.setPath("/a/b");

        response.addCookie(ck);
        response.addCookie(ck1);
        response.addCookie(ck2);
        System.out.println("session id:" + request.getSession().getId());
        return ResponseEntity.ok("SUCCESS");
    }


    @RequestMapping("/a")
    @ResponseBody
    public ResponseEntity a(){
        System.out.println("a");

        System.out.println("session id:" + request.getSession().getId());
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/a/b")
    @ResponseBody
    public ResponseEntity ab(){
        System.out.println("a/b");
        System.out.println("session id:" + request.getSession().getId());
        return ResponseEntity.ok("SUCCESS");
    }


}
