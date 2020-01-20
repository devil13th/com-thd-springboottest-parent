package com.thd.springboottest.mybatis.service;

//import com.github.pagehelper.PageInfo;
//import com.thd.springboottest.mybatis.entity.MyUser;
import com.github.pagehelper.PageInfo;
import com.thd.springboottest.mybatis.entity.MyUser;
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

    public List<SysUser> queryByName(String name);

    // 分页查询
    public PageInfo<SysUser> queryByNamePage(String username, int limit, int page);
}
