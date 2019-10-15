package com.thd.springboottest.datetimestamp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.sql.Timestamp;

/**
 * com.thd.springboottest.datetimestamp.entity.EntityA
 * @author: wanglei62
 * @DATE: 2019/10/15 21:39
 **/
public class TimestampB {
    private String name;
    @JsonFormat( pattern="yyyy/MM/dd HH:mm:ss",timezone = "GMT+8")
    private Timestamp timestamp;
    public Timestamp getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
}
