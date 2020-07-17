package com.thd.springboottest.activiti.vo;

import java.io.Serializable;

/**
 * com.thd.springboottest.activiti.vo.User
 *
 * @author: wanglei62
 * @DATE: 2020/7/17 18:26
 **/
public class User implements Serializable {

    private String input;
    private String audit;

    public User(String input, String audit) {
        this.input = input;
        this.audit = audit;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getAudit() {
        return audit;
    }

    public void setAudit(String audit) {
        this.audit = audit;
    }
}
