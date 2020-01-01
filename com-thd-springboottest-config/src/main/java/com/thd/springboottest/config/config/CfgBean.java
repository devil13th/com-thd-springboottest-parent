package com.thd.springboottest.config.config;

/**
 * com.thd.springboottest.config.config.CfgBean
 *
 * @author: wanglei62
 * @DATE: 2020/1/1 16:37
 **/
public class CfgBean {
    private String name;
    private Integer age;

    @Override
    public String toString() {
        return "CfgBean{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

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
