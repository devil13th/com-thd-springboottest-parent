package com.thd.springboottest.elasticsearch.resthighlevelclient.vo;

/**
 * com.thd.springboottest.elasticsearch.resthighlevelclient.vo.Author
 *
 * @author: wanglei62
 * @DATE: 2021/6/7 9:03
 **/
public class Author {
    private String name;
    private Long age;
    private String desc;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getAge() {
        return age;
    }

    public void setAge(Long age) {
        this.age = age;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
