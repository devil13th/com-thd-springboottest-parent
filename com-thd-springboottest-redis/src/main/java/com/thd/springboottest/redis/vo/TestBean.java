package com.thd.springboottest.redis.vo;

import java.io.Serializable;

/**
 * @author devil13th
 **/
public class TestBean implements Serializable {
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
