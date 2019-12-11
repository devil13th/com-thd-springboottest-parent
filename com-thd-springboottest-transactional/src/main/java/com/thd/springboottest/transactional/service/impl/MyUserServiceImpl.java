package com.thd.springboottest.transactional.service.impl;

import com.thd.springboottest.transactional.dao.JdbcDao;
import com.thd.springboottest.transactional.service.MyUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * com.thd.springboottest.transactional.service.impl.MyUserServiceImpl
 *
 * @author: wanglei62
 * @DATE: 2019/11/28 10:22
 **/
@Service
public class MyUserServiceImpl implements MyUserService {
    @Autowired
    private JdbcDao jdbcDaoImpl;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private ApplicationContext applicationContext;

    @Transactional(propagation=Propagation.REQUIRED)
    public String insertAwithReqTx(String name) {
        String id = UUID.randomUUID().toString().replace("-","");
        String sql = " INSERT INTO my_user(user_id,user_name,user_age,user_birthday,user_create_time) values('" + id + "','" + name + "'," + 5 + ",'2019-01-01','2019-01-01 23:23:23') ";
        this.jdbcTemplate.execute(sql);
        return id;
    }

    @Transactional(propagation=Propagation.REQUIRES_NEW)
    public String insertAwithNewTx(String name) {
        String id = UUID.randomUUID().toString().replace("-","");
        String sql = " INSERT INTO my_user(user_id,user_name,user_age,user_birthday,user_create_time) values('" + id + "','" + name + "'," + 5 + ",'2019-01-01','2019-01-01 23:23:23') ";
        this.jdbcTemplate.execute(sql);
        return id;
    }

    public String insertAwithNoTx(String name) {
        String id = UUID.randomUUID().toString().replace("-","");
        String sql = " INSERT INTO my_user(user_id,user_name,user_age,user_birthday,user_create_time) values('" + id + "','" + name + "'," + 5 + ",'2019-01-01','2019-01-01 23:23:23') ";
        this.jdbcTemplate.execute(sql);
        return id;
    }

    @Transactional(propagation=Propagation.REQUIRED)
    public String insertNestA(boolean hasEx){
        String id = this.insertAwithReqTx("张三");
        this.insertAwithNewTx("张三");
        if(hasEx){
            throw new RuntimeException("业务异常");
        }
        return id;
    };


    public String insertNestAGetBean(boolean hasEx){
        String id = this.insertAwithReqTx("张三");
//        this.insertAwithNewTx("张三");

        MyUserService mu = (MyUserService)applicationContext.getBean("myUserServiceImpl");
        mu.insertAwithNewTx("张三");
        if(hasEx){
            throw new RuntimeException("业务异常");
        }
        return id;
    };


    //@Transactional
    public void deleteAllMyUser() {
        String sql = "delete from my_user";
        this.jdbcTemplate.execute(sql);
    }
}
