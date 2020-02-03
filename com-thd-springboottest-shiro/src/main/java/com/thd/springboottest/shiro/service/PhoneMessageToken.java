package com.thd.springboottest.shiro.service;

import com.thd.springboottest.shiro.entity.User;
import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class PhoneMessageToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    /**
     * The phone
     */
    private String phone;

    private User user;

    private boolean rememberMe;
    private String host;


    public PhoneMessageToken(User u,String phone) {
        this.phone = phone;
        this.rememberMe = false;
        this.host = null;
        this.user=u;
    }



    public void setRememberMe(boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    public void setHost(String host) {
        this.host = host;
    }


    @Override
    public String getHost() {
        return this.host;
    }

    @Override
    public boolean isRememberMe() {
        return this.rememberMe;
    }

    @Override
    public Object getPrincipal() {
        return this.user;
    }

    @Override
    public Object getCredentials() {
        return this.phone;
    }
}
