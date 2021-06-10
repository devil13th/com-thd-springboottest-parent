package com.thd.springboottest.elasticsearch.resthighlevelclient.vo;

import java.util.List;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.vo.NestTestBean
 *
 * @author: wanglei62
 * @DATE: 2021/6/7 7:19
 **/
public class Book {
    private String author;
    private String title;
    private String keyword;
    private String content;
    private List<Comments> comments;

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public List<Comments> getComments() {
        return comments;
    }

    public void setComments(List<Comments> comments) {
        this.comments = comments;
    }
}
