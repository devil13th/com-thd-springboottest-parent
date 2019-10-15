package com.thd.springboottest.datetimestamp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

/**
 * com.thd.springboottest.datetimestamp.entity.EntityA
 * @author: wanglei62
 * @DATE: 2019/10/15 21:39
 **/
public class DateB {
    private String name;
    @JsonFormat( pattern="yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
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
