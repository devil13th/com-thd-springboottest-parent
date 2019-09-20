package com.thd.springboottest.mybatisplus.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.thd.springboottest.mybatisplus.entity.SysUser;
import com.thd.springboottest.mybatisplus.mapper.SysUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * com.thd.springboottest.mybatisplus.service.SysUserServiceImpl
 *
 * @author: wanglei62
 * @DATE: 2019/9/20 16:13
 **/

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;

    public List queryAll(){
        return sysUserMapper.selectList(null);
    };

    public List queryByCondition(QueryWrapper<SysUser> wrapper){
        return sysUserMapper.selectList(wrapper);
    }

    public SysUser queryById(String id){
        QueryWrapper<SysUser> qw = new QueryWrapper<SysUser>();
        qw.eq("user_id",id);
        return sysUserMapper.selectOne(qw);
    };


    public List queryByPage(QueryWrapper<SysUser> wrapper,int currentPage,int pageSize){
        IPage<SysUser> page = new Page<SysUser>();
        //当前页
        page.setCurrent(currentPage);
        //每页条目数
        page.setSize(pageSize);
        sysUserMapper.selectPage(page,wrapper);
        return page.getRecords();
    };


}
