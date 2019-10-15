package com.thd.springboottest.datetimestamp.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * com.thd.springboottest.datetimestamp.entity.EntityA
 * @author: wanglei62
 * @DATE: 2019/10/15 21:39
 **/
public class TimestampA {
    private String name;
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
