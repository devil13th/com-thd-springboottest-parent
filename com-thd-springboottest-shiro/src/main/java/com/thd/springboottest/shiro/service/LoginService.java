package com.thd.springboottest.shiro.service;

import com.thd.springboottest.shiro.entity.User;

/**
 * com.thd.springboottest.shiro.service.LoginService
 * User: devil13th
 * Date: 2020/1/23
 * Time: 16:12
 * Description: No Description
 */
public interface LoginService {
    // 根据用户名获取用户
    public User getUserByName(String userName);
    // 根据手机号获取用户
    public User getUserByPhone(String phone);
}
