package com.thd.springboottest.elasticsearch.resthighlevelclient.controller;

import com.thd.springboottest.elasticsearch.resthighlevelclient.service.ArticleService;
import com.thd.springboottest.elasticsearch.resthighlevelclient.service.TestService;
import com.thd.springboottest.elasticsearch.resthighlevelclient.util.EsUtils;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Author;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Book;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Comments;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.OneByOneObject;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.controller.TestController
 *
 * @author: wanglei62
 * @DATE: 2021/6/7 6:51
 **/
@Controller
@RequestMapping("/es/test")
public class TestController {

    @Autowired
    private RestHighLevelClient esClient = EsUtils.getEsClient();

    @Autowired
    private TestService testService;

    // url : http://127.0.0.1:8899/thd/es/test/test
    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity test(){
        System.out.println("test");
        return ResponseEntity.ok("SUCCESS");
    }


    // =======================  1对1对象的使用 ========================= //

    // 创建嵌套索引
    @RequestMapping("/createObjectIndex")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/test/createObjectIndex
    public ResponseEntity createObjectIndex() throws Exception{
        this.testService.createObjectIndex();
        return ResponseEntity.ok("SUCCESS");
    }



    @RequestMapping("/indexObject")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/test/indexObject
    public ResponseEntity indexObject() throws Exception{
        OneByOneObject book1 = new OneByOneObject();

        book1.setContent("工厂模式 适配模式 模板模式 命令模式 策略模式 组合模式");
        book1.setKeyword("java 设计模式");
        book1.setTitle("java 设计模式");


        Author author1 = new Author();
        author1.setName("张三");
        author1.setAge(5l);
        author1.setDesc("张三,毕业于北京大学");

        book1.setAuthor(author1);


        // ---------------------------

        OneByOneObject book2 = new OneByOneObject();

        book2.setContent("react ");
        book2.setKeyword("react mvvn javascript框架");
        book2.setTitle("react in action");


        Author author2 = new Author();
        author2.setName("李四");
        author2.setAge(15l);
        author2.setDesc("李四,毕业于清华大学");

        book1.setAuthor(author1);
        book2.setAuthor(author2);


        this.testService.indexOneByOneObject(book1);
        this.testService.indexOneByOneObject(book2);




        return ResponseEntity.ok("SUCCESS");
    }



    // 创建嵌套索引
    @RequestMapping("/searchOneByOneObject")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/test/searchOneByOneObject?keyWords=xxx
    public ResponseEntity searchOneByOneObject(@RequestParam String keyWords) throws Exception{
        SearchResponse searchResponse = this.testService.searchOneByOneObject(keyWords);
        return ResponseEntity.ok(searchResponse);
    }

    // =======================  嵌套的使用 ========================= //

    // 创建嵌套索引
    @RequestMapping("/createNestIndex")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/test/createNestIndex
    public ResponseEntity createNestIndex() throws Exception{
        this.testService.createNestIndex();
        return ResponseEntity.ok("SUCCESS");
    }


    @RequestMapping("/indexNest")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/test/indexNest
    public ResponseEntity indexNest() throws Exception{
        Book book1 = new Book();
        book1.setAuthor("张三");
        book1.setContent("工厂模式 适配模式 模板模式 命令模式 策略模式 组合模式");
        book1.setKeyword("java 设计模式");

        Comments comments1 = new Comments();
        comments1.setComment("学习设计模式很好的书籍");
        comments1.setName("devil13th");
        comments1.setStars(5);
        comments1.setDate(new Date());

        Comments comments2 = new Comments();
        comments2.setComment("工厂模式没有看的很明白");
        comments2.setName("devil");
        comments2.setStars(5);
        comments2.setDate(new Date());

        List<Comments> commentsList1 = new ArrayList<Comments>();
        commentsList1.add(comments1);
        commentsList1.add(comments2);
        book1.setComments(commentsList1);

        // ---------------------------

        Book book2 = new Book();
        book2.setAuthor("李四");
        book2.setContent("react mvvn JS前端框架");
        book2.setKeyword("react 深入浅出");

        Comments comments21 = new Comments();
        comments21.setComment("相对react领域不错的书籍");
        comments21.setName("thd");
        comments21.setStars(5);
        comments21.setDate(new Date());

        Comments comments22 = new Comments();
        comments22.setComment("有没有介绍vue的");
        comments22.setName("Thirdteendevil");
        comments22.setStars(5);
        comments22.setDate(new Date());

        List<Comments> commentsList2 = new ArrayList<Comments>();
        commentsList2.add(comments21);
        commentsList2.add(comments22);
        book2.setComments(commentsList2);



        this.testService.indexNest(book1);
        this.testService.indexNest(book2);




        return ResponseEntity.ok("SUCCESS");
    }



    // 创建嵌套索引
    @RequestMapping("/searchNest")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/test/searchNest?keyWords=xxx
    public ResponseEntity searchNest(@RequestParam String keyWords) throws Exception{
        SearchResponse searchResponse = this.testService.searchNest(keyWords);
        return ResponseEntity.ok(searchResponse);
    }



}
