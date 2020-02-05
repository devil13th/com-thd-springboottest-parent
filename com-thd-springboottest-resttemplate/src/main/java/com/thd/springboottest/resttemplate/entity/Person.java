package com.thd.springboottest.resttemplate.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.Date;

/**
 * com.thd.springboottest.requestparameter.entity.Person
 * User: devil13th
 * Date: 2019/9/30
 * Time: 0:14
 * Description: No Description
 */
public class Person {
    private String name;
    private int age;

    @Override
    public String toString() {
        return " ****** Person {" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", birthday=" + birthday +
                '}';
    }
    @JSONField(ordinal=4,format="yyyy-MM-dd")
    private Date birthday;
    public Person() {

    }
    public Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }
}
