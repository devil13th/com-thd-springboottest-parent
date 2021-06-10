package com.thd.springboottest.elasticsearch.resthighlevelclient.service;


import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Book;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.OneByOneObject;
import org.elasticsearch.action.search.SearchResponse;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.service.TestService
 *
 * @author: wanglei62
 * @DATE: 2021/6/7 6:38
 **/
public interface TestService {

    // ========================= 1对1对象的使用 ============================ //
    /**
     * 创建嵌套结构的索引
     */
    public void createObjectIndex() throws Exception;
    /**
     * 插入数据
     */
    public void indexOneByOneObject(OneByOneObject book) throws Exception;
    /**
     * 查询
     */
    public SearchResponse searchOneByOneObject(String keyWords) throws  Exception;


    // ========================= 嵌套的使用 ============================ //

    /**
     * 创建嵌套结构的索引
     */
    public void createNestIndex() throws Exception;

    /**
     * 插入数据
     */
    public void indexNest(Book book)  throws Exception;
    /**
     * 查询
     */
    public SearchResponse searchNest(String keyWords) throws  Exception;
}
