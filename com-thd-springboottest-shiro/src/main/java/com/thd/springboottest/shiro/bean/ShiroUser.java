package com.thd.springboottest.shiro.bean;

import java.io.Serializable;
import java.util.Set;

/**
 * com.thd.springboottest.shiro.entity.User
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:05
 * Description: No Description
 */
public class ShiroUser implements Serializable {
    private String id;
    private String userName;
    private String phone;
    private boolean locked;
    // 凭证  例如密码 或 短信验证码
    private String credential;

    /**
     * 用户对应的角色集合
     */
    private Set<ShiroRole> roles;


    public ShiroUser(){}

    public ShiroUser(String id, String userName,String phone, Set<ShiroRole> roles) {
        this.id = id;
        this.userName = userName;
        this.phone = phone;
        this.roles = roles;
    }

    public ShiroUser(String id, String userName, String credential, String phone, Set<ShiroRole> roles) {
        this.id = id;
        this.userName = userName;
        this.roles = roles;
        this.phone = phone;
        this.credential = credential;
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

    public Set<ShiroRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<ShiroRole> roles) {
        this.roles = roles;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getCredential() {
        return credential;
    }

    public void setCredential(String credential) {
        this.credential = credential;
    }


}
