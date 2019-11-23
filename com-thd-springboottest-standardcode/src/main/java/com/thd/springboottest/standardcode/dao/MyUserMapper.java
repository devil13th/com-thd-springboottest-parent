package com.thd.springboottest.standardcode.dao;

import com.thd.springboottest.standardcode.entity.MyUser;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author devil13th
 **/
@Mapper
@Repository
public interface MyUserMapper {


    //根据name查询
    @Select("select user_id, user_name, user_age, user_birthday, user_create_time from my_user where user_name = #{name}")
    public MyUser queryByName(@Param("name") String name);


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
