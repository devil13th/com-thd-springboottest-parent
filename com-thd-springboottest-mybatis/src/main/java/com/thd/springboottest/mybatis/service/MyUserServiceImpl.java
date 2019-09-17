package com.thd.springboottest.mybatis.service;

import com.thd.springboottest.mybatis.dao.MyUserDao;
import com.thd.springboottest.mybatis.entity.MyUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author devil13th
 **/
@Service
public class MyUserServiceImpl implements  MyUserService {
    @Autowired
    private MyUserDao myUserDao;
    @Override
    public int save(MyUser user) {
        return myUserDao.save(user);
    }

    @Override
    public int update(MyUser user) {
        return myUserDao.update(user);
    }

    @Override
    public int delete(String id) {
        return myUserDao.delete(id);
    }

    @Override
    public MyUser queryById(String id) {
        return myUserDao.queryById(id);
    }

    @Override
    public List queryByName(MyUser user) {
        return myUserDao.queryByName(user);
    }
}
