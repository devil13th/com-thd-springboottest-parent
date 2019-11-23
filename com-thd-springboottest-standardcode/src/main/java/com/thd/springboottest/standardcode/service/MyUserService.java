package com.thd.springboottest.standardcode.service;

import com.thd.springboottest.standardcode.entity.MyUser;

/**
 * com.thd.springboottest.standardcode.service.MyUserService
 * User: devil13th
 * Date: 2019/11/23
 * Time: 18:21
 * Description: No Description
 */
public interface MyUserService {
    public MyUser queryUserById(String id);
    public MyUser queryUserByName(String name);
}
