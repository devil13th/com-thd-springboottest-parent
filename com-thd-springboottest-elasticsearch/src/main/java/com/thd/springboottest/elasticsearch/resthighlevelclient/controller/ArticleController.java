package com.thd.springboottest.elasticsearch.resthighlevelclient.controller;

import com.thd.springboottest.elasticsearch.resthighlevelclient.service.ArticleService;
import com.thd.springboottest.elasticsearch.resthighlevelclient.util.EsUtils;
import com.thd.springboottest.elasticsearch.resthighlevelclient.util.MyFileUtils;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Article;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.client.RequestOptions;
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
    private RestHighLevelClient esClient = EsUtils.getEsClient();

    @Autowired
    private ArticleService articleService;

    // url : http://127.0.0.1:8899/thd/es/article/test
    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity test(){
        System.out.println("test");


        return ResponseEntity.ok("SUCCESS");
    }

    // ================================== 索引操作 ======================================= //

    /**
     * 索引操作
     * @return
     */
    @RequestMapping("/indexExample")
    @ResponseBody
    // http://127.0.0.1:8899/thd/es/article/indexExample
    public ResponseEntity indexExample(){

//        // 查询索引是否存在
//        boolean r = this.articleService.checkIndex("article");
//        return ResponseEntity.ok(r);

//        // 查询索引列表
//        List<String> indexList = this.articleService.queryIndex("*");
//        return ResponseEntity.ok(indexList);

//        // 查询某个索引
//        List<String> indexList = this.articleService.queryIndex("article");
//        return ResponseEntity.ok(indexList);

//        // 查询某个索引mapping详细信息
//        List<Map<String,Object>> r = this.articleService.indexMappingInfo("article");
//        return ResponseEntity.ok(r);

        //查询某个索引setting详细信息
        Map<String,String> r = this.articleService.indexSettingInfo("article");
        return ResponseEntity.ok(r);
    };


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
     * 查询索引列表
     */
    @RequestMapping("/queryIndex/{indexName}")
    @ResponseBody
    // http://127.0.0.1:8899/thd/es/article/queryIndex/* (查询所有索引)
    public ResponseEntity queryIndex (@PathVariable String indexName){
        List<String> r = this.articleService.queryIndex(indexName);
        return ResponseEntity.ok(r);
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
    public boolean deleteIndex(@PathVariable String indexName){
        boolean r = this.articleService.deleteIndex(indexName);
        return r;
    };

    // ================================== 文档操作 ======================================= //


    @RequestMapping("/testDoc")
    @ResponseBody
    // http://127.0.0.1:8899/thd/es/article/testDoc
    public ResponseEntity testDoc() throws Exception{

        // 根据id获取doc
        GetRequest getRequest = new GetRequest("article","315e6503e3574847bdedbf13d1e82fbd");
        GetResponse getResponse = esClient.get(getRequest, RequestOptions.DEFAULT);
        return ResponseEntity.ok(getResponse);

//        // 根据id删除doc
//        DeleteRequest deleteRequest = new DeleteRequest("article","e518f6ea08b04506a7b870273f45d72a");
//        DeleteResponse deleteResponse = esClient.delete(deleteRequest,RequestOptions.DEFAULT);
//        return ResponseEntity.ok(deleteResponse);
    }

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


    // ================================== 文档查询 ======================================= //





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
