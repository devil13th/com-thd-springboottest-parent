package com.thd.springboottest.jackson.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.thd.springboottest.jackson.bean.JacksonBean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

@Controller
@RequestMapping("/jackson")
public class JacksonController {


    /**
     * java对象转json
     * @return
     * @throws Exception
     */
    @RequestMapping("/testDateOutput01")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jackson/testDateOutput01
    public ResponseEntity testDateOutput01() throws Exception{
        JacksonBean jb = new JacksonBean();
        jb.setName("devil13th");
        jb.setBirthday(new Date());
        jb.setDate1(new Date());
        jb.setDate2(new Date());
        jb.setCreateTime(new Timestamp(System.currentTimeMillis()));
        //先创建objmapper的对象
        ObjectMapper mapper=new ObjectMapper();
        // 序列化
        String str = mapper.writeValueAsString(jb);
        System.out.println(str);
        return ResponseEntity.ok( str);
    }


    /**
     * java对象转json日期属性的处理
     * 优先类中的注释@Annotation处理日期类型属性,然后再是配置mapper.setDateFormat
     * @return
     * @throws Exception
     */

    @RequestMapping("/testDateOutput02")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jackson/testDateOutput02
    public ResponseEntity testDateOutput02() throws Exception{
        JacksonBean jb = new JacksonBean();
        jb.setName("devil13th");
        jb.setBirthday(new Date());
        jb.setDate1(new Date());
        jb.setDate2(new Date());
        jb.setCreateTime(new Timestamp(System.currentTimeMillis()));
        ObjectMapper mapper=new ObjectMapper();//先创建objmapper的对象
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 设置日期序列化的格式, 优先级低于Bean属性的@Annotation注释
        mapper.setDateFormat(formatter);
        String str = mapper.writeValueAsString(jb);
        System.out.println(str);
        return ResponseEntity.ok( str);
    }

    /**
     * 反序列化例子
     */
    @RequestMapping("testDeSerializa")
    // url : http://127.0.0.1:8899/thd/jackson/testDeSerializa
    public ResponseEntity testDeSerializa() throws Exception{
        ObjectMapper mapper = new ObjectMapper();
        String json = "{\"name\":\"devil13th\",\"birthday\":1585613995021,\"date1\":\"2020-03-31 08:19:55\",\"date2\":\"2020-03-31\",\"createTime\":1585613995021}";
        JacksonBean jb = mapper.readValue(json, JacksonBean.class);
        System.out.println(jb);
        return ResponseEntity.ok(jb);
    }


    @RequestMapping("/testInput")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jackson/testInput
    public ResponseEntity testInput(@RequestBody JacksonBean jb) throws Exception{
        System.out.println(jb);
        return ResponseEntity.ok( jb);
    }

}
