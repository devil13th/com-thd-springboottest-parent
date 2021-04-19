package com.thd.springboottest.elasticsearch.resthighlevelclient.util;

import org.apache.http.HttpHost;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.graph.Connection;
import org.elasticsearch.client.transport.TransportClient;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.util.EsUtil
 *
 * @author: wanglei62
 * @DATE: 2021/4/19 17:12
 **/
public class EsUtils {
    static String ip = "localhost";
    static int port = 9200;
    static RestHighLevelClient restHighLevelClient = null;
    static TransportClient client = null;

    public static  RestHighLevelClient getEsClient(){
        RestHighLevelClient restHighLevelClient = new RestHighLevelClient(
            RestClient.builder(new HttpHost(ip,port,"http"))
        );
        return restHighLevelClient;
    }


}
