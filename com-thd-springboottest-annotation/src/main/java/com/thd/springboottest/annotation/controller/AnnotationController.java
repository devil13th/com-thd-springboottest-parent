package com.thd.springboottest.annotation.controller;

import com.thd.springboottest.annotation.entity.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/annotation")
public class AnnotationController {
    Logger log = LoggerFactory.getLogger(this.getClass());

    @ResponseBody
    @RequestMapping(value="/testGet",method=RequestMethod.GET)
    public String testGet(){
        this.log.info("testGet");
        int i = 1;
//        while(i>0){
//            i++;
//            System.out.println(i);
//        }
        return "index";
    }


    @ResponseBody
    @RequestMapping(value="/testPostForRequestBody",method=RequestMethod.POST)
    public Person testPostForRequestBody(@RequestBody Person person){
        this.log.info("testPostForRequestBody");
        System.out.println(person);
        return person;
    }

    @ResponseBody
    @RequestMapping(value="/testPostForFormData",method=RequestMethod.POST)
    public Person testPostForFormData(Person person){
        this.log.info("testPostForFormData");
        System.out.println(person);
        return person;
    }
}
