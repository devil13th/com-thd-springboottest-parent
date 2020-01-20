package com.thd.springboottest.mybatis.dao;

import com.thd.springboottest.mybatis.entity.SysUser;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @author devil13th
 **/
@Mapper
@Repository
public interface SysUserMapper {
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

    // map类型
    List<Map<String,String>> selectAllForMap();

    // 批量插入
    void insertBatch(@Param(value="list") List<SysUser> list);

    // in 查询
    List<SysUser> selectin(@Param(value="list") List<String> list);

    // 返回map, key为指定属性，value为实体类结果集
    @MapKey("userId")  // 指定key属性取哪列
    Map<String,SysUser> selectAllForMapKey();

    // 统计查询
    Integer queryCount(@Param("userName") String userName);

    // 根据姓名查询用户
    public List<SysUser> queryByName(@Param(value="name") String name);
}
