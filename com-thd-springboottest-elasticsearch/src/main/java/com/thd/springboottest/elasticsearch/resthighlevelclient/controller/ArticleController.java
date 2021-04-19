package com.thd.springboottest.elasticsearch.resthighlevelclient.controller;

import com.thd.springboottest.elasticsearch.resthighlevelclient.service.ArticleService;
import com.thd.springboottest.elasticsearch.resthighlevelclient.service.EsIndexService;
import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Map;

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
    @Autowired
    private EsIndexService esIndexServiceImpl;

    // url : http://127.0.0.1:8899/thd/es/article/test
    @RequestMapping("/test")
    @ResponseBody
    public ResponseEntity test(){
        System.out.println("test");
        System.out.println(articleService);
        System.out.println(articleService.getEsClient());

        return ResponseEntity.ok("SUCCESS");
    }


    /**
     * 判断索引是否存在
     */
    @RequestMapping("/checkIndex/{indexName}")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/es/article/checkIndex/movies
    public boolean checkIndex (@PathVariable String indexName){
        return this.esIndexServiceImpl.checkIndex(indexName);
    };


    /**
     * 创建索引
     */
    @RequestMapping("/createIndex/{indexName}")
    @ResponseBody
    public boolean createIndex (String indexName , Map<String, Object> columnMap){
        boolean r = this.esIndexServiceImpl.createIndex(indexName,columnMap);
        return r;
    };

    /**
     * 删除索引
     */
    @RequestMapping("/deleteIndex/{indexName}")
    @ResponseBody
    public boolean deleteIndex(String indexName){
        boolean r = this.esIndexServiceImpl.deleteIndex(indexName);
        return r;
    };


}
