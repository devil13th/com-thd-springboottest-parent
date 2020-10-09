package com.thd.springboottest.annotation.controller;

import com.thd.springboottest.annotation.entity.Person;
import com.thd.springboottest.annotation.ipt.MyImportBeanA;
import com.thd.springboottest.annotation.ipt.MyImportBeanB;
import com.thd.springboottest.annotation.ipt.MyImportBeanC;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import javax.websocket.server.PathParam;
import java.io.*;
import java.util.List;

/**
 * @author devil13th

 *
 **/
@Controller
@RequestMapping(value="/annotation")
public class AnnotationController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private Environment env;

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private MyImportBeanA myImportBeanA;
    @Autowired
    private MyImportBeanB myImportBeanB;
    @Autowired
    private MyImportBeanC myImportBeanC;
    @ResponseBody
    @RequestMapping("/test")
    public String test(){
        return "SUCCESS";
    }


    @ResponseBody
    @RequestMapping("/getAllComponent")
    public String getAllComponent(){
        String[] beanNames = applicationContext.getBeanDefinitionNames();
        for(String name : beanNames){
            System.out.println(name);
        }
        return "SUCCESS";
    }


    @ResponseBody
    @RequestMapping("/getComponentByType")
    public String getComponentByType(){
        String[] beanNames = applicationContext.getBeanNamesForType(Person.class);
        for(String name : beanNames){
            System.out.println(name);
        }
        return "SUCCESS";
    }


    @ResponseBody
    @RequestMapping("/getEnv")
    public String getEnv(){
        System.out.println("配置文件：" );
        String[] profileNames = env.getActiveProfiles();
        for(String profileName : profileNames){
            System.out.println(profileName);
        }

        System.out.println("默认配置文件：" );
        profileNames = env.getDefaultProfiles();
        for(String profileName : profileNames){
            System.out.println(profileName);
        }


        System.out.println("server.port : " + env.getProperty("server.port") );



        return "SUCCESS";
    }


    @ResponseBody
    @RequestMapping("/getMyImportBean")
    public String getMyImportBean(){
        System.out.println(myImportBeanA);
        System.out.println(myImportBeanB);
        System.out.println(myImportBeanC);

        return "SUCCESS";
    }


}
