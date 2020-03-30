package com.thd.springboottest.mybatis.entity;

import java.util.Date;

/**
 * @author devil13th
 **/
public class MyUser {
    private String userId;
    private String userName;
    private Integer userAge;
    private Date userBirthday;
    private Date userCreateTime;

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

    public Date getUserCreateTime() {
        return userCreateTime;
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

    public void setUserCreateTime(Date userCreateTime) {
        this.userCreateTime = userCreateTime;
    }
}
