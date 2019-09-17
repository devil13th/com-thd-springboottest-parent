package com.thd.springboottest.normalweb.dto;

/**
 * @author devil13th
 **/
public class MyBean {
    private String name;

    public MyBean(String name, String welcome) {
        this.name = name;
        this.welcome = welcome;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getWelcome() {
        return welcome;
    }

    public void setWelcome(String welcome) {
        this.welcome = welcome;
    }

    private String welcome;

}
