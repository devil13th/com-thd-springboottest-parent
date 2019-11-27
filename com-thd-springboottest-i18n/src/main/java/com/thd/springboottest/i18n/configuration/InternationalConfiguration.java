package com.thd.springboottest.i18n.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

import java.util.Locale;

/**
 * com.thd.springboottest.i18n.interceptor.Internationalization
 *
 * @author: wanglei62
 * @DATE: 2019/11/27 0:21
 **/
@Configuration
public class InternationalConfiguration implements WebMvcConfigurer {


    // 可以不进行配置，系统会进行自动配置
    @Bean(name = "messageSource")
    public ResourceBundleMessageSource getMessageResource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        // 可以通过下面的方法对资源文件位置进行修改或配置，如果不修改则默认application.yml中的spring.messages.basename属性配置的内容
        // messageSource.setBasenames();
        // messageSource.setBasenames();
        return messageSource;
    }


    /**
     * 默认解析器 其中locale表示默认语言
     * 功能为将语言设置如何进行保存 ， 例如保存到session  保存到cookie ，也可以自定义
     */
    @Bean
    public LocaleResolver localeResolver() {
        //使用session保存语言设置
//        SessionLocaleResolver localeResolver = new SessionLocaleResolver();
//        localeResolver.setDefaultLocale(Locale.CHINESE);
//        return localeResolver;

        //使用cookie保存语言设置
        CookieLocaleResolver localeResolver = new CookieLocaleResolver();
        // 设置保存语言cookie的名称
        localeResolver.setCookieName("i18n");
//        localeResolver.setCookieMaxAge(Integer.MAX_VALUE);
//        localeResolver.setCookiePath("/");
//        localeResolver.setDefaultLocale(Locale.CHINESE);
        return localeResolver;
    }

    /**
     * 默认拦截器 其中lang表示切换语言的参数名
     * http://xxxx/xxx?lang=zh_CN
     * http://xxxx/xxx?lang=zh_US
     * 参数值为语言配置文件的后缀
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        LocaleChangeInterceptor localeInterceptor = new LocaleChangeInterceptor();
        localeInterceptor.setParamName("lang");
        registry.addInterceptor(localeInterceptor);

    }

}
