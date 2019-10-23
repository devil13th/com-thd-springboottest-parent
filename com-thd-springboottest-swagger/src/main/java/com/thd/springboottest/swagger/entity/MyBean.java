package com.thd.springboottest.swagger.entity;

import io.swagger.annotations.ApiModel;

/**
 * com.thd.springboottest.swagger.entity.MyBean
 *
 * @author: wanglei62
 * @DATE: 2019/10/23 18:31
 **/
@ApiModel(value="基础返回类",description="基础返回类")
public class MyBean {
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
}
