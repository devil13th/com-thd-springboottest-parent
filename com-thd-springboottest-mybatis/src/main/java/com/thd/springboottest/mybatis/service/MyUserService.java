package com.thd.springboottest.mybatis.service;

import com.thd.springboottest.mybatis.entity.MyUser;

import java.util.List;
import java.util.Map;

/**
 * @author devil13th
 **/
public interface MyUserService {
    //插入用户
    public int save(MyUser user);
    //更新用户
    public int update(MyUser user);
    //根据id查询
    public MyUser queryById(String id);
    //通用查询
    public List queryList(MyUser user);
    //通用list查询
    public List queryListByLike(MyUser user);
    //删除用户
    public int deleteById(String id);
    //一个联合查询和Map类型的参数
    public List queryJoin(Map m);




}
