package com.thd.springboottest.redistemplate.bean;

import java.io.Serializable;

/**
 * com.thd.jackson.vo.Item
 *
 * @author: wanglei62
 * @DATE: 2021/4/22 11:51
 **/
public class Item implements Serializable {
    private Integer id;
    private String name;
    private String type;

    @Override
    public String toString() {
        return "Item{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                '}';
    }
    public Item() {}

    public Item(Integer id, String name, String type) {
        this.id = id;
        this.name = name;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
