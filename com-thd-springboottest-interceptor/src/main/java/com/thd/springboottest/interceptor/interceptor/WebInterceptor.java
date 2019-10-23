package com.thd.springboottest.interceptor.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * com.thd.springboottest.interceptor.interceptor.WebInterceptor
 *
 * @author: wanglei62
 * @DATE: 2019/9/30 17:32
 **/
@Component
public class WebInterceptor implements HandlerInterceptor {
    /*
     * 视图渲染之后的操作
     */
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        System.out.println(" completion ");
    }

    /*
     * 处理请求完成后视图渲染之前的处理操作
     */
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        System.out.println(" postHandle ");

    }

    /*
     * 进入controller层之前拦截请求
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        System.out.println("getContextPath:" + request.getContextPath());
        System.out.println("getServletPath:" + request.getServletPath());
        System.out.println("getRequestURI:" + request.getRequestURI());
        System.out.println("getRequestURL:" + request.getRequestURL());
        System.out.println("getRealPath:" + request.getSession().getServletContext().getRealPath("image"));

        //return true;


        response.getWriter().write("there's a error in interceptor");
        return false;
    }
}
