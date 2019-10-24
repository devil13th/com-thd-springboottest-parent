package com.thd.springboottest.mybatis.dao;

import com.thd.springboottest.mybatis.entity.MyUser;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author devil13th
 **/
@Mapper
@Repository
public interface MyUserMapper {
    //插入用户
    public int save(MyUser user);
    //更新用户
    public int update(MyUser user);
    //根据id查询
    public MyUser queryById(String id);
    //通用查询
    public List queryList(MyUser user);
    //通用Like查询
    public List queryListByLike(MyUser user);
    //根据id删除
    public int deleteById(String id);

    //一个联合查询
    public List queryJoinExample(Map m);
}
