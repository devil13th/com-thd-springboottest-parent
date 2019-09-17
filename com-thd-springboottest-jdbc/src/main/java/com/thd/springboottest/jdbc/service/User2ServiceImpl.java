package com.thd.springboottest.jdbc.service;

import com.thd.springboottest.jdbc.bean.User;
import com.thd.springboottest.jdbc.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author devil13th
 **/
@Service
public class User2ServiceImpl implements User2Service {
    @Autowired
    private UserDao userDao;


    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void saveUserWithNewTx(User user) {
        userDao.saveUser(user);
    }

    @Override
    public void saveUserWithNoTx(User user) {
        userDao.saveUser(user);
    }
}
