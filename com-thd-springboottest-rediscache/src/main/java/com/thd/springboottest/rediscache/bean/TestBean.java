package com.thd.springboottest.rediscache.bean;

/**
 * @author devil13th
 **/
public class TestBean {
    private String id;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TestBean() {
    }

    public TestBean(String id, String name) {
        this.id = id;
        this.name = name;
    }
}
