package com.thd.springboottest.shiro.entity;

import java.io.Serializable;
import java.util.Set;

/**
 * com.thd.springboottest.shiro.entity.User
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:05
 * Description: No Description
 */
public class User implements Serializable {
    private String id;
    private String userName;
    private String password;
    private String phone;

    public User(){}

    public User(String id, String userName, String password, Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
    }

    public User(String id, String userName, String password, String phone,Set<Role> roles) {
        this.id = id;
        this.userName = userName;
        this.password = password;
        this.roles = roles;
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    /**
     * 用户对应的角色集合
     */
    private Set<Role> roles;
}
