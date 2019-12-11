package com.thd.springboottest.transactional.service.impl;

import com.thd.springboottest.transactional.dao.JdbcDao;
import com.thd.springboottest.transactional.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * com.thd.springboottest.transactional.service.impl.SysUserServiceImpl
 *
 * @author: wanglei62
 * @DATE: 2019/11/28 10:28
 **/
@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private JdbcDao jdbcDaoImpl;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public String insertBwithReqTx(String name) {
        String id = UUID.randomUUID().toString().replace("-","");
        String sql = " INSERT INTO sys_user(user_id,user_name) values('" + id + "','" + name + "') ";
        this.jdbcTemplate.execute(sql);
        return id;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public String insertBwithNewTx(String name) {
        String id = UUID.randomUUID().toString().replace("-","");
        String sql = " INSERT INTO sys_user(user_id,user_name) values('" + id + "','" + name + "') ";
        this.jdbcTemplate.execute(sql);
        return id;
    }

    @Override
    public String insertBwithNoTx(String name) {
        String id = UUID.randomUUID().toString().replace("-","");
        String sql = " INSERT INTO sys_user(user_id,user_name) values('" + id + "','" + name + "') ";
        this.jdbcTemplate.execute(sql);
        return id;
    }

    @Override
    //@Transactional
    public void deleteAllSysUser() {
        String sql = "delete from sys_user";
        this.jdbcTemplate.execute(sql);
    }
}
