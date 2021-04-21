package com.thd.springboottest.elasticsearch.resthighlevelclient.service;

import com.thd.springboottest.elasticsearch.resthighlevelclient.util.EsUtils;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.service.EsIndexServiceImpl
 *
 * @author: wanglei62
 * @DATE: 2021/4/19 17:26
 **/
@Component
public class EsIndexServiceImpl implements EsIndexService{
    @Autowired
    private RestHighLevelClient esClient;

    private final RequestOptions options = RequestOptions.DEFAULT;

    @Override
    public boolean checkIndex(String index) {
        try {
            return esClient.indices().exists(new GetIndexRequest(index), options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE ;
    }

    /**
     * 创建索引
     */
    public boolean createIndex (String indexName ,Map<String, Object> columnMap){
        try {
            if(!checkIndex(indexName)){
                CreateIndexRequest request = new CreateIndexRequest(indexName);

                request.source("{" +
                        "\"settings\": {" +
                            "\"number_of_shards\": 3," +
                            "\"number_of_replicas\": 2" +
                        "}," +
                        "\"mappings\": {" +
                            "\"properties\": {" +
                                "\"title\": {\"type\": \"keyword\"}" +
                            "}" +
                        "}," +
                        "\"aliases\": {\"blog_alias_javaboy\": {}}}", XContentType.JSON);
                request.mapping()
                request.
                if (columnMap != null && columnMap.size()>0) {
                    Map<String, Object> source = new HashMap<>();
                    source.put("properties", columnMap);
                    request.mapping(source);
                }
                esClient.indices().create(request, options);
                return Boolean.TRUE ;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }

    /**
     * 删除索引
     */
    public boolean deleteIndex(String indexName) {
        try {
            if(checkIndex(indexName)){
                DeleteIndexRequest request = new DeleteIndexRequest(indexName);
                AcknowledgedResponse response = esClient.indices().delete(request, options);
                return response.isAcknowledged();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return Boolean.FALSE;
    }
}
