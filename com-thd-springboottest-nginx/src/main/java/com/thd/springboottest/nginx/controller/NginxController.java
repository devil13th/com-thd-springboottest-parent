package com.thd.springboottest.nginx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.ws.Response;
import java.util.HashMap;
import java.util.Map;

/**
 * com.thd.springboottest.nginx.controller.NginxController
 *
 * @author: wanglei62
 * @DATE: 2019/11/7 23:14
 **/
@Controller
@RequestMapping("/nginx")
public class NginxController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;

    @RequestMapping("/setCookie/{cookieName}/{cookieValue}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/nginx/setCookie/name/devil13th
    public ResponseEntity setCookie(@PathVariable  String cookieName,@PathVariable String cookieValue){
        System.out.println("setCookie");
        Cookie ck = new Cookie(cookieName,cookieValue);
        ck.setMaxAge(6 * 24 * 60 * 60);
        ck.setPath("/");

        response.addCookie(ck);
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/getCookie")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/nginx/getCookie
    public ResponseEntity getCookie(){
        System.out.println("getCookie");
        Cookie[] cks = request.getCookies();
        Map m = new HashMap();
        if(cks != null){
            for(Cookie ck : cks){
                m.put(ck.getName(),ck.getValue());
            }
        }
        return ResponseEntity.ok(m);
    }

    @RequestMapping("/delCookie/{cookieName}")
    @ResponseBody
    public ResponseEntity delCookie(@PathVariable String cookieName){
        System.out.println("delCookie");
        Cookie ck = new Cookie(cookieName,null);
        ck.setMaxAge(-1);
        ck.setPath("/");
        response.addCookie(ck);
        return ResponseEntity.ok("SUCCESS");
    }
}
