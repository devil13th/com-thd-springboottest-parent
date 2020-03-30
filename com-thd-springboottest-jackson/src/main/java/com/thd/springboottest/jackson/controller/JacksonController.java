package com.thd.springboottest.jackson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thd.springboottest.jackson.bean.JacksonBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.sql.Timestamp;
import java.util.Date;

@Controller
@RequestMapping("/jackson")
public class JacksonController {
    @RequestMapping("/output")
    // url : http://127.0.0.1:8899/thd/jackson/output
    public ResponseEntity output() throws Exception{
        JacksonBean jb = new JacksonBean();
        jb.setName("devil13th");
        jb.setBirthday(new Date());
        jb.setDate1(new Date());
        jb.setDate2(new Date());
        jb.setCreateTime(new Timestamp(System.currentTimeMillis()));
        ObjectMapper mapper=new ObjectMapper();//先创建objmapper的对象
        String str = mapper.writeValueAsString(jb);

        System.out.println(str);
        return ResponseEntity.ok( str);
    }

}
