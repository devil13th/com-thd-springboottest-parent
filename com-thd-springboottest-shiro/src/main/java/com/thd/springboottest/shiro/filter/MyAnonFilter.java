package com.thd.springboottest.shiro.filter;

import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.thd.springboottest.shiro.filter.MyFilter
 *
 * @author: wanglei62
 * @DATE: 2020/4/26 8:51
 **/
public class MyAnonFilter extends AnonymousFilter {
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) {
        System.out.println("------------" + this.getClass().getName() + "---------------");
        return true;
    }
}
