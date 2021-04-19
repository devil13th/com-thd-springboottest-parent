package com.thd.springboottest.elasticsearch.resthighlevelclient.service;

import com.thd.springboottest.elasticsearch.resthighlevelclient.util.EsUtils;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.service.ArticleServiceImpl
 *
 * @author: wanglei62
 * @DATE: 2021/4/19 16:45
 **/
@Component
public class ArticleServiceImpl implements ArticleService {

    private final RequestOptions options = RequestOptions.DEFAULT;

    public RestHighLevelClient getEsClient(){
        return EsUtils.getEsClient();
    };


}
