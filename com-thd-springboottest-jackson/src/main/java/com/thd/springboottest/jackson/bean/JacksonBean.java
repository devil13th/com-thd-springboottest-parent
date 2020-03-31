package com.thd.springboottest.jackson.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

public class JacksonBean {
    private String name;
    private Date birthday;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date date1;
    @JsonSerialize(using = JsonDateSerializer.class)
    private Date date2;


    // 反序列化测试用
    @JsonDeserialize(using = JsonDateDeserializer.class)
    private Date date3;

    private Timestamp createTime;






    @Override
    public String toString() {
        return "JacksonBean{" +
                "name='" + name + '\'' +
                ", birthday=" + birthday +
                ", date1=" + date1 +
                ", date2=" + date2 +
                ", createTime=" + createTime +
                '}';
    }

    public Date getDate1() {
        return date1;
    }

    public void setDate1(Date date1) {
        this.date1 = date1;
    }

    public Date getDate2() {
        return date2;
    }

    public void setDate2(Date date2) {
        this.date2 = date2;
    }



    public JacksonBean() {}

    public JacksonBean(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    public Date getDate3() {
        return date3;
    }

    public void setDate3(Date date3) {
        this.date3 = date3;
    }
}
