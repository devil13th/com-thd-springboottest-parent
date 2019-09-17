package com.thd.springboottest.mybatis.dao;

import com.thd.springboottest.mybatis.entity.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author devil13th
 **/
@Mapper
@Repository
public interface SysUserDao {
    @Select("select * from sys_user where user_id=#{id}")
    public SysUser getNameById(@Param("id") long id);


//    @Options(useGeneratedKeys = true, keyProperty = "iid")
//    @Insert("insert into user(name) values(#{name})")
//    public int insert(SysUser user);

    //插入用户
    int insert(SysUser user);
    //根据id查询
    SysUser selectById(String id);
    //查询所有
    List<SysUser> selectAll();
}
