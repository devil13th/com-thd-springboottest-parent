package com.thd.springboottest.annotation.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/annotation")
public class AnnotationController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value="/index",method=RequestMethod.GET)
    public String index(){
        this.log.info("index");
        int i = 1;
        while(i>0){
            i++;
            System.out.println(i);
        }
        return "index";
    }
}
