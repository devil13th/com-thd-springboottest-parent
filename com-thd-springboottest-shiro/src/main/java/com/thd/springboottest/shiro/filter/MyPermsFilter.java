package com.thd.springboottest.shiro.filter;

import org.apache.shiro.web.filter.authz.PermissionsAuthorizationFilter;
import org.apache.shiro.web.util.WebUtils;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.io.IOException;

/**
 * com.thd.springboottest.shiro.filter.MyFilter
 *  实现动态权限
 * @author: wanglei62
 * @DATE: 2020/4/26 8:51
 **/
//public class MyFilter extends AccessControlFilter {
public class MyPermsFilter extends PermissionsAuthorizationFilter {
    /**
     * 根据请求接口路径进行验证
     * @param request
     * @param response
     * @param mappedValue
     * @return
     * @throws IOException
     */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) throws IOException {
        // 获取接口请求路径
        String servletPath = WebUtils.toHttp(request).getServletPath();
        mappedValue = new String[]{servletPath};
        boolean b = super.isAccessAllowed(request, response, mappedValue);
        return b;
    }

    /**
     * 解决权限不足302问题
     * @param request
     * @param response
     * @return
     * @throws IOException
     */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
        /**
         * 可以直接返回错误信息  也可以抛出 AuthorizationException 异常后定义Controller的异常切面处理(@RestControllerAdvice)
         */
        WebUtils.toHttp(response).setContentType("application/json; charset=utf-8");
        WebUtils.toHttp(response).getWriter().print("{\"code\":401,\"msg\":\"没有权限\"}");

        /* Subject subject = getSubject(request, response);
        if (subject.getPrincipal() != null) {

            return super.onAccessDenied(request,response);


        } else {

            //解决 WebUtils.toHttp 往返回response写数据跨域问题
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String origin = httpRequest.getHeader("Origin");
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
            //通过对 Credentials 参数的设置，就可以保持跨域 Ajax 时的 Cookie
            //设置了Allow-Credentials，Allow-Origin就不能为*,需要指明具体的url域
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

            WebUtils.toHttp(response).setContentType("application/json; charset=utf-8");
            WebUtils.toHttp(response).getWriter().print("{code:401,msg:'没有权限'}");
        }
        */
        return false;
    }

//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws IOException {
//        Subject subject = this.getSubject(request, response);
//        if (subject.getPrincipal() == null) {
//            response.getWriter().write("{code:401,msg:'请先登录吧'}");
//        } else {
//
//            System.out.println("------------" + this.getClass().getName() + "---------------");
//            HttpServletRequest httpRequest = (HttpServletRequest) request;
//            HttpServletResponse httpResponse = (HttpServletResponse) response;
//            String requestUrl = httpRequest.getServletPath();
//            System.out.println("PermissionsAuthorizationFilter - 请求的url:  " + requestUrl);
//
//            // 检查是否拥有访问权限
//
//            if (subject.getPrincipal() == null) {
//                this.saveRequestAndRedirectToLogin(request, response);
//            } else {
//                // 转换成http的请求和响应
//                HttpServletRequest req = (HttpServletRequest) request;
//                HttpServletResponse resp = (HttpServletResponse) response;
//
//                // 下面的add应该是url 判断某用户是否有访问某个资源的权限
//                if(SecurityUtils.getSubject().hasRole("add")){
//                    return true;
//                }else{
//                    response.getWriter().write("{code:401,msg:'没有权限呀'}");
//                };
//            }
//            return false;
//        }
//        return false;
//    }




}
