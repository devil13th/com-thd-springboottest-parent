package com.thd.springboottest.jdbc.service;

import com.thd.springboottest.jdbc.bean.User;

/**
 * @author devil13th
 **/
public interface User2Service {

    public void saveUserWithNewTx(User user);
    public void saveUserWithNoTx(User user);
}
