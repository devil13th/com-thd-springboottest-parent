package com.thd.springboottest.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.thd.springboottest.mybatisplus.entity.SysUser;

import java.util.List;

/**
 * com.thd.springboottest.mybatisplus.service.SysUserService
 *
 * @author: wanglei62
 * @DATE: 2019/9/20 16:13
 **/

public interface SysUserService {
    /**
     * 查询所有
     * @return
     */
    public List queryAll();
    public SysUser loadByName(String userName);
    public List<SysUser> queryByName(String userName);
    /**
     * 根据条件查询
     * @param wrapper
     * @return
     */
    public List queryByCondition(QueryWrapper<SysUser> wrapper);

    public SysUser queryById(String id);

    public List queryByPage(QueryWrapper<SysUser> wrapper,int currentPage,int pageSize);
}
