package com.thd.springboottest.elasticsearch.resthighlevelclient.service;

import com.thd.springboottest.elasticsearch.resthighlevelclient.util.EsUtils;
import com.thd.springboottest.elasticsearch.resthighlevelclient.util.MyFileUtils;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Article;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsRequest;
import org.elasticsearch.action.admin.indices.settings.get.GetSettingsResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.*;
import org.elasticsearch.cluster.metadata.MappingMetaData;
import org.elasticsearch.common.collect.ImmutableOpenMap;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.Operator;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.sort.FieldSortBuilder;
import org.elasticsearch.search.sort.ScoreSortBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.service.ArticleServiceImpl
 *
 * @author: wanglei62
 * @DATE: 2021/4/19 16:45
 **/
@Component
public class ArticleServiceImpl implements ArticleService {




    @Autowired
    private RestHighLevelClient esClient = EsUtils.getEsClient();

    private final RequestOptions options = RequestOptions.DEFAULT;
    // ================================== 索引操作 ======================================= //
    @Override
    public boolean checkIndex(String index) {
        try {
            return esClient.indices().exists(new GetIndexRequest(index), options);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Boolean.FALSE ;
    }


    @Override
    public List<String> queryIndex(String indexName){
        // 查询索引  如果查询所有索引则设置indexName为'*'
        GetIndexRequest request = new GetIndexRequest(indexName);
        GetIndexResponse response = null;
        try {
            response = esClient.indices().get(request, RequestOptions.DEFAULT);
            String[] indices = response.getIndices();
            return Arrays.asList(indices);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;

    }

    @Override
    public List<Map<String, Object>> indexMappingInfo(String indexName) {
        GetMappingsRequest getMappings = new GetMappingsRequest().indices("article");
        try {
           GetMappingsResponse getMappingResponse = esClient.indices().getMapping(getMappings,options);
            //处理数据
            Map<String, MappingMetaData> allMappings = getMappingResponse.mappings();
            System.out.println(allMappings);
            List<Map<String, Object>> mapList = new ArrayList<>();
            for (Map.Entry<String, MappingMetaData> indexValue : allMappings.entrySet()) {
                Map<String, Object> mapping = indexValue.getValue().sourceAsMap();

                Iterator<Map.Entry<String, Object>> entries = mapping.entrySet().iterator();
                entries.forEachRemaining(stringObjectEntry -> {
                    if (stringObjectEntry.getKey().equals("properties")) {
                        Map<String, Object> value = (Map<String, Object>) stringObjectEntry.getValue();
                        for (Map.Entry<String, Object> ObjectEntry : value.entrySet()) {
                            Map<String, Object> map = new HashMap<>();
                            String key = ObjectEntry.getKey();
                            Map<String, Object> value1 = (Map<String, Object>) ObjectEntry.getValue();
                            map.put(key, value1.get("type"));
                            mapList.add(map);
                        }
                    }
                });
            }

            return mapList;

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Map<String, String> indexSettingInfo(String indexName){
        GetSettingsRequest getSettingsRequest = new GetSettingsRequest().indices(indexName);
        try {
            GetSettingsResponse getSettingsResponse = esClient.indices().getSettings(getSettingsRequest,options);
            ImmutableOpenMap<String, Settings> settings =  getSettingsResponse.getIndexToSettings();
            Map<String,String> st = new HashMap<String,String>();
            settings.forEach( item -> {
                Settings s = item.value;
                s.keySet().forEach(kst -> {
                    st.put(kst,s.get(kst));
                });
//                st.put(item.key,s.);
            });
            return st;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };

    /**
     * 创建索引
     */
    public boolean createIndex (){
        try {
            if(!checkIndex("article")){
                // 1、创建 创建索引request 参数：索引名java
                CreateIndexRequest request = new CreateIndexRequest("article");

                // 2、设置索引setting
//                Settings.Builder builder = Settings.builder()
//                    .put("index.number_of_shards",3) // 分片数量
//                    .put("index.number_of_replicas",2) ;// 每个分片的副本数量
//                request.settings(builder);

                // 使用json设置索引内容
                request.source("{\n" +
                        "    \"settings\" : {\n" +
                        "        \"number_of_shards\" : 1,\n" +
                        "        \"number_of_replicas\" : 0\n" +
                        "    },\n" +
                        "    \"mappings\" : {\n" +
                        "        \"properties\" : {\n" +
                        "            \"title\" : { \"type\" : \"text\" },\n" +
                        "            \"classify\" : { \"type\" : \"keyword\" },\n" +
                        "            \"path\" : { \"type\" : \"keyword\" },\n" +
                        "            \"content\" : { \"type\" : \"text\" }\n" +
                        "        }\n" +
                        "    },\n" +
                        "    \"aliases\" : {\n" +
                        "        \"article_\" : {}\n" +
                        "    }\n" +
                        "}", XContentType.JSON);

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



    /**
     * 索引文档
     */
    public boolean index(Article article) {
        IndexRequest request = new IndexRequest("article");
        String id = UUID.randomUUID().toString().replace("-","");
        request.id(id);

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("title", article.getTitle());
        jsonMap.put("content", article.getContent());
        jsonMap.put("classify", article.getClassify());
        jsonMap.put("path", article.getPath());
        request.source(jsonMap);

        try {
            IndexResponse is = esClient.index(request,options);

            System.out.println(is);
        } catch (IOException e) {
            throw new RuntimeException(" create index error ... ");
        }
        return true;
    };



    /**
     * 索引文档
     */
    public void indexAllFile(String path) {
        String folderPath = path;
        File folder = new File(folderPath);
        String[] folderList = folder.list();
        String[] classifyArrays = new String[]{"REACT","JS","JVM","VUE","JAVA"};
        Stream.of(folderList).forEach( item -> {

            File f = new File(folder.getAbsolutePath() + "\\" + item);
            if(f.isFile()) {
                if(f.getName().toLowerCase().indexOf(".html") != -1
                ||f.getName().toLowerCase().indexOf(".htm") != -1
                ||f.getName().toLowerCase().indexOf(".md") != -1
                ) {
                    System.out.println(f.getAbsolutePath());
                    if(f.length() <= 1 * 1024 * 1024) {
                        String content = MyFileUtils.readFile(f.getAbsolutePath());
                        Article art = new Article();
                        art.setTitle(f.getName());
                        art.setContent(content);
                        art.setPath(f.getAbsolutePath());

                        int c = new Random().nextInt(3);
                        art.setClassify(classifyArrays[c]);
                        boolean r = this.index(art);
                    }
                }
            }else{
                this.indexAllFile(f.getAbsolutePath());
            }
        });
    };




    public List<Article> search(String keywords){
        SearchRequest sr = new SearchRequest("article");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();


        // 返回的字段集合
        String[] includeFields = new String[] {"title","path"};
        searchSourceBuilder.fetchSource(includeFields,null);


        // ================== 下面是各种查询条件 ==================

        searchSourceBuilder.query(QueryBuilders.matchQuery("content",keywords));
        // 单字段查询
//        searchSourceBuilder.query(QueryBuilders.termQuery("content",keywords));

        // 必须匹配到每一个词
//        searchSourceBuilder.query(QueryBuilders.matchQuery("content","java jvm thread pool").operator(Operator.AND));

        // 多个条件或者
//        searchSourceBuilder.query(QueryBuilders.boolQuery()
//                .should(QueryBuilders.matchQuery("content","java jvm thread pool").operator(Operator.AND))
//                .should(QueryBuilders.matchQuery("title","thread"))
//        );
        // 多个条件并且
//        searchSourceBuilder.query(QueryBuilders.boolQuery()
//                .must(QueryBuilders.matchQuery("content","jvm thread pool").operator(Operator.OR))
//                .must(QueryBuilders.matchQuery("classify","JVM"))
//        );

        // 模拟sql and  or 优先级
//        searchSourceBuilder.query(
//                QueryBuilders.boolQuery()
//                .should(
//                        QueryBuilders.boolQuery()
//                                .must(QueryBuilders.matchQuery("content","jvm").operator(Operator.OR))
//                                .must(QueryBuilders.matchQuery("classify","JVM"))
//                )
//                .should(
//                        QueryBuilders.boolQuery()
//                                .must(QueryBuilders.matchQuery("content","java").operator(Operator.OR))
//                                .must(QueryBuilders.matchQuery("classify","REACT"))
//                )
//        );

//        searchSourceBuilder.query(QueryBuilders.matchQuery("classify","JVM"));

//        searchSourceBuilder.query(QueryBuilders.termQuery("title","我爱北京天安门"));
//        searchSourceBuilder.query(QueryBuilders.matchQuery("title","我爱北京天安门"));
//        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("title","北京 天安门"));
//        searchSourceBuilder.query(QueryBuilders.matchPhraseQuery("content","天安门 北京"));
//        searchSourceBuilder.query(QueryBuilders.queryStringQuery("天安门 北京").field("content"));


        // 分页
        searchSourceBuilder.from(0);
        searchSourceBuilder.size(100);

        searchSourceBuilder.sort(new ScoreSortBuilder().order(SortOrder.DESC));// 默认得分倒叙
        //searchSourceBuilder.sort(new FieldSortBuilder("_uid").order(SortOrder.ASC));//也可以按_id字段进行升序排序



        // 高亮
        HighlightBuilder highlightBuilder = new HighlightBuilder();

        highlightBuilder.preTags("</bbb>");
        highlightBuilder.postTags("</bbb>");


        HighlightBuilder.Field highlightTitle = new HighlightBuilder.Field("title");
        highlightTitle.highlighterType("unified");
        highlightBuilder.field(highlightTitle);

        HighlightBuilder.Field highlightContent = new HighlightBuilder.Field("content");
        highlightBuilder.field(highlightContent);

        searchSourceBuilder.highlighter(highlightBuilder);


        sr.source(searchSourceBuilder);
        System.out.println(sr.source().toString());
        try {
            SearchResponse searchResponse = esClient.search(sr,options);
            System.out.println(searchResponse.toString());
            SearchHit[] r = searchResponse.getHits().getHits();
            List<Article> l = Stream.of(r).map(item -> {
                Article arc = new Article();
                Map arcMap = item.getSourceAsMap();
                arc.setId(item.getId());
                arc.setPath(null == arcMap.get("path") ? null : arcMap.get("path").toString());
                arc.setTitle(null == arcMap.get("title") ? null : arcMap.get("title").toString());
                arc.setContent(null == arcMap.get("content") ? null : arcMap.get("content").toString());
                arc.setClassify(null == arcMap.get("classify") ? null : arcMap.get("classify").toString());
                arc.setHighLight(item.getHighlightFields().get("content").toString());
                return arc;
            }).collect(Collectors.toList());
            return l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };

    public List<Article> searchById(String id){
        SearchRequest sr = new SearchRequest("article");

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(QueryBuilders.idsQuery().addIds(id.split(",")));

        sr.source(searchSourceBuilder);
        try {
            SearchResponse res = esClient.search(sr,options);
            SearchHit[] r = res.getHits().getHits();
            List<Article> l = Stream.of(r).map(item -> {
                Article arc = new Article();
                Map arcMap = item.getSourceAsMap();
                arc.setId(item.getId());
                arc.setPath(arcMap.get("path").toString());
                arc.setTitle(arcMap.get("title").toString());
                return arc;
            }).collect(Collectors.toList());
            return l;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    };
}
