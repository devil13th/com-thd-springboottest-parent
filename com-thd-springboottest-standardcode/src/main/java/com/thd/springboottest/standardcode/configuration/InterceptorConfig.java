package com.thd.springboottest.standardcode.configuration;

import com.thd.springboottest.standardcode.interceptor.WebInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * com.thd.springboottest.interceptor.interceptor.InterceptorConfig
 *
 * @author: wanglei62
 * @DATE: 2019/9/30 17:33
 **/
@Configuration
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private WebInterceptor webInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 自定义拦截器，添加拦截路径和排除拦截路径
        registry.addInterceptor(webInterceptor).addPathPatterns("/**").excludePathPatterns("/static/**");
    }
}
