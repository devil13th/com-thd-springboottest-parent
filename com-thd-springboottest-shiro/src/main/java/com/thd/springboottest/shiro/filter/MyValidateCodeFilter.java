package com.thd.springboottest.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;

/**
 * com.thd.springboottest.shiro.filter.MyFilter
 *
 * @author: wanglei62
 * @DATE: 2020/4/26 8:51
 **/
public class MyValidateCodeFilter extends AccessControlFilter {

    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
        return false;
    }

    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {

        // 生成6位随机数
        String code =  UUID.randomUUID().toString().replace("-","").substring(0,6);
        // 保存到session中
        SecurityUtils.getSubject().getSession().setAttribute("validateCode",code);




        //解决 WebUtils.toHttp 往返回response写数据跨域问题
        HttpServletRequest httpRequest = (HttpServletRequest) servletRequest;
        String origin = httpRequest.getHeader("Origin");
        HttpServletResponse httpServletResponse = (HttpServletResponse) servletResponse;
        httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
        //通过对 Credentials 参数的设置，就可以保持跨域 Ajax 时的 Cookie
        //设置了Allow-Credentials，Allow-Origin就不能为*,需要指明具体的url域
        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");


        // 为了测试所以返回了验证码，实际中是发短信 然后返回true即可
        WebUtils.toHttp(servletResponse).setContentType("application/json; charset=utf-8");
        WebUtils.toHttp(servletResponse).getWriter().print("{\"validatecode\":\"" + code + "\"}");


        return false;
    }
}
