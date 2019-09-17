package com.thd.springboottest.jdbc.service;

import com.thd.springboottest.jdbc.bean.User;

import java.util.List;

/**
 * @author devil13th
 **/
public interface UserService {
    public int saveUser(User user);
    public int deleteUser(String id);
    public int updateUser(User user);
    public List queryUser(String userName);

    public User queryUserById(String id);
    public void saveMoreUser(User u1,User u2);

    public void testTransaction1();
    public void testTransaction2();
    public void testTransaction3();
    public void testTransaction4();
}
