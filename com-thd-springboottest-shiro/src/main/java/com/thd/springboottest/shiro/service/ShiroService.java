package com.thd.springboottest.shiro.service;


import com.thd.springboottest.shiro.bean.ShiroUser;

/**
 * com.thd.springboot.framework.shiro.service.ShiroService
 *
 * @author: wanglei62
 * @DATE: 2020/5/7 17:41
 **/
public interface ShiroService {
    /**
     * 根据账号获取用户
     * @param account
     * @return
     */
    public ShiroUser loadUserByAccount(String account);

    /**
     * 根据手机号获取用户
     * @param phone
     * @return
     */
    public ShiroUser loadUserByPhone(String phone);
}
