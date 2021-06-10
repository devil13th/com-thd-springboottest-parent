package com.thd.springboottest.elasticsearch.resthighlevelclient.service;

import com.thd.springboottest.elasticsearch.resthighlevelclient.util.EsUtils;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Book;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Comments;
import com.thd.springboottest.elasticsearch.resthighlevelclient.vo.OneByOneObject;
import org.apache.lucene.util.QueryBuilder;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.common.settings.Setting;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.*;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.service.TestServiceImpl
 *
 * @author: wanglei62
 * @DATE: 2021/6/7 6:38
 **/
@Service
public class TestServiceImpl implements TestService {
    @Autowired
    private RestHighLevelClient esClient = EsUtils.getEsClient();

    private final RequestOptions options = RequestOptions.DEFAULT;



    public void createObjectIndex() throws Exception{
        CreateIndexRequest createIndexRequest  = new CreateIndexRequest("objecttest");

/*
{
    "settings": {
        "number_of_shards": 1,
        "number_of_replicas": 0
    },
    "mappings": {
        "properties": {

            "title": {
                "type": "text"
            },
            "keyword": {
                "type": "text"
            },
            "content": {
                "type": "text"
            },
            "author": {
                "properties": {
                    "name": {
                        "type": "keyword"
                    },
                    "age": {
                        "type": "long"
                    },
                    "desc":{
                        "type": "text"
                    }
                }
            }
        }
    },
    "aliases": {
        "nestTest_": {}
    }
}
 */
        String indexStr = "{\n" +
                "\"settings\": {\n" +
                "  \"number_of_shards\": 1,\n" +
                "  \"number_of_replicas\": 0\n" +
                "},\n" +
                "\"mappings\": {\n" +
                "  \"properties\": {\n" +
                "    \n" +
                "    \"title\": {\n" +
                "      \"type\": \"text\"\n" +
                "    },\n" +
                "    \"keyword\": {\n" +
                "      \"type\": \"text\"\n" +
                "    },\n" +
                "    \"content\": {\n" +
                "      \"type\": \"text\"\n" +
                "    },\n" +
                "    \"author\": {\n" +
                "      \"properties\": {\n" +
                "        \"name\": {\n" +
                "          \"type\": \"keyword\"\n" +
                "        },\n" +
                "        \"age\": {\n" +
                "          \"type\": \"long\"\n" +
                "        },\n" +
                "        \"desc\":{\n" +
                "          \"type\": \"text\"\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  }\n" +
                "},\n" +
                "\"aliases\": {\n" +
                "  \"objecttest_\": {}\n" +
                "}"+
                "}";

        System.out.println("=================================");
        System.out.println(indexStr);
        System.out.println("=================================");
        createIndexRequest.source(indexStr, XContentType.JSON);

        esClient.indices().create(createIndexRequest,options);
    };




    @Override
    public void indexOneByOneObject(OneByOneObject book) throws Exception {
        IndexRequest request = new IndexRequest("objecttest");
        String id = UUID.randomUUID().toString().replace("-","");
        request.id(id);

        Map<String, Object> jsonMap = new HashMap<>();
        jsonMap.put("title", book.getTitle());
        jsonMap.put("keyword", book.getKeyword());
        jsonMap.put("content", book.getContent());



        Map<String, Object> authMap = new HashMap<>();
        authMap.put("name", book.getAuthor().getName());
        authMap.put("age", book.getAuthor().getAge());
        authMap.put("desc", book.getAuthor().getDesc());


        jsonMap.put("author",authMap);
        request.source(jsonMap);


        this.esClient.index(request,options);
    }


    public SearchResponse searchOneByOneObject(String keyWords) throws  Exception{

        /*

        {
            "query": {
                "match": {
                    "title": "java"
                }
            }
        }


        {
            "query": {
                "term": {
                    "author.name": "李四"
                }
            }
        }


        {
            "query": {
                "match": {
                    "author.desc": "清华"
                }
            }
        }


         */
        SearchRequest searchRequest = new SearchRequest("objecttest");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();

        searchSourceBuilder.query(QueryBuilders.matchQuery("author.name",keyWords));
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchRequest);
        return this.esClient.search(searchRequest,options);
    };



    @Override
    public void createNestIndex() throws Exception{
        CreateIndexRequest createIndexRequest  = new CreateIndexRequest("nesttest");



/*
{
	"settings": {
		"number_of_shards": 1,
		"number_of_replicas": 0
	},
	"mappings": {
		"properties": {
			"author": {
				"type": "keyword"
			},
			"type": {
				"type": "text"
			},
			"keyword": {
				"type": "text"
			},
			"content": {
				"type": "text"
			},
			"comments": {
				"type": "nested",
				"properties": {
					"name": {
						"type": "keyword"
					},
					"comment": {
						"type": "text"
					},
					"stars": {
						"type": "short"
					},
					"date": {
						"type": "date"
					}
				}
			}
		}
	},
	"aliases": {
		"nestTest_": {}
	}
}
 */
        // 使用json设置索引内容
        String indexStr = "{\n" +
                "  \"settings\": {\n" +
                "    \"number_of_shards\": 1,\n" +
                "    \"number_of_replicas\": 0\n" +
                "  },\n" +
                "  \"mappings\": {\n" +
                "    \"properties\": {\n" +
                "      \"author\": {\n" +
                "        \"type\": \"keyword\"\n" +
                "      },\n" +
                "      \"title\": {\n" +
                "        \"type\": \"text\"\n" +
                "      },\n" +
                "      \"keyword\": {\n" +
                "        \"type\": \"text\"\n" +
                "      },\n" +
                "      \"content\": {\n" +
                "        \"type\": \"text\"\n" +
                "      },\n" +
                "      \"comments\": {\n" +
                "        \"type\": \"nested\",\n" +
                "        \"properties\": {\n" +
                "          \"name\": {\n" +
                "            \"type\": \"keyword\"\n" +
                "          },\n" +
                "          \"comment\": {\n" +
                "            \"type\": \"text\"\n" +
                "          },\n" +
                "          \"stars\": {\n" +
                "            \"type\": \"short\"\n" +
                "          },\n" +
                "          \"date\": {\n" +
                "            \"type\": \"date\"\n" +
                "          }\n" +
                "        }\n" +
                "      }\n" +
                "    }\n" +
                "  },\n" +
                "  \"aliases\": {\n" +
                "    \"nesttest_\": {}\n" +
                "  }\n" +
                "}";
        System.out.println("=================================");
        System.out.println(indexStr);
        System.out.println("=================================");
        createIndexRequest.source(indexStr, XContentType.JSON);

        esClient.indices().create(createIndexRequest,options);
    }


    @Override
    public void indexNest(Book book) throws Exception {
        IndexRequest request = new IndexRequest("nesttest");
        String id = UUID.randomUUID().toString().replace("-","");
        request.id(id);

        Map<String, Object> jsonMap = new HashMap<>();

        jsonMap.put("author", book.getAuthor());
        jsonMap.put("title", book.getTitle());
        jsonMap.put("keyword", book.getKeyword());
        jsonMap.put("content", book.getContent());


        List<Map<String, Object>> commentsList = new ArrayList<Map<String, Object>>();

        book.getComments().forEach(item -> {
            Map<String, Object> commentMap = new HashMap<>();
            commentMap.put("name", item.getName());
            commentMap.put("comment", item.getComment());
            commentMap.put("starts", item.getStars());
            commentMap.put("date", item.getDate());
            commentsList.add(commentMap);
        });


        jsonMap.put("comments",commentsList);
        request.source(jsonMap);


        this.esClient.index(request,options);
    }


    public SearchResponse searchNest(String keyWords) throws  Exception{


        /*
        {
            "query": {
                "nested": {
                    "path": "comments",
                    "query": {
                        "match":{
                            "comments.comment": "设计模式"
                        }
                    }
                }
            }
        }
         */
        SearchRequest searchRequest = new SearchRequest("nesttest");
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();



        searchSourceBuilder.query(QueryBuilders.matchQuery("comments.name",keyWords));
        searchRequest.source(searchSourceBuilder);
        System.out.println(searchRequest);
        return this.esClient.search(searchRequest,options);
    };
}
