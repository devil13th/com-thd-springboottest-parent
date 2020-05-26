package com.thd.springboottest.elasticsearch.controller;

import com.thd.springboottest.elasticsearch.service.EsService;
import com.thd.springboottest.elasticsearch.vo.QueryPage;
import com.thd.springboottest.elasticsearch.vo.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

/**
 * com.thd.springboottest.elasticsearch.controller.ESController
 *
 * @author: wanglei62
 * @DATE: 2020/5/25 10:16
 **/
@Controller
@RequestMapping("/es")
public class EsController {
    @Autowired
    private EsService esService;
    @Autowired
    private ElasticsearchRestTemplate elasticsearchTemplate;

    @RequestMapping("/test")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/test
    private ResponseEntity test(){
        System.out.println("SUCCESS");
        return ResponseEntity.ok("SUCCESS");
    }
    @RequestMapping("/insert")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/insert
    private ResponseEntity insert(){
        List<Student> students = new ArrayList<>();
        for (int i = 10; i <= 12; i++) {
            Student student = new Student();
            student.setId(i + "").setAge(10 + i).setName("王二狗" + i).setScore(72.5 + i).setInfo("大王派我来巡山" + i);
            students.add(student);
        }
        esService.addAll(students);
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/search")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/search
    private ResponseEntity search(){
        QueryPage queryPage = new QueryPage();
        queryPage.setCurrent(1).setSize(5);
        Page<Student> list = esService.search("二狗2", queryPage);
        list.forEach(System.out::println);
        return ResponseEntity.ok(list);
    }
}
