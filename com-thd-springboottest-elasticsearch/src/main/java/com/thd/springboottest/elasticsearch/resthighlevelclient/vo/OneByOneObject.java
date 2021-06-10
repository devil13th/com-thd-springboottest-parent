package com.thd.springboottest.elasticsearch.resthighlevelclient.vo;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.vo.OneByOneObject
 *
 * @author: wanglei62
 * @DATE: 2021/6/7 9:03
 **/
public class OneByOneObject {
    private String title;
    private String keyword;
    private String content;
    private Author author;
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

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }
}
