package com.thd.springboottest.shiro.filter;

import org.apache.shiro.web.filter.authz.RolesAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * com.thd.springboot.framework.shiro.filter.MyRoleFilter
 *
 * @author: wanglei62
 * @DATE: 2020/9/17 18:03
 **/
public class MyRoleFilter extends RolesAuthorizationFilter {

    public MyRoleFilter()
    {
        super();
    }


    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        /**
         * 可以直接返回错误信息  也可以抛出 AuthorizationException 异常后定义Controller的异常切面处理(@RestControllerAdvice)
         */
        WebUtils.toHttp(response).setContentType("application/json; charset=utf-8");
        WebUtils.toHttp(response).getWriter().print("{\"code\":401,\"msg\":\"角色没有权限\"}");


        return false;
    }
}
