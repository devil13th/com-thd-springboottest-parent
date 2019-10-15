package com.thd.springboottest.datetimestamp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

/**
 * com.thd.springboottest.datetimestamp.entity.DateTimeStamp
 *
 * @author: wanglei62
 * @DATE: 2019/10/15 22:51
 **/
public class DateTimeStamp {
    private String name;
    // @DateTimeFormat(pattern="yyyy-MM-dd")
    // @DateTimeFormat(pattern="yyyy/MM/dd HH:mm:ss")
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date date;
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp timestamp;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
}
