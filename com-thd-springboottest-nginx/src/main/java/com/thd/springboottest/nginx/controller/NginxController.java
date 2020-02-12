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

    // ===================  以下方法是测试nginx下不同应用 cookie 获取和设置 ==================== //
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
        System.out.println("session id:" + request.getSession().getId());
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
        System.out.println("session id:" + request.getSession().getId());
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
        System.out.println("session id:" + request.getSession().getId());
        return ResponseEntity.ok("SUCCESS");
    }

    // ===================  以下方法是测试cookie path 路径问题 ==================== //

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


}
