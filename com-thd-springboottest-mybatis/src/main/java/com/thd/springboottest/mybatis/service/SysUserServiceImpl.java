package com.thd.springboottest.mybatis.service;


import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.thd.springboottest.mybatis.dao.SysUserMapper;
import com.thd.springboottest.mybatis.entity.MyUser;
import com.thd.springboottest.mybatis.entity.SysUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author devil13th
 **/
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    // 根据id查询用户
    public SysUser getByUserId(String id){
        return sysUserMapper.selectById(id);
    }
    //获取全部用户
    public List<SysUser> getAll(){
        return sysUserMapper.selectAll();
    }
    @Transactional
    //保存用户
    public int insert(SysUser user){
        return sysUserMapper.insert(user);
    }


    public List<SysUser> queryByName(String name){
        return sysUserMapper.queryByName(name);
    }

    // 分页查询
    public PageInfo<SysUser> queryByNamePage(String username, int limit, int page){
        PageHelper.startPage(page, limit).setOrderBy("user_name desc");
        PageInfo<SysUser> userPageInfo = new PageInfo<SysUser>(sysUserMapper.queryByName(username));
        return userPageInfo;
    }

}
