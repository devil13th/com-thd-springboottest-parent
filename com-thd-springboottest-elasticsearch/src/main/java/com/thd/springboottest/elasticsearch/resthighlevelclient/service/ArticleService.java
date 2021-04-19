package com.thd.springboottest.elasticsearch.resthighlevelclient.service;

import org.elasticsearch.client.RestHighLevelClient;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.service.ArticleService
 *
 * @author: wanglei62
 * @DATE: 2021/4/19 16:45
 **/
public interface ArticleService {
    public RestHighLevelClient getEsClient();


}
