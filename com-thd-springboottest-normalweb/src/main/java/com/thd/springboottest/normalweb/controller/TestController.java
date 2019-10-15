package com.thd.springboottest.normalweb.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * com.thd.springboottest.normalweb.controller.TestController
 * User: devil13th
 * Date: 2019/10/14
 * Time: 22:37
 * Description: No Description
 */
@Controller
@RequestMapping("/test")
public class TestController {

    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;


    @RequestMapping("/setCookie/{cookieName}/{cookieValue}")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/test/setCookie/{cookieName}/{cookieValue}
    public List<Map<String,String>> setCookie(@PathVariable String cookieName,@PathVariable String cookieValue){
        System.out.println("---------- setCookie -------------");

        Cookie currentCk = new Cookie(cookieName,cookieValue);
        currentCk.setMaxAge(60*60*24*30) ;
        response.addCookie(new Cookie(cookieName,cookieValue));

//        Map<String,String> cookieMap = new HashMap<String,String>();
        List<Map<String,String>> cookieList = new ArrayList<Map<String,String>>();
        Cookie[] cks =  request.getCookies();
        for(Cookie ck : cks){
            Map<String,String> hm = new HashMap<String,String>();
            hm.put(ck.getName(),ck.getValue());
            cookieList.add(hm);
        }
        return cookieList;

    }


    @RequestMapping("a")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/test/a?param=a1
    public String a(HttpServletRequest req, HttpServletResponse res){
        System.out.println("---------- A -------------");
        String param = req.getParameter("param");
        String token = req.getHeader("token");
        System.out.println("token:" + token);
        res.addCookie(new Cookie("token","devil13th-a"));
        res.addHeader("tokenA","tokenA");

        Cookie[] cks =  req.getCookies();
        System.out.println("------------ cookie info : ------------");
        for(Cookie ck : cks){
            System.out.println(ck.getName() + ":" + ck.getValue());
        }
        return param;
    }

    @RequestMapping("b")
    @ResponseBody
    //url : http://127.0.0.1:8899/thd/test/b?param=b1
    public String b(HttpServletRequest req, HttpServletResponse res){
        System.out.println("---------- B -------------");
        String param = req.getParameter("param");
        String token = req.getHeader("token");

        System.out.println("token:" + token);
        res.addCookie(new Cookie("token","devil13th-b"));
        res.addHeader("tokenB","tokenB");

        System.out.println("------------ cookie info : ------------");
        Cookie[] cks =  req.getCookies();
        for(Cookie ck : cks){
            System.out.println(ck.getName() + ":" + ck.getValue());
        }
        return param;
    }
}
