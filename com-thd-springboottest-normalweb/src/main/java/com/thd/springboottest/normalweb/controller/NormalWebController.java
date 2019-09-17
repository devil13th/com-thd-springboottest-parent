package com.thd.springboottest.normalweb.controller;

import com.thd.springboottest.normalweb.dto.MyBean;
import com.thd.springboottest.normalweb.service.MyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author devil13th
 **/
@Controller
@RequestMapping(value="/normalWeb")
public class NormalWebController {
    @Autowired
    private MyService myService;

    @RequestMapping(value="/hello")
    @ResponseBody
    //http://127.0.0.1:8899/thd/normalWeb/hello
    public String hello(){
        System.out.println("hello");
        myService.hello();
        return "hello ";
    }

    @RequestMapping(value="/hello/{name}")
    @ResponseBody
    //http://127.0.0.1:8899/thd/normalWeb/hello/devil13th
    public String hello2(@PathVariable String name){

        System.out.println("hello2");
        String str = myService.hello(name);
        return str;
    }

    @RequestMapping(value="/hello/{name}/{welcome}")
    @ResponseBody
    //http://127.0.0.1:8899/thd/normalWeb/hello/devil/welcome
    public String hello3(@PathVariable String name,@PathVariable String welcome){

        System.out.println("hello3");
        String str = myService.hello(name,welcome);
        return str;
    }

    @RequestMapping(value="/helloSb/{name}/{welcome}")
    @ResponseBody
    //http://127.0.0.1:8899/thd/normalWeb/helloSb/zhangsan/heihei
    public MyBean hello4(@PathVariable String name,@PathVariable String welcome){

        System.out.println("hello4");
        MyBean mb = myService.helloSb(name,welcome);
        return mb;
    }
}
