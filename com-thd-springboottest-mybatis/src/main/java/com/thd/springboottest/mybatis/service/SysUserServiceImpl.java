package com.thd.springboottest.mybatis.service;

import com.thd.springboottest.mybatis.dao.SysUserDao;
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
    private SysUserDao sysUserDao;

    public SysUser getByUserId(String id){
        return sysUserDao.selectById(id);
    }
    //获取全部用户
    public List<SysUser> getAll(){
        return sysUserDao.selectAll();
    }
    @Transactional
    public int insert(SysUser user){
        return sysUserDao.insert(user);
    }
}
