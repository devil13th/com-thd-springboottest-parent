package com.thd.springboottest.elasticsearch.resthighlevelclient.vo;

import java.util.Date;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Comments
 *
 * @author: wanglei62
 * @DATE: 2021/6/7 7:21
 **/
public class Comments {
    private String name;
    private String comment;
    private Integer stars;
    private Date date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getStars() {
        return stars;
    }

    public void setStars(Integer stars) {
        this.stars = stars;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
