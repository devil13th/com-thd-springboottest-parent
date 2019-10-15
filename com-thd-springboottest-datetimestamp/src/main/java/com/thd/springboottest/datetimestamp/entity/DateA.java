package com.thd.springboottest.datetimestamp.entity;

import java.util.Date;

/**
 * com.thd.springboottest.datetimestamp.entity.EntityA
 * @author: wanglei62
 * @DATE: 2019/10/15 21:39
 **/
public class DateA {
    private String name;
    private Date date;
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
