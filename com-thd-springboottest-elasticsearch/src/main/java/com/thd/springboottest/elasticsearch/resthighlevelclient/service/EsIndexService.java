package com.thd.springboottest.elasticsearch.resthighlevelclient.service;

import java.util.Map;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.service.EsIndexService
 *
 * @author: wanglei62
 * @DATE: 2021/4/19 17:26
 **/
public interface EsIndexService {
    /**
     * 判断索引是否存在
     */
    public boolean checkIndex (String index);

    /**
     * 创建索引
     */
    public boolean createIndex (String indexName , Map<String, Object> columnMap);

    /**
     * 删除索引
     */
    public boolean deleteIndex(String indexName);
}
