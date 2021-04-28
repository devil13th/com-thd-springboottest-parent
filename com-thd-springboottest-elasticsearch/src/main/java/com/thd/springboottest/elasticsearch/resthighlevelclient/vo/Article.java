package com.thd.springboottest.elasticsearch.resthighlevelclient.vo;

import java.util.Date;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Article
 *
 * @author: wanglei62
 * @DATE: 2021/4/19 16:38
 **/
public class Article {
    private String id;
    private String title;
    // 分类 参见 com.thd.springboottest.elasticsearch.resthighlevelclient.constant.ArticleClassifyEnum
    private String classify;
    private String content;
    private String author;
    private Date date;
    private Integer supportNum;
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Integer getSupportNum() {
        return supportNum;
    }

    public void setSupportNum(Integer supportNum) {
        this.supportNum = supportNum;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
