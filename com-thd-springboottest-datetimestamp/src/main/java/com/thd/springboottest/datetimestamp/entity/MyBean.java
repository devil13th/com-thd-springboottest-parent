package com.thd.springboottest.datetimestamp.entity;

import java.sql.Timestamp;
import java.util.Date;

/**
 * com.thd.springboottest.datetimestamp.entity.MyBean
 *
 * @author: wanglei62
 * @DATE: 2020/4/3 10:37
 **/
public class MyBean {
    private String name;
    private Timestamp createTime;
    private Date birthday;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
