package com.thd.springboottest.shiro.filter;

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
//public class MyFilter extends AccessControlFilter {
public class MyAuthcFilter extends FormAuthenticationFilter {



    public MyAuthcFilter()
    {
        super();
    }
    /**
     *
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，参见ShiroConfig.shiroFilterFactoryBean方法中 “ map.put("/perm/*","anon,authc[12345],prems[admin,alal]"); ”
     * 如果允许访问返回true，否则false；
     * (感觉这里应该是对白名单（不需要登录的接口）放行的)
     *
     * adviceFilter中的boolean preHandle(...)方法其实最后调用的是isAccessAllowed() || onAccessDenied() 所以
     * 如果isAccessAllowed返回true则onAccessDenied方法不会继续执行
     * 这里可以用来判断一些不被通过的链接（个人备注）
     * * 表示是否允许访问 ，如果允许访问返回true，否则false；
     * @param request
     * @param response
     * @param mappedValue 表示写在拦截器中括号里面的字符串 mappedValue 就是 [urls] 配置中拦截器参数部分
     * @return
     * @throws Exception
     * */
    @Override
    public boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue){
        if (((HttpServletRequest)request).getMethod().toUpperCase().equals("OPTIONS"))
        {
            return true;
        }
        return super.isAccessAllowed(request, response, mappedValue);
    }


    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     * onAccessDenied是否执行取决于isAccessAllowed的值，如果返回true则onAccessDenied不会执行；如果返回false，执行onAccessDenied
     * 如果onAccessDenied也返回false，则直接返回，不会进入请求的方法（只有isAccessAllowed和onAccessDenied的情况下）
     * onAccessDenied如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     * */
    @Override
    protected boolean onAccessDenied(ServletRequest request, ServletResponse response)throws Exception{
        if (isLoginRequest(request, response)) { // 是否是登录的地址
            if (isLoginSubmission(request, response)) {
                return executeLogin(request, response); // 执行登录
            } else {
                return true;
            }
        } else {
            //解决 WebUtils.toHttp 往返回response写数据跨域问题
            HttpServletRequest httpRequest = (HttpServletRequest) request;
            String origin = httpRequest.getHeader("Origin");
            HttpServletResponse httpServletResponse = (HttpServletResponse) response;
            httpServletResponse.setHeader("Access-Control-Allow-Origin", origin);
            //通过对 Credentials 参数的设置，就可以保持跨域 Ajax 时的 Cookie
            //设置了Allow-Credentials，Allow-Origin就不能为*,需要指明具体的url域
            httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");

            // 返回固定的JSON串
            WebUtils.toHttp(response).setContentType("application/json; charset=utf-8");
            WebUtils.toHttp(response).getWriter().print("{\"code\":401,\"msg\":\"尚未登录\"}");
            return false;
        }
    }



    /**
     *
     * 表示是否允许访问；mappedValue就是[urls]配置中拦截器参数部分，如果允许访问返回true，否则false；
     * (感觉这里应该是对白名单（不需要登录的接口）放行的)
     * 如果isAccessAllowed返回true则onAccessDenied方法不会继续执行
     * 这里可以用来判断一些不被通过的链接（个人备注）
     * * 表示是否允许访问 ，如果允许访问返回true，否则false；
     * @param request
     * @param response
     * @param mappedValue 表示写在拦截器中括号里面的字符串 mappedValue 就是 [urls] 配置中拦截器参数部分
     * @return
     * @throws Exception
     * */
//    @Override
//    protected boolean isAccessAllowed(ServletRequest request, ServletResponse response, Object mappedValue) {
//        System.out.println("------------" + this.getClass().getName() + "---------------");
//
//        if (isLoginRequest(request, response)) {
//            return true;
//        } else {
//            Subject subject = getSubject(request, response);
//            // If principal is not null, then the user is known and should be allowed access.
//            return subject.getPrincipal() != null;
//        }
//    }

    /**
     * 表示当访问拒绝时是否已经处理了；如果返回true表示需要继续处理；如果返回false表示该拦截器实例已经处理了，将直接返回即可。
     * onAccessDenied是否执行取决于isAccessAllowed的值，如果返回true则onAccessDenied不会执行；如果返回false，执行onAccessDenied
     * 如果onAccessDenied也返回false，则直接返回，不会进入请求的方法（只有isAccessAllowed和onAccessDenied的情况下）
     * */
//    @Override
//    protected boolean onAccessDenied(ServletRequest request, ServletResponse response) throws Exception {
//        System.out.println("------------" + this.getClass().getName() + "---------------");
//        HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
//        //这里是个坑，如果不设置的接受的访问源，那么前端都会报跨域错误，因为这里还没到corsConfig里面
//        httpServletResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest) request).getHeader("Origin"));
//        httpServletResponse.setHeader("Access-Control-Allow-Credentials", "true");
//        httpServletResponse.setCharacterEncoding("UTF-8");
//        httpServletResponse.setContentType("application/json");
//        httpServletResponse.getWriter().write("{code:403,msg:'请登录'}");
//        return false;
//
//    }
}
