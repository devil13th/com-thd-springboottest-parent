package com.thd.springboottest.mybatis.service;

import com.thd.springboottest.mybatis.entity.SysUser;

import java.util.List;

/**
 * @author devil13th
 **/
public interface SysUserService {

    public SysUser getByUserId(String id);
    //获取全部用户
    public List<SysUser> getAll();

    public int insert(SysUser user);
}
