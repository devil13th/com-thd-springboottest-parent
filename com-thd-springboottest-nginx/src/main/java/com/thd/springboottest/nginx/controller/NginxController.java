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
@RequestMapping("/nginx")
public class NginxController {
    @Autowired
    private HttpServletRequest request;
    @Autowired
    private HttpServletResponse response;


    /**
     * 设置cookie
     * @param cookieName cookie name
     * @param cookieValue cookie value
     * @return
     */
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


    /**
     * 展示所有cookie
     * @return
     */
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

    /**
     * 删除cookie
     * @param cookieName
     * @return
     */
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
