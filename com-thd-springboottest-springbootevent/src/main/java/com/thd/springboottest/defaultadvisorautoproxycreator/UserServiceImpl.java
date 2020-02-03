package com.thd.springboottest.defaultadvisorautoproxycreator;

import org.springframework.stereotype.Component;

/**
 * com.thd.springboottest.defaultadvisorautoproxycreator.UserServiceImpl
 * User: devil13th
 * Date: 2020/1/23
 * Time: 23:53
 * Description: No Description
 */
@Component
public class UserServiceImpl implements UserService {
    @Override
    public void print() {
        System.out.println(getClass()+"#print");
    }
}
