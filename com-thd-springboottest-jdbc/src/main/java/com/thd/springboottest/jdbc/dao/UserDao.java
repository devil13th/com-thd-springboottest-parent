package com.thd.springboottest.jdbc.dao;


import com.thd.springboottest.jdbc.bean.User;

import java.util.List;

/**
 * @author devil13th
 **/
public interface UserDao {
    public int saveUser(User user);
    public int deleteUser(String id);
    public int updateUser(User user);
    public List queryUser(String userName);
    public User queryUserById(String id);
}
