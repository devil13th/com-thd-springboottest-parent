package com.thd.springboottest.jsonmessageconverters.vo;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * com.thd.springboottest.jsonmessageconverters.vo.User
 *
 * @author: wanglei62
 * @DATE: 2019/12/9 23:40
 **/
public class User {
   // @JSONField(name="birthday",format="yyyy-MM-dd")

    @JSONField(name="birthday",format = "yyyy-MM-dd HH:mm:ss")
    private Date createTime;

    private String name;
    private Integer age;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
