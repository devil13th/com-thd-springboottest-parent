package com.thd.springboottest.druid.controller;

import com.thd.springboottest.dao.JdbcDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * com.thd.springboottest.standardcode.controller.TestController
 *
 * @author: wanglei62
 * @DATE: 2019/11/22 9:08
 **/

@RequestMapping("/test")
@Controller
public class TestController{
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    private JdbcDao jdbcDaoImpl;

    @RequestMapping("/test")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/druid/test
    public ResponseEntity test(){
        this.logger.info("test()");
        String sql = "select user_id as userId,user_name as userName,user_age as userAge,user_birthday as userBirthday,user_create_time as userCreateTime from my_user";
        List l = jdbcDaoImpl.query(sql,null,null);
        return ResponseEntity.ok(l);
    }

}
