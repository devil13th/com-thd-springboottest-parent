package com.thd.springboottest.datetimestamp.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thd.springboottest.datetimestamp.utils.DateToLongSerializer;

import java.sql.Timestamp;

/**
 * com.thd.springboottest.datetimestamp.entity.EntityA
 * @author: wanglei62
 * @DATE: 2019/10/15 21:39
 **/
public class TimestampC {
    private String name;
    @JsonSerialize(using = DateToLongSerializer.class)
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
