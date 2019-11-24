package com.thd.springboottest.redis.vo;

import com.alibaba.fastjson.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.format.annotation.DateTimeFormat;

import java.sql.Timestamp;
import java.util.Date;

/**
 * @author devil13th
 **/
public class MyUser {
    private String userId;
    private String userName;
    private Integer userAge;
    // redis使用fastjson格式化
    @JSONField(format="yyyy-MM-dd")
    // MVC Date反序列化格式(客户端->服务端)
    @DateTimeFormat(pattern="yyyy-MM-dd")
    // MVC Date 的序列化格式(服务端->客户端)
    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date userBirthday;

    // redis使用fastjson格式化
    @JSONField(format="yyyy-MM-dd HH:mm:ss")
    // MVC TimeStamp 的序列化和反序列化格式(客户端->服务端 以及服务端->客户端)
    @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Timestamp userCreateTime;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getUserAge() {
        return userAge;
    }

    public void setUserAge(Integer userAge) {
        this.userAge = userAge;
    }

    public Date getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(Date userBirthday) {
        this.userBirthday = userBirthday;
    }

    public Timestamp getUserCreateTime() {
        return userCreateTime;
    }

    public void setUserCreateTime(Timestamp userCreateTime) {
        this.userCreateTime = userCreateTime;
    }

    @Override
    public String toString() {
        return "MyUser{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userAge=" + userAge +
                ", userBirthday=" + userBirthday +
                ", userCreateTime=" + userCreateTime +
                '}';
    }
}
