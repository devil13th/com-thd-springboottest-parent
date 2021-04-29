package com.thd.springboottest.elasticsearch.resthighlevelclient.controller;

import com.thd.springboottest.elasticsearch.resthighlevelclient.service.ArticleService;
import com.thd.springboottest.elasticsearch.resthighlevelclient.util.MyFileUtils;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Article;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.controller.ArticleController
 *
 * @author: wanglei62
 * @DATE: 2021/4/19 16:45
 **/
@Controller
@RequestMapping("/es/article")
public class ArticleController {


    @Autowired
    private ArticleService articleService;

    // url : http://127.0.0.1:8899/thd/es/article/test
    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity test(){
        System.out.println("test");


        return ResponseEntity.ok("SUCCESS");
    }


    /**
     * 判断索引是否存在
     */
    @RequestMapping("/checkIndex/{indexName}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/article/checkIndex/movies
    public boolean checkIndex (@PathVariable String indexName){
        return this.articleService.checkIndex(indexName);
    };


    /**
     * 创建索引
     */
    @RequestMapping("/createIndex")
    @ResponseBody
    // http://127.0.0.1:8899/thd/es/article/createIndex
    public boolean createIndex (){
        boolean r = this.articleService.createIndex();
        return r;
    };

    /**
     * 删除索引
     */
    @RequestMapping("/deleteIndex/{indexName}")
    @ResponseBody
    public boolean deleteIndex(String indexName){
        boolean r = this.articleService.deleteIndex(indexName);
        return r;
    };



    @RequestMapping("/index")
    @ResponseBody
    // http://127.0.0.1:8899/thd/es/article/index
    public ResponseEntity index(){

        String folderPath = "D:\\devil13th\\Thirdteendevil\\Thirdteendevil\\resource\\tec";
        this.articleService.indexAllFile(folderPath);
//        File folder = new File(folderPath);
//        String[] folderList = folder.list();
//        Stream.of(folderList).forEach( item -> {
//
//            File f = new File(folder.getAbsolutePath() + "\\" + item);
//            if(f.isFile()) {
//                String content = MyFileUtils.readFile(f.getAbsolutePath());
//                Article art = new Article();
//                art.setTitle(f.getName());
//                art.setContent(content);
//                art.setPath(f.getAbsolutePath());
//                art.setClassify("JAVA");
//                boolean r = this.articleService.index(art);
//            }
//        });
        return ResponseEntity.ok("SUCCESS");
    };


    @RequestMapping("/indexTest")
    @ResponseBody
    // http://127.0.0.1:8899/thd/es/article/indexTest
    public ResponseEntity indexTest(){
        Article art = new Article();
        art.setTitle("我爱北京天安门");
        art.setContent("我爱天安门北京");
        art.setClassify("我爱天安门北京");
        art.setPath("我爱天安门北京");
        this.articleService.index(art);
        return ResponseEntity.ok("SUCCESS");
    };



    @RequestMapping("/search/{keywords}")
    @ResponseBody
    // http://127.0.0.1:8899/thd/es/article/search
    public ResponseEntity search(@PathVariable String keywords){
        List<Article> list = this.articleService.search(keywords);
        return ResponseEntity.ok(list);
    };


    @RequestMapping("/searchById/{id}")
    @ResponseBody
    // http://127.0.0.1:8899/thd/es/article/searchById
    public ResponseEntity searchById(@PathVariable String id){
        List<Article> list = this.articleService.searchById(id);
        return ResponseEntity.ok(list);
    };




}
