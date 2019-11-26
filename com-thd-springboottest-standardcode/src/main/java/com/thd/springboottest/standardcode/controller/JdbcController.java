package com.thd.springboottest.standardcode.controller;

import com.thd.springboottest.standardcode.controller.parent.BasicController;
import com.thd.springboottest.standardcode.jdbc.JdbcDao;
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

@RequestMapping("/jdbc")
@Controller
public class JdbcController extends BasicController {
    @Autowired
    private JdbcDao jdbcDaoImpl;

    @RequestMapping("/jdbc")
    @ResponseBody
    // url : http://127.0.0.1:8899/thd/jdbc/jdbc
    public ResponseEntity jdbc(){
        this.getLog().info("jdbc()");
        String sql = "select user_id as userId,user_name as userName,user_age as userAge,user_birthday as userBirthday,user_create_time as userCreateTime from my_user";
        List l = jdbcDaoImpl.query(sql,null,null);
        return ResponseEntity.ok(l);
    }

}
