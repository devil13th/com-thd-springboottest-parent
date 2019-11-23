package com.thd.springboottest.standardcode.service.impl;

import com.thd.springboottest.standardcode.dao.MyUserMapper;
import com.thd.springboottest.standardcode.entity.MyUser;
import com.thd.springboottest.standardcode.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * com.thd.springboottest.standardcode.service.impl.MyUserServiceImpl
 * User: devil13th
 * Date: 2019/11/23
 * Time: 18:21
 * Description: No Description
 */
@Service
@Transactional
public class MyUserServiceImpl implements MyUserService {
    @Autowired
    private MyUserMapper myUserMapper;

    public MyUser queryUserById(String id){
        return this.myUserMapper.queryById(id);
    };

    public MyUser queryUserByName(String name){
        return this.myUserMapper.queryByName(name);
    };
}
