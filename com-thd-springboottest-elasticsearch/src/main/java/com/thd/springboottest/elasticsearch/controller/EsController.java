package com.thd.springboottest.elasticsearch.controller;



import com.thd.springboottest.elasticsearch.dao.EsRepository;
import com.thd.springboottest.elasticsearch.vo.Student;
import org.elasticsearch.index.query.QueryBuilders;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;


/**
 * com.thd.springboottest.elasticsearch.controller.ESController
 *
 * @author: wanglei62
 * @DATE: 2020/5/25 10:16
 **/
@Controller
@RequestMapping("/es")
public class EsController {
//    @Autowired
//    private EsService esService;
    @Autowired
    private EsRepository esRepository;
//    @Autowired
//    private ElasticsearchRestTemplate elasticsearchTemplate;

    @RequestMapping("/test")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/test
    private ResponseEntity test(){
        System.out.println("SUCCESS");
        System.out.println(esRepository);
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/insert")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/insert
    private ResponseEntity insert(){
        Student student = new Student();
        student.setId(UUID.randomUUID().toString()).setAge(10).setName("devil13th").setScore(72.5 ).setInfo("大王派我来巡山");
        esRepository.save(student);
        return ResponseEntity.ok("SUCCESS");
    }

    @RequestMapping("/search")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/search
    private ResponseEntity search(){
        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.matchQuery("id","b06743d2-5ad2-4f18-bda8-57e330a4d8cf"));
//        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.matchAllQuery());
        Page<Student> list = esRepository.search(searchQuery);
        return ResponseEntity.ok(list);
    }

    @RequestMapping("/searchAll")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/searchAll
    private ResponseEntity searchAll(){
//        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.matchQuery("id","b06743d2-5ad2-4f18-bda8-57e330a4d8cf"));
        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.matchAllQuery());
        Page<Student> list = esRepository.search(searchQuery);
        return ResponseEntity.ok(list);
    }

    @RequestMapping("/fullindex")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/fullindex
    private ResponseEntity fullindex() throws Exception{

        File folder = new File("D:\\devil13th\\github\\com-thd-springboottest-parent\\com-thd-springboottest-elasticsearch\\src\\main\\java\\com\\thd\\springboottest\\elasticsearch\\article\\fullindex\\");
        Arrays.stream(folder.list()).forEach(fileName -> {
            try{
                File f = new File(folder,fileName);
                FileReader fr = new FileReader(f);
                BufferedReader reader = new BufferedReader(fr);
                String line = null;
                StringBuffer sb = new StringBuffer("");
                while((line = reader.readLine()) != null){
                    System.out.println(line);
                    sb.append(line);
                }

                //System.out.println(sb.toString());
                reader.close();

                Student student = new Student();
                student.setId(UUID.randomUUID().toString());
                student.setName(fileName);
                student.setInfo(sb.toString());
                this.esRepository.save(student);
                System.out.println("===============================================================================");
            }catch(Exception e){
                e.printStackTrace();
            }

        });




        return ResponseEntity.ok("SUCCESS");
    }



    @RequestMapping("/searchByIndex/{kw}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/searchByIndex/美国
    private ResponseEntity searchByIndex(@PathVariable String kw){

        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.matchQuery("info",kw));
//        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.matchAllQuery());
        Page<Student> list = esRepository.search(searchQuery);
        return ResponseEntity.ok(list);
    }










    /*
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
//        QueryPage queryPage = new QueryPage();
//        queryPage.setCurrent(1).setSize(5);
//        Page<Student> list = esService.search("二狗2", queryPage);
//        list.forEach(System.out::println);
//        return ResponseEntity.ok(list);


        SearchQuery searchQuery = new NativeSearchQuery(QueryBuilders.matchAllQuery());
        List<Student> users = elasticsearchTemplate.queryForList(searchQuery, Student.class);
        System.out.println(users.toString());
        return ResponseEntity.ok(users);
    }*/
}
