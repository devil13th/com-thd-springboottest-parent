package com.thd.springboottest.webmvcconfigurer.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    /*
     * 视图渲染之后的操作
     */
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
            throws Exception {
        logger.info("  completion ");
    }

    /*
     * 处理请求完成后视图渲染之前的处理操作
     */
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
            throws Exception {
        logger.info(" postHandle ");

    }

    /*
     * 进入controller层之前拦截请求
     */
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object obj) throws Exception {
        logger.info("getContextPath:" + request.getContextPath());
        logger.info("getServletPath:" + request.getServletPath());
        logger.info("getRequestURI:" + request.getRequestURI());
        logger.info("getRequestURL:" + request.getRequestURL());
        logger.info("getRealPath:" + request.getSession().getServletContext().getRealPath("image"));

        return true;


//        response.getWriter().write("there's a error in interceptor");
//        return false;
    }
}
