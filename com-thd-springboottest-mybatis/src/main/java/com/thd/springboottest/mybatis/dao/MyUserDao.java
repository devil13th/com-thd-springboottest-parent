package com.thd.springboottest.mybatis.dao;

import com.thd.springboottest.mybatis.entity.MyUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author devil13th
 **/
@Mapper
@Repository
public interface MyUserDao {

    //插入用户
    public int save(MyUser user);
    //更新用户
    public int update(MyUser user);
    //删除用户
    public int delete(String id);
    //根据id查询
    public MyUser queryById(String id);
    //根据名称查询
    public List queryByName(MyUser user);
}
