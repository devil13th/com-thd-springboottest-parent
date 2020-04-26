package com.thd.springboottest.shiro.filter;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.StringUtils;
import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * com.thd.springboottest.shiro.filter.MyFilter
 *  实现动态权限
 * @author: wanglei62
 * @DATE: 2020/4/26 8:51
 **/
//public class MyFilter extends AccessControlFilter {
public class MyPermsFilter extends PermissionsAuthorizationFilter {

    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        Subject subject = this.getSubject(request, response);
        if (subject.getPrincipal() == null) {
            response.getWriter().write("{code:401,msg:'请先登录吧'}");
        } else {

            System.out.println("------------" + this.getClass().getName() + "---------------");
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            HttpServletResponse httpResponse = (HttpServletResponse) response;
            String requestUrl = httpRequest.getServletPath();
            System.out.println("PermissionsAuthorizationFilter - 请求的url:  " + requestUrl);

            // 检查是否拥有访问权限

            if (subject.getPrincipal() == null) {
                this.saveRequestAndRedirectToLogin(request, response);
            } else {
                // 转换成http的请求和响应
                HttpServletRequest req = (HttpServletRequest) request;
                HttpServletResponse resp = (HttpServletResponse) response;

                // 下面的add应该是url 判断某用户是否有访问某个资源的权限
                if(SecurityUtils.getSubject().hasRole("add")){
                    return true;
                }else{
                    response.getWriter().write("{code:401,msg:'没有权限呀'}");
                };
            }
            return false;
        }
        return false;
    }




}
