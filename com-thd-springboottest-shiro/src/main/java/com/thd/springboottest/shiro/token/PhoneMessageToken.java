package com.thd.springboottest.shiro.token;

import org.apache.shiro.authc.HostAuthenticationToken;
import org.apache.shiro.authc.RememberMeAuthenticationToken;

public class PhoneMessageToken implements HostAuthenticationToken, RememberMeAuthenticationToken {

    /**
     * The phone
     */
    private String phone;
    private boolean rememberMe;
    private String host;
    private String validateCode;


    public PhoneMessageToken(String phone, String validateCode) {
        this.rememberMe = false;
        this.host = null;
        this.phone = phone;
        this.validateCode = validateCode;
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
        return this.phone;
    }

    @Override
    public Object getCredentials() {
        return this.validateCode;
    }



}
