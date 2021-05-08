package com.thd.springboottest.elasticsearch.resthighlevelclient.service;

import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Article;
import org.elasticsearch.client.RestHighLevelClient;

import java.util.List;
import java.util.Map;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.service.ArticleService
 *
 * @author: wanglei62
 * @DATE: 2021/4/19 16:45
 **/
public interface ArticleService {
//    public RestHighLevelClient getEsClient();

    /**
     * 查询索引
     * @param indexName
     * @return
     */
    public List<String> queryIndex(String indexName);

    /**
     * 查询索引mapping详细信息
     * @param indexName
     * @return
     */
    public List<Map<String, Object>> indexMappingInfo(String indexName);

    /**
     * 查询索引setting详细信息
     * @param indexName
     * @return
     */
    public Map<String, String> indexSettingInfo(String indexName);

    /**
     * 删除索引
     */
    public boolean deleteIndex(String indexName);
    /**
     * 判断索引是否存在
     */
    public boolean checkIndex (String index);
    /**
     * 创建索引
     */
    public boolean createIndex ();

    /**
     * 索引文档
     */
    public boolean index(Article article);
    public void indexAllFile(String path);
    public List<Article> search(String keywords);
    public List<Article> searchById(String id);

}
