package com.thd.springboottest.redistemplate.bean;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.thd.springboottest.redistemplate.serializer.JsonDateDeserializer;
import com.thd.springboottest.redistemplate.serializer.JsonDateSerializer;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;

/**
 * @author devil13th
 **/
public class User implements Serializable {
    private String userId;
    private String userName;
    private Integer userAge;
//    @JsonSerialize(using = JsonDateSerializer.class)
//    @JsonDeserialize(using = JsonDateDeserializer.class)
//    @JsonFormat(pattern="yyyy-MM-dd", timezone = "GMT+8")
    private Date userBirthday;


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
