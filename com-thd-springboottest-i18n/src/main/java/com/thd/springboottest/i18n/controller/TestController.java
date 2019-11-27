package com.thd.springboottest.i18n.controller;

import com.thd.springboottest.i18n.utils.MessageUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Locale;

/**
 * com.thd.springboottest.i18n.controller.TestController
 *
 * @author: wanglei62
 * @DATE: 2019/11/27 0:05
 **/
@Controller
@RequestMapping("/test")
public class TestController {
    @Autowired
    private MessageSource messageSource;

    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity test(){

        System.out.println(LocaleContextHolder.getLocale());
        //LocaleContextHolder.setLocale(Locale.CHINESE);
        String name = MessageUtils.get("login.title");
        String aa = messageSource.getMessage("login.name", null, LocaleContextHolder.getLocale());
        //msg = messageSource.getMessage(I18nConstant.NAME, null, LocaleContextHolder.getLocale());
        return ResponseEntity.ok(name + "||" + aa);
    }
}
